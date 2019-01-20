package sionea.sourcesearch.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import sionea.sourcesearch.data.SearchResult;

public class SearchLabelProvider implements ITableLabelProvider {

	// Image to display if the player led his team
	private Image icon;

	public SearchLabelProvider() {
		try {
			icon = new Image(null, new FileInputStream("icons/remove.gif"));
		} catch (FileNotFoundException e) {
			// Swallow it
		}
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		if (icon != null) {
			icon.dispose();
		}
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		SearchResult res = (SearchResult) element;
		Image image = null;
		switch (columnIndex) {
		// A player can't lead team in first name or last name
		case 1:
		case 2:
			if (true)
				// Set the image
				image = icon;
			break;
		}
		return image;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		SearchResult res = (SearchResult) element;
		String text = "";
		switch (columnIndex) {
		case 0:
			text = res.getSourceName();
			break;
		case 1:
			text = res.getSourceType();
			break;
		case 2:
			text = Integer.toString(res.getOwnerId());
			break;
		case 3:
			text = Integer.toString(res.getCount());
			break;
		}
		return text;
	}

}
