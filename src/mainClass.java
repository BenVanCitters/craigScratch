import java.io.File;
import java.io.IOException;
import java.lang.System.*;

import javax.xml.parsers.ParserConfigurationException;

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
        Scratcher s = new Scratcher(url);
//        parseCLPage(url);
        
    }


    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
