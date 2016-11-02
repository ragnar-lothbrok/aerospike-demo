import java.util.StringTokenizer;

import org.slf4j.LoggerFactory;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Host;
import com.aerospike.client.async.AsyncClient;
import com.aerospike.client.async.AsyncClientPolicy;
import com.aerospike.client.policy.ClientPolicy;

/**
 * 
 * @author raghunandangupta
 *
 */
public class AerospikeClientFactory {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AerospikeClientFactory.class);

	private static AsyncClient asyncAerospikeClient;
	private static AerospikeClient syncAerospikeClient;

	public static AerospikeClient getSyncAerospikeClient(AerospikeConfiguration aerospikeConfiguration) {
		if (null == syncAerospikeClient) {
			synchronized (AerospikeClientFactory.class) {
				if (null == syncAerospikeClient) {
					LOGGER.info("Creating Aerospike client with properties {}", aerospikeConfiguration);
					Host[] hosts = initializeHosts(aerospikeConfiguration.getAerospikeServers());
					ClientPolicy policy = new ClientPolicy();
					policy = setPolicy(policy, aerospikeConfiguration);
					syncAerospikeClient = new AerospikeClient(policy, hosts);
				}
			}
		}
		return syncAerospikeClient;
	}

	public static AsyncClient getAsyncAerospikeClient(AerospikeConfiguration aerospikeConfiguration) {
		if (null == asyncAerospikeClient) {
			synchronized (AerospikeClientFactory.class) {
				if (null == asyncAerospikeClient) {
					LOGGER.info("Creating Aerospike client with properties {}", aerospikeConfiguration);
					Host[] hosts = initializeHosts(aerospikeConfiguration.getAerospikeServers());
					AsyncClientPolicy policy = new AsyncClientPolicy();
					policy = (AsyncClientPolicy) setPolicy(policy, aerospikeConfiguration);
					asyncAerospikeClient = new AsyncClient(policy, hosts);
				}
			}
		}
		return asyncAerospikeClient;
	}

	private static Host[] initializeHosts(String server) {
		StringTokenizer st = new StringTokenizer(server, ",");
		Host[] hosts = new Host[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			String host = st.nextToken();
			hosts[i] = new Host(host.split(":")[0], Integer.parseInt(host.split(":")[1]));
			i++;
		}
		return hosts;
	}

	private static ClientPolicy setPolicy(ClientPolicy policy, AerospikeConfiguration aerospikeConfiguration) {
		policy.readPolicyDefault.timeout = aerospikeConfiguration.getReadTimeout();
		policy.readPolicyDefault.maxRetries = aerospikeConfiguration.getReadMaxRetries();
		policy.readPolicyDefault.sleepBetweenRetries = aerospikeConfiguration.getReadSleepBetweenRetries();
		policy.writePolicyDefault.timeout = aerospikeConfiguration.getWriteTimeout();
		policy.writePolicyDefault.maxRetries = aerospikeConfiguration.getWriteMaxRetries();
		policy.writePolicyDefault.sleepBetweenRetries = aerospikeConfiguration.getWriteSleepBetweenRetries();
		return policy;
	}

}
