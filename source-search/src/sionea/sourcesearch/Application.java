package sionea.sourcesearch;

//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;

//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.IndexableField;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) {
		Display display = PlatformUI.createDisplay();
		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			
//			try {
//				testLucene();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	@Override
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning())
			return;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
	/*
	private void testLucene() throws IOException, ParseException {

		
		// New index
				StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
				Directory directory = FSDirectory.open(Paths.get("F:\\workspace\\sourcesearch\\index"));
				
				Files.walk(Paths.get("F:\\workspace\\sourcesearch\\index"))
		        .filter(Files::isRegularFile)
		        .map(Path::toFile)
		        .forEach(File::delete);
				
				IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);
				
				
				// Create a writer
				IndexWriter writer = new IndexWriter(directory, config);
				Document document = new Document();
				// In a real world example, content would be the actual content that needs to be
				// indexed.
				// Setting content to Hello World as an example.
				document.add(new TextField("name", "TEST_SOURCE", Field.Store.YES));
				document.add(new TextField("type", "BUSINESS TYPE CONFIG", Field.Store.YES));
				document.add(new TextField("content", "Hello World", Field.Store.YES));
				writer.addDocument(document);
				
				document = new Document();
				document.add(new TextField("name", "VP$XZZ", Field.Store.YES));
				document.add(new TextField("type", "AVALOQ SCRIPT PACKAGE", Field.Store.YES));
				document.add(new TextField("content", "Hello People", Field.Store.YES));
				writer.addDocument(document);
				writer.close();

				// Now let's try to search for Hello
				IndexReader reader = DirectoryReader.open(directory);
				IndexSearcher searcher = new IndexSearcher(reader);
				QueryParser parser = new QueryParser("content", standardAnalyzer);
				Query query = parser.parse("Hello");
				TopDocs results = searcher.search(query, 5);
				System.out.println("Hits for Hello -->" + results.totalHits);

				// case insensitive search
				query = parser.parse("world");
				results = searcher.search(query, 5);
				System.out.println("Hits for world -->" + results.totalHits);
				for (ScoreDoc d : results.scoreDocs) {
					System.out.println("Score doc " + d.toString() + ",");
					Document doc = reader.document(d.doc);
					for (IndexableField f : doc.getFields("content")) {
						System.out.println("content="+f.stringValue());
					}
					for (IndexableField f : doc.getFields("name")) {
						System.out.println("name="+f.stringValue());
					}
					System.out.println("d " + doc.getField("content"));
				}

				// search for a value not indexed
				query = parser.parse("people");
				results = searcher.search(query, 5);
				System.out.println("Hits for le peo -->" + results.totalHits);
	}
	*/
}
