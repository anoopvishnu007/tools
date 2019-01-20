package sionea.sourcesearch.ui;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import sionea.sourcesearch.data.SearchResult;
import sionea.sourcesearch.db.ConnectionSingleton;

public class SearchContentProvider implements IStructuredContentProvider {

	private SearchResult[] list;

	public SearchContentProvider() {
		list = new SearchResult[0];
	}

	/**
	 * Gets the elements for the table
	 * 
	 * @param arg0
	 *            the model
	 * @return Object[]
	 */
	public Object[] getElements(Object arg0) {
		// Returns all the players in the specified team
		System.out.println("get Elements " + arg0);
		if (this.list == null) {
			this.list = new SearchResult[0];
		}
		return list;
	}

	/**
	 * Disposes any resources
	 */
	public void dispose() {
		// We don't create any resources, so we don't dispose any
	}
	public String getText(SearchResult res) {
		return ConnectionSingleton.getInstance().getText(res.getSourceId());
	}

	/**
	 * Called when the input changes
	 * 
	 * @param arg0
	 *            the parent viewer
	 * @param arg1
	 *            the old input
	 * @param arg2
	 *            the new input
	 */
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		if (arg2 != null) {
			
			ArrayList<SearchResult> list = ConnectionSingleton.getInstance().getResult((String)arg2);
			this.list = list.toArray(new SearchResult[list.size()]);
		}
		else {
			this.list = new SearchResult[0];
		}
			
		

	}
}
