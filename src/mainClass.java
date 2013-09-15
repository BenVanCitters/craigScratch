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
        catch(java.lang.IllegalArgumentException e)
        {
        	System.out.println(e);
        }
//        finally
//        {
//        	System.out.println("finally");
//        	File input = new File(url);
//        	doc = Jsoup.parse(input, "UTF-8", null);	
//        }
        Elements links = doc.select("p.row");
/*
 * <p class="row" data-latitude="47.675818130271" 
 * data-longitude="-122.398223569797" 
 * data-pid="4051365390"> <a href="/see/apa/4051365390.html" class="i"></a>
 *  <span class="pl"> <span class="star v" title="save this post in your favorites list"></span> <span class="date">Sep  6</span>  <a href="/see/apa/4051365390.html">Ballard Craftsman 3BR 1.5 Bath</a> </span> <span class="l2"> 
 *   <span class="price">$2400</span> / 3br -  <span class="pnr"> <small> (3047 NW 65th. St.)</small> <span class="px"> <span class="p"> <a href="#" class="maptag" data-pid="4051365390">map</a></span></span> </span>  </span> </p>
 */
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
        	float lat =0;
        	float lng = 0;
        	float cost = 0;
        	String txt = link.attr("data-latitude");
        	if(txt != "")
        	{
        		lat = Float.parseFloat(txt);
        	}
        	txt = link.attr("data-longitude");
        	if(txt != "")
        	{
        		lng = Float.parseFloat(txt);
        	}
            
            Elements price = link.select("span.price");
            for (Element p : price)
            {
            	String txt1 = p.text();
            	cost = Float.parseFloat(txt1.substring(1, txt1.length()));
            	//print("txt: %f",cost);
//            	link.attr("data-latitude");
            }
            print(" * lat,long: %f, %f price $%f", lat,lng,cost);
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
