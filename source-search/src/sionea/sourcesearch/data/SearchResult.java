package sionea.sourcesearch.data;

public class SearchResult {

	private int sourceId;
	private String sourceName;
	private String sourceType;
	private int count;
	private int ownerId;
	
	
	public String getSourceName() {
		return sourceName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public int getCount() {
		return count;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public int getSourceId() {
		return sourceId;
	}

	

	public SearchResult(int sourceId, String sourceName, String sourceType, int count, int ownerId) {
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.sourceType = sourceType;
		this.count = count;
		this.ownerId = ownerId;
	}

	@Override
	public String toString() {
		return sourceName + " [" + sourceType + "]";
	}
	
}
