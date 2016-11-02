import java.io.Serializable;

public class AerospikeConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	private String aerospikeServers = "10.41.87.67:3000";
	private int readTimeout = 50000;
	private int readMaxRetries = 3;
	private int readSleepBetweenRetries = 10;
	private int writeTimeout = 20000;
	private int writeMaxRetries = 3;
	private int writeSleepBetweenRetries = 50;
	private int writeExpirationTime = 10;
	private SearchConfiguration searchConfiguration = new SearchConfiguration();

	public SearchConfiguration getSearchConfiguration() {
		return searchConfiguration;
	}

	public void setSearchConfiguration(SearchConfiguration searchConfiguration) {
		this.searchConfiguration = searchConfiguration;
	}

	public int getWriteExpirationTime() {
		return writeExpirationTime;
	}

	public void setWriteExpirationTime(int writeExpirationTime) {
		this.writeExpirationTime = writeExpirationTime;
	}

	public String getAerospikeServers() {
		return aerospikeServers;
	}

	public void setAerospikeServers(String aerospikeServers) {
		this.aerospikeServers = aerospikeServers;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getReadMaxRetries() {
		return readMaxRetries;
	}

	public void setReadMaxRetries(int readMaxRetries) {
		this.readMaxRetries = readMaxRetries;
	}

	public int getReadSleepBetweenRetries() {
		return readSleepBetweenRetries;
	}

	public void setReadSleepBetweenRetries(int readSleepBetweenRetries) {
		this.readSleepBetweenRetries = readSleepBetweenRetries;
	}

	public int getWriteTimeout() {
		return writeTimeout;
	}

	public void setWriteTimeout(int writeTimeout) {
		this.writeTimeout = writeTimeout;
	}

	public int getWriteMaxRetries() {
		return writeMaxRetries;
	}

	public void setWriteMaxRetries(int writeMaxRetries) {
		this.writeMaxRetries = writeMaxRetries;
	}

	public int getWriteSleepBetweenRetries() {
		return writeSleepBetweenRetries;
	}

	public void setWriteSleepBetweenRetries(int writeSleepBetweenRetries) {
		this.writeSleepBetweenRetries = writeSleepBetweenRetries;
	}

	@Override
	public String toString() {
		return "AerospikeConfiguration [readTimeout=" + readTimeout + ", readMaxRetries=" + readMaxRetries + ", readSleepBetweenRetries="
				+ readSleepBetweenRetries + ", writeTimeout=" + writeTimeout + ", writeMaxRetries=" + writeMaxRetries + ", writeSleepBetweenRetries="
				+ writeSleepBetweenRetries + "]";
	}

	static class SearchConfiguration {
		private String aerospikeNS = "ad_network";
		private String aerospikeSet = "ad_search_requests";
		private String aerospikeBin = "search_res";

		public String getAerospikeNS() {
			return aerospikeNS;
		}

		public void setAerospikeNS(String aerospikeNS) {
			this.aerospikeNS = aerospikeNS;
		}

		public String getAerospikeSet() {
			return aerospikeSet;
		}

		public void setAerospikeSet(String aerospikeSet) {
			this.aerospikeSet = aerospikeSet;
		}

		public String getAerospikeBin() {
			return aerospikeBin;
		}

		public void setAerospikeBin(String aerospikeBin) {
			this.aerospikeBin = aerospikeBin;
		}

		@Override
		public String toString() {
			return "SearchConfiguration [aerospikeNS=" + aerospikeNS + ", aerospikeSet=" + aerospikeSet + ", aerospikeBin=" + aerospikeBin + "]";
		}

	}

}
