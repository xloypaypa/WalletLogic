package tool;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class String2Element {
	public static Element toElement(String s){
		try {
			return DocumentHelper.parseText(s).getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String toString(Element message){
		return message.asXML();
	}
}
