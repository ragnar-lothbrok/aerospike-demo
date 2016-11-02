import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchResponse implements Serializable {
	private static final long serialVersionUID = -2478411489910608933L;
	private long pogId;
	private float probability;
	private List<String> supcs = new ArrayList<String>();

	public long getPogId() {
		return pogId;
	}

	public void setPogId(long pogId) {
		this.pogId = pogId;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}

	public List<String> getSupcs() {
		return supcs;
	}

	public void setSupcs(List<String> supcs) {
		this.supcs = supcs;
	}

	@Override
	public String toString() {
		return "SearchResponse{" + "pogId=" + pogId + ", probability=" + probability + ", supcs=" + supcs + '}';
	}
}
