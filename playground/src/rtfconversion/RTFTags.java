package rtfconversion;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RTFTags {

	static Map<String, xhtmlElement> mappings = new HashMap<>();
	
	static String test = "{\\rtf1\\ansi\\deff0 {\\fonttbl {\\f0 Times New Roman;}}\\f0\\fs60 Hello, World!}";
	private static String regex = "(\\\\[^\\\\{\\s]+)";

	static {
		mappings.put("\\f0", new xhtmlAttribute("style", "font-family: Times New Roman"));
		mappings.put("\\fs22", new xhtmlAttribute("style", "font-size: 11pt;"));
		mappings.put("\\fs60", new xhtmlAttribute("style", "font-size: 30pt;"));
	}
	
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(test);
		while (matcher.find()) {
	      System.out.print("Start index: " + matcher.start());
	      System.out.print(" End index: " + matcher.end() + " ");
	      xhtmlElement element = parseRTFToXHTML(matcher.group());
	      if(element != null){
	    	  System.out.print(" matcher group: " + matcher.group());
	      }else{
	      System.out.println(" matcher group: " + matcher.group());
	      }
		}
	}
	
	static xhtmlElement parseRTFToXHTML(String rtfTag){
		return mappings.get(rtfTag);
	}
	
	private static class xhtmlAttribute implements xhtmlElement{
		String value;
		String name;

		public xhtmlAttribute(String name, String value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString() {
			return "xhtmlAttribute [value=" + value + ", name=" + name
					+  "]";
		}
	}
	
	private static class xhtmlTag implements xhtmlElement{
		String name;
		String openTag;
		String closeTag;
		
		public xhtmlTag(String name, String openTag, String closeTag) {
			this.name = name;
			this.openTag = openTag;
			this.closeTag = closeTag;
		}

		@Override
		public String toString() {
			return "xhtmlTag [name=" + name + ", openTag=" + openTag
					+ ", closeTag=" + closeTag + "]";
		}
	}
	
	private static interface xhtmlElement {
		
	}
}
