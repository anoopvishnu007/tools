package sionea.sourcesearch;
/**
 * 
 * @author Anoopvn
 *
 */
public class SearchData {
	
	private String searchString;
	private String sourceNameFilter;
	private String filterOwner;
	private String filterPackage;
	private SearchData next;
	private SearchData previous;
	/**
	 * @return the searchString
	 */
	public String getSearchString() {
		return searchString;
	}
	/**
	 * @param searchString the searchString to set
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	/**
	 * @return the sourceNameFilter
	 */
	public String getSourceNameFilter() {
		return sourceNameFilter;
	}
	/**
	 * @param sourceNameFilter the sourceNameFilter to set
	 */
	public void setSourceNameFilter(String sourceNameFilter) {
		this.sourceNameFilter = sourceNameFilter;
	}
	/**
	 * @return the filterOwner
	 */
	public String getFilterOwner() {
		return filterOwner;
	}
	/**
	 * @param filterOwner the filterOwner to set
	 */
	public void setFilterOwner(String filterOwner) {
		this.filterOwner = filterOwner;
	}
	/**
	 * @return the filterPackage
	 */
	public String getFilterPackage() {
		return filterPackage;
	}
	/**
	 * @param filterPackage the filterPackage to set
	 */
	public void setFilterPackage(String filterPackage) {
		this.filterPackage = filterPackage;
	}
	/**
	 * @return the next
	 */
	public SearchData getNext() {
		return next;
	}
	/**
	 * @param next the next to set
	 */
	public void setNext(SearchData next) {
		this.next = next;
	}
	/**
	 * @return the previous
	 */
	public SearchData getPrevious() {
		return previous;
	}
	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(SearchData previous) {
		this.previous = previous;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filterOwner == null) ? 0 : filterOwner.hashCode());
		result = prime * result + ((filterPackage == null) ? 0 : filterPackage.hashCode());
		result = prime * result + ((searchString == null) ? 0 : searchString.hashCode());
		result = prime * result + ((sourceNameFilter == null) ? 0 : sourceNameFilter.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchData other = (SearchData) obj;
		if (filterOwner == null) {
			if (other.filterOwner != null)
				return false;
		} else if (!filterOwner.equals(other.filterOwner))
			return false;
		if (filterPackage == null) {
			if (other.filterPackage != null)
				return false;
		} else if (!filterPackage.equals(other.filterPackage))
			return false;
		if (searchString == null) {
			if (other.searchString != null)
				return false;
		} else if (!searchString.equals(other.searchString))
			return false;
		if (sourceNameFilter == null) {
			if (other.sourceNameFilter != null)
				return false;
		} else if (!sourceNameFilter.equals(other.sourceNameFilter))
			return false;
		return true;
	}
	 
	

}
