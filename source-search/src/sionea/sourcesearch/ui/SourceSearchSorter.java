package sionea.sourcesearch.ui;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import sionea.sourcesearch.data.SearchResult;

public class SourceSearchSorter extends ViewerComparator {
	private static final int ASCENDING = 0;

	private static final int DESCENDING = 1;

	private int column;

	private int direction;

	/**
	 * Does the sort. If it's a different column from the previous sort, do an
	 * ascending sort. If it's the same column as the last sort, toggle the sort
	 * direction.
	 * 
	 * @param column
	 */
	public void doSort(int column) {
		if (column == this.column) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.column = column;
			direction = ASCENDING;
		}
	}

	/**
	 * Compares the object for sorting
	 */
	public int compare(Viewer viewer, Object e1, Object e2) {
		int rc = 0;
		SearchResult p1 = (SearchResult) e1;
		SearchResult p2 = (SearchResult) e2;

		// Determine which column and do the appropriate sort
		switch (column) {
		case SourceSearchView.COLUMN_SOURCE_NAME:
			rc = p1.getSourceName().compareTo(p2.getSourceName());
			break;
		case SourceSearchView.COLUMN_SOURCE_TYPE:
			rc = p1.getSourceType().compareTo(p2.getSourceType());
			break;
		case SourceSearchView.COLUMN_SOURCE_OWNER:
			rc = p1.getOwnerId() > p2.getOwnerId() ? 1 : -1;
			break;
		case SourceSearchView.COLUMN_SOURCE_COUNT:
			rc = p1.getCount() > p2.getCount() ? 1 : -1;
			break;
		}

		// If descending order, flip the direction
		if (direction == DESCENDING)
			rc = -rc;

		return rc;
	}
	public int getDirection(){
		if (direction == DESCENDING){
			return SWT.DOWN;
		}else{
			return SWT.UP;
		}
	}
}
