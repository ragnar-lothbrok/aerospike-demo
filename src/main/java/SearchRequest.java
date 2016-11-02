import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchRequest implements Serializable {

	private static final long serialVersionUID = -7018374703636567684L;
	private int pageNo = 0;
	private int pageSize;
	private String keyWord;
	private String queryText;
	private String categoryPath;
	private Map<String, String> filterMap = new HashMap<String, String>();

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public Map<String, String> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, String> filterMap) {
		this.filterMap = filterMap;
	}

	@Override
	public String toString() {
		return "SearchRequest [pageNo=" + pageNo + ", pageSize=" + pageSize + ", keyWord=" + keyWord + ", queryText=" + queryText + ", categoryPath="
				+ categoryPath + ", filterMap=" + filterMap + "]";
	}

}
