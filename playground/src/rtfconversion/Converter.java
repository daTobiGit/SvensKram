package rtfconversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;

public class Converter {

	public static String rtfToHtml(Reader rtf) throws IOException {
		JEditorPane p = new JEditorPane();
		p.setContentType("text/rtf");
		EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
		try {
			kitRtf.read(rtf, p.getDocument(), 0);
			kitRtf = null;
			EditorKit kitHtml = p.getEditorKitForContentType("text/html");
			Writer writer = new StringWriter();
			kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());
			return writer.toString();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String htmlToRtf(Reader html) throws IOException {
		JEditorPane p = new JEditorPane();
		p.setContentType("text/html");
		EditorKit kitRtf = p.getEditorKitForContentType("text/html");
		try {
			kitRtf.read(html, p.getDocument(), 0);
			kitRtf = null;
			EditorKit kitHtml = p.getEditorKitForContentType("text/rtf");
			Writer writer = new StringWriter();
			kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());
			return writer.toString();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		FileReader fileReader = new FileReader(new File("H:/My Documents/Requirement_Dokuments/E_Texte_rtf/ESP_ESP_80014098.rtf"));
		String html = Converter.rtfToHtml(fileReader);
		System.out.println(html);
		fileReader.close();
		FileWriter fileWriter = new FileWriter("H:/My Documents/Requirement_Dokuments/E_Texte_rtf/ESP_ESP_80014098.html");
		fileWriter.write(html);
		fileWriter.flush();
		fileWriter.close();
//		
//		System.out.println("----------------------------------------------");
//		System.out.println("----------------------------------------------");
//		
//		FileReader reader = new FileReader("H:/My Documents/Requirement_Dokuments/E_Texte_rtf/ENS_IN_M10080310.html");
//		String rtf = Converter.htmlToRtf(reader);
//		System.out.println(rtf);
//		
//		fileWriter = new FileWriter("H:/My Documents/Requirement_Dokuments/E_Texte_rtf/ENS_IN_M10080310_test.rtf");
//		fileWriter.write(rtf);
//		fileWriter.flush();
//		fileWriter.close();
		
//		String html = Converter.rtfToHtml(new StringReader("{\\rtf1\\ansi\\deff0 {\\fonttbl {\\f0 Times New Roman;}}\\qc\\f0\\fs60 Hello, World!}"));
//		System.out.println(html);
	}
}
