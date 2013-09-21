import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Scratcher {
	org.w3c.dom.Document mDoc;
	org.w3c.dom.Element mRootElement;
	
	public Scratcher(String url)
	{
		try {
			createXMLDoc();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try 
		{			
			parseCLPage(url);
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveXMLFile();
		
	}

	public void parseCLPage(String url) throws IOException{
		Document doc = null;
        try
        {
        	print("Fetching %s...", url);
        	doc = Jsoup.connect(url).get();        	
        }
        catch(Exception e)
        {        	
        	System.out.println(e);
        	System.out.println("exception caught! - opening from disk");	
        	File input = new File ("/Users/admin/Documents/workspace/craigScratch/craigslist page/site.html");
        	doc = Jsoup.parse(input, "UTF-8", "");
        }
        parseCLPage(doc);
		
	}
	


/* <p class="row" data-latitude="47.675818130271" data-longitude="-122.398223569797" data-pid="4051365390"> 
 *   <a href="/see/apa/4051365390.html" class="i"></a>
 *   <span class="pl"> 
 *      <span class="star v" title="save this post in your favorites list"></span> 
 *      <span class="date">Sep  6</span> 
 *      <a href="/see/apa/4051365390.html">Ballard Craftsman 3BR 1.5 Bath</a> 
 *   </span> 
 *  <span class="l2"> 
 *    <span class="price">$2400</span> / 3br -  
 *      <span class="pnr"> 
 *        <small> (3047 NW 65th. St.)</small> 
 *        <span class="px"> 
 *          <span class="p"> 
 *          <a href="#" class="maptag" data-pid="4051365390">map</a>
 *        </span>
 *      </span> 
 *    </span>  
 *  </span> 
 *  </p>
 */     
	public void parseCLPage(Document doc) throws IOException{                        
        		
		Elements links = doc.select("p.row");
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
        	float lat =0;
        	float lng = 0;
        	float cost = 0;
        	String pid = link.attr("data-pid");
        	String lattxt = link.attr("data-latitude");
        	String lngtxt = link.attr("data-longitude");
        	if((lattxt != "")&&(lngtxt != ""))
        	{
        		lat = Float.parseFloat(lattxt);
        		lng = Float.parseFloat(lngtxt);
        	}
        	else
        	{
        		continue;
        	}
            
            Elements price = link.select("span.price");
            for (Element p : price)
            {
            	String txt1 = p.text();
            	cost = Float.parseFloat(txt1.substring(1, txt1.length()));
            	//print("txt: %f",cost);
//            	link.attr("data-latitude");
            }
            addListingXML( pid,lat,lng,cost);
            print(" *%s lat,long: %f, %f price $%.2f", pid,lat,lng,cost);
        }
        
        String nextURL = null;
        Elements nxtSpans = doc.select("span.nplink");
        for (Element nxtSpan : nxtSpans) {
        	Elements nxtLinks = nxtSpan.select("a[href]");
        	String title = nxtSpan.attr("title");
        	if(title.contains("next"))
        	{
        		for (Element nxLnk : nxtLinks) {
	        		nextURL = nxLnk.attr("abs:href");
	        		print(" * <%s>",nextURL);        		
                	parseCLPage(Jsoup.connect(nextURL).get());
                	return;
            	}
        	}
        	
        }
        
		return; //we only need to follow one "next" link
	}
	
	
	private void createXMLDoc() throws ParserConfigurationException
	{
		javax.xml.parsers.DocumentBuilderFactory docFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		javax.xml.parsers.DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		mDoc = docBuilder.newDocument();
		mRootElement = mDoc.createElement("CLDataPoints");
		mDoc.appendChild(mRootElement);
	}
	
	private void addListingXML(String pid, float lat, float lon, float cost)
	{
		org.w3c.dom.Element listing = mDoc.createElement("Listing");
		mRootElement.appendChild(listing);
 
		listing.setAttribute("pid", pid);
		listing.setAttribute("lat", Float.toString(lat));
		listing.setAttribute("lon", Float.toString(lon));
		listing.setAttribute("cost", Float.toString(cost));
	}
	private void saveXMLFile()
	{
		javax.xml.transform.TransformerFactory transformerFactory = 
				javax.xml.transform.TransformerFactory.newInstance();
		javax.xml.transform.Transformer transformer = null;
		try 
		{
			transformer = transformerFactory.newTransformer();
		} 
		catch (TransformerConfigurationException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(mDoc);
		javax.xml.transform.stream.StreamResult result = 
				new javax.xml.transform.stream.StreamResult(new File("file.xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		try 
		{
			transformer.transform(source, result);
		}
		catch (TransformerException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		System.out.println("File saved!");
	}
	
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
	
}
