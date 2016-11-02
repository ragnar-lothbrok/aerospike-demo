import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.Value;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;

/**
 * This service will communicate with aerospike
 * 
 * @author raghunandangupta
 *
 */
public class AerospikeAdSearchService {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AerospikeAdSearchService.class);

	private AerospikeConfiguration aerospikeConfiguration;
	private static volatile AerospikeAdSearchService aerospikeAdSearchService = null;

	private static final String SEPERATOR = "~";

	public static AerospikeAdSearchService getASSearchServiceInstance(AerospikeConfiguration aerospikeConfiguration) {
		if (aerospikeAdSearchService == null) {
			synchronized (AerospikeAdSearchService.class) {
				if (aerospikeAdSearchService == null) {
					aerospikeAdSearchService = new AerospikeAdSearchService(aerospikeConfiguration);
				}
			}
		}
		return aerospikeAdSearchService;
	}

	private AerospikeAdSearchService(AerospikeConfiguration aerospikeConfiguration) {
		super();
		this.aerospikeConfiguration = aerospikeConfiguration;
	}

	public Record readFromAeroSpike(SearchRequest searchRequest) {
		if (searchRequest != null) {
			AerospikeClient aClient = AerospikeClientFactory.getAsyncAerospikeClient(aerospikeConfiguration);
			return this.readSync(searchRequest, aClient);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SearchResponse> getSearchResponse(SearchRequest searchRequest) {
		List<SearchResponse> searchResponseList = null;
		Record record = readFromAeroSpike(searchRequest);
		if (record != null) {
			LOGGER.debug("Found record: Expiration=" + record.expiration + " Generation=" + record.generation);
			for (Map.Entry<String, Object> entry : record.bins.entrySet()) {
				if (entry.getValue() instanceof List<?>) {
					searchResponseList = (List<SearchResponse>) entry.getValue();
				}
			}
		}
		return searchResponseList;
	}

	private Record readSync(SearchRequest searchRequest, AerospikeClient aClient) {
		Record record = null;
		if (searchRequest != null) {
			try {
				Key key = new Key(aerospikeConfiguration.getSearchConfiguration().getAerospikeNS(),
						aerospikeConfiguration.getSearchConfiguration().getAerospikeSet(), Value.get(generateSearchKey(searchRequest)));
				record = aClient.get(new QueryPolicy(), key);

			} catch (Exception e) {
				LOGGER.error("Error while reading from aerospike {} {}", searchRequest, e);
				e.printStackTrace();
			}
		}
		return record;
	}

	public void writeToAerospike(SearchRequest searchRequest, List<SearchResponse> searchResponse) {
		if (searchResponse != null && searchRequest != null) {
			List<SearchResponse> cachedResponse = getSearchResponse(searchRequest);
			if (cachedResponse == null) {
				AerospikeClient aClient = AerospikeClientFactory.getAsyncAerospikeClient(aerospikeConfiguration);
				this.writeAsync(searchRequest, searchResponse, aClient);
			}
		}
	}

	public void writeAsync(SearchRequest searchRequest, List<SearchResponse> searchResponse, AerospikeClient aClient) {
		if (searchResponse != null && searchRequest != null) {
			try {
				Key key = new Key(aerospikeConfiguration.getSearchConfiguration().getAerospikeNS(),
						aerospikeConfiguration.getSearchConfiguration().getAerospikeSet(), Value.get(generateSearchKey(searchRequest)));
				Bin bin = new Bin(aerospikeConfiguration.getSearchConfiguration().getAerospikeBin(), searchResponse);
				WritePolicy wp = new WritePolicy();
				wp.expiration = aerospikeConfiguration.getWriteExpirationTime();
				wp.recordExistsAction = RecordExistsAction.UPDATE;
				aClient.put(wp, key, bin);
			} catch (Exception e) {
				LOGGER.error("Error while pushing to aerospike {} {} {}", searchRequest, searchResponse, e);
			}
		}
	}

	public static String generateSearchKey(SearchRequest searchRequest) {
		StringBuilder sb = new StringBuilder();
		sb.append(searchRequest.getPageNo()).append(SEPERATOR).append(searchRequest.getPageSize());

		if (searchRequest.getKeyWord() != null) {
			sb.append(SEPERATOR).append(searchRequest.getKeyWord());
		}

		if (searchRequest.getCategoryPath() != null) {
			sb.append(SEPERATOR).append(searchRequest.getCategoryPath());
		}

		if (searchRequest.getQueryText() != null) {
			sb.append(SEPERATOR).append(searchRequest.getQueryText());
		}

		return sb.toString();
	}

}