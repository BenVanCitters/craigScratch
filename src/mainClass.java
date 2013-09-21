import java.io.File;
import java.io.IOException;
import java.lang.System.*;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class mainClass {
	public static void main(String [] args) throws IOException
	{
		Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        print("Fetching %s...", url);
        Document doc = null;
        try
        {
        	doc = Jsoup.connect(url).get();        	
        }
        catch(Exception e)
        {        	
        	System.out.println(e);
        	System.out.println("exception caught! - opening from disk");	
        	File input = new File ("/Users/admin/Documents/workspace/craigScratch/craigslist page/site.html");
        	doc = Jsoup.parse(input, "UTF-8", "");
        }

        
        Elements nxtSpans = doc.select("span.nplink");
        for (Element nxtSpan : nxtSpans) {
        	Elements nxtLinks = nxtSpan.select("a[href]");
        	for (Element nxLnk : nxtLinks) {
        		print(" * <%s>",nxLnk.attr("abs:href"));
        	}
        }
        
        Elements links = doc.select("p.row");

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
        		//continue;
        	}
            
            Elements price = link.select("span.price");
            for (Element p : price)
            {
            	String txt1 = p.text();
            	cost = Float.parseFloat(txt1.substring(1, txt1.length()));
            	//print("txt: %f",cost);
//            	link.attr("data-latitude");
            }
            
            print(" *%s lat,long: %f, %f price $%.2f", pid,lat,lng,cost);
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
