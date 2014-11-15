package de.oszimt.DreiSchichten.controler;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// w3c-Standards
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * LastChange: 09.11.2014
 * @author Robert Schardt
 */

// To Do:
// - Überlegungen bzw. Entscheidungen zur Struktur der Datenbank im XML-Format treffen
// - Beispiel Code für Rückgabe, Zählen, Setzen, Hinzufügen und Entfernen in XML schreiben
// - zwei Methoden zur Initialisierung schreiben, einmal CreateDBStructure und FillDBWithTestData
// - 

public class DOMParser {
    
    public void CreateDatabase()
    {
        
        try {

              DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
              DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

              // root elements
              Document doc = docBuilder.newDocument();
              Element tableBeruf = doc.createElement("tableBeruf");
              doc.appendChild(tableBeruf);

              // staff elements
              Element entryBeruf = doc.createElement("entryBeruf");
              tableBeruf.appendChild(entryBeruf);
            
             entryBeruf.setAttribute("P_ID", "1");
              

              // FK_TYP_ID elements
              Element fkTypID = doc.createElement("FK_TYP_ID");
              fkTypID.appendChild(doc.createTextNode("1"));
              entryBeruf.appendChild(fkTypID);

              // FK_Mitglied_ID elements
              Element fkMitgliedID = doc.createElement("FK_Mitglied_ID");
              fkMitgliedID.appendChild(doc.createTextNode("1"));
              entryBeruf.appendChild(fkMitgliedID);

              // Punkte elements
              Element punkte = doc.createElement("Punkte");
              punkte.appendChild(doc.createTextNode("100"));
              entryBeruf.appendChild(punkte);

              // write the content into xml file
              TransformerFactory transformerFactory = TransformerFactory.newInstance();
              Transformer transformer = transformerFactory.newTransformer();
              DOMSource source = new DOMSource(doc);
              StreamResult result = new StreamResult(new File("D:\\lif_DB.xml"));

              // Output to console for testing
              // StreamResult result = new StreamResult(System.out);

              transformer.transform(source, result);

              System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
              pce.printStackTrace();
        } catch (TransformerException tfe) {
              tfe.printStackTrace();
        }
    }
    
    

    public void ReadDatabase()
    {
        try {

           File fXmlFile = new File("D:\\lif_DB.xml");
           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
           Document doc = dBuilder.parse(fXmlFile);

           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           doc.getDocumentElement().normalize();

           System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

           NodeList nList = doc.getElementsByTagName("entryBeruf");

           System.out.println("----------------------------");

           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);

                   System.out.println("\nCurrent Element :" + nNode.getNodeName());

                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;

                           System.out.println("P_ID : " + eElement.getAttribute("P_ID"));
                           System.out.println("FK_TYP_ID : " + eElement.getElementsByTagName("FK_TYP_ID").item(0).getTextContent());
                           System.out.println("FK_Mitglied_ID : " + eElement.getElementsByTagName("FK_Mitglied_ID").item(0).getTextContent());
                           System.out.println("Punkte : " + eElement.getElementsByTagName("Punkte").item(0).getTextContent());                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
}

