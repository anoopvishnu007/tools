package sionea.sourcesearch;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Table;

public class SourceSearchColumnLabelProvider extends ColumnLabelProvider {

		private Table table;

		public SourceSearchColumnLabelProvider(Table table) {
			this.table = table;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ColumnLabelProvider#getBackground(java.lang.Object)
		 */
		@Override
		public Color getBackground(Object element) {
			Color color = table.getDisplay().getSystemColor(SWT.COLOR_GRAY);

			return  color;
		}

		 
	}