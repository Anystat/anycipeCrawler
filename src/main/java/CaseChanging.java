import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * Created by Lofv on 13.11.2016.
 */
public class CaseChanging {
    private Document doc = null;

    // CaseChanging caseChanging=new CaseChanging("курицы");
    //System.out.println("Root element is "+caseChanging.getRootElement());
    //System.out.println(caseChanging.getMorph("И"));

    public CaseChanging(String word) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

            doc = builder.parse("http://morpher.ru/WebService.asmx/GetXml?s="+word);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    public String getRootElement() {
        String result = "";
        if (null != doc) {
            Element el = doc.getDocumentElement();
            result = el.getTagName();
        }
        return result;
    }


    public String getMorph(String p) {
        String result = "";
        if (null != doc) {
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            for (int x = 0; x < nodes.getLength(); x++) {
                Node item = nodes.item(x);
                if (item instanceof Element) {
                    Element el = ((Element)item);
                    if (el.getTagName().equals(p)) {
                        result = ((Text)el.getFirstChild()).getData().trim();
                    }
                }
            }
        }
        return result;
    }

}
