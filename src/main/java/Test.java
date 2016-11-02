import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.aerospike.client.AerospikeException;
import com.google.gson.Gson;


/**
 * 
 * sudo service aerospike restart
 * 
 * sudo vim /etc/aerospike/aerospike.conf
 * 
 * namespace ad_network {
        replication-factor 2
        memory-size 1G
        default-ttl 30d # 30 days, use 0 to never expire/evict.

        storage-engine memory
   }
 * 
 * @author raghunandangupta
 *
 */
public final class Test {
	public static void main(String[] args) {
		try {
			runTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void runTest() throws AerospikeException {

		System.out.println(new Gson().toJson(new AerospikeConfiguration()));
		
		AerospikeConfiguration aerospikeConfiguration = new AerospikeConfiguration();
		AerospikeAdSearchService aerospikeAdSearchService = AerospikeAdSearchService.getASSearchServiceInstance(aerospikeConfiguration);
		List<SearchResponse> getSearchResponse = getSearchResponse();
		for (int i = 0; i < 10; i++) {
			try {
				aerospikeAdSearchService.writeToAerospike(new SearchRequest(), getSearchResponse);
				getSearchResponse.get(0).getSupcs().add("" + i);
				List<SearchResponse> record = aerospikeAdSearchService.getSearchResponse(new SearchRequest());
				System.out.println(record);

			} finally {

			}
		}
	}

	private static List<SearchResponse> getSearchResponse() {
		List<SearchResponse> searchResponseList = new ArrayList<SearchResponse>();
		for (int i = 0; i < 5; i++) {
			SearchResponse searchResponse = new SearchResponse();
			searchResponse.setProbability(new Random().nextFloat());
			searchResponse.setPogId(new Random().nextLong());
			searchResponse.getSupcs().add(UUID.randomUUID().toString());
			searchResponseList.add(searchResponse);
		}
		return searchResponseList;
	}
}