package de.oszimt.DreiSchichten.controller;

import java.io.File;
import java.io.IOException;
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

// Model-Imports
import de.oszimt.DreiSchichten.model.Beruf;
import de.oszimt.DreiSchichten.model.Berufstyp;
import de.oszimt.DreiSchichten.model.Dorf;
import de.oszimt.DreiSchichten.model.Lager;
import de.oszimt.DreiSchichten.model.LagerBestand;
import de.oszimt.DreiSchichten.model.Mitglied;
import de.oszimt.DreiSchichten.model.Ressource;


/**
 * LastChange: 09.11.2014
 * @author Robert Schardt
 */

// To Do:
// - XML-Pfad anpassen
// - XML-Datei mit Testdaten füllen

public class XMLAccess {
    private File m_XMLFile;
    private DocumentBuilderFactory m_DBFactory;
    private DocumentBuilder m_DocBuilder;
    private Document m_Doc;
    
    public XMLAccess()
    {               
        try
        {
            m_XMLFile = new File("D:\\lif_DB.xml");
            m_DBFactory = DocumentBuilderFactory.newInstance();
            m_DocBuilder = m_DBFactory.newDocumentBuilder();
            m_Doc = m_DocBuilder.parse(m_XMLFile);       
            
        } catch (Exception e) {
           e.printStackTrace();
       }         
    }
    
   
    /////// Database-Operations
    //////////////////////////
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

              // Veränderung in XML-Datei übernehmen
              WriteChangetoXMLFile();

        } catch (Exception e) {
           e.printStackTrace();
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
    
    public void WriteChangetoXMLFile()
    {
        try{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(m_Doc);
        StreamResult result = new StreamResult(new File("D:\\lif_DB.xml"));
        transformer.transform(source, result);
        } catch (Exception e) {
           e.printStackTrace();
       }         
    }
    //////////////////////////
    
    
    /////// Beruf
    //////////////////////////
    public Beruf GetBeruf(int berufID)
    {
        Beruf curBeruf = new Beruf(-1, -1, -1, -1);
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBeruf";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);
                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           
                           
                           Integer pID = Integer.parseInt(eElement.getAttribute("P_ID"));
                           if(pID == berufID)
                           {
                                curBeruf.setId(pID);
                                curBeruf.setTypID(Integer.parseInt(eElement.getElementsByTagName("FK_TYP_ID").item(0).getTextContent()));
                                curBeruf.setMitgliedID(Integer.parseInt(eElement.getElementsByTagName("FK_Mitglied_ID").item(0).getTextContent()));
                                curBeruf.setPunkte(Integer.parseInt(eElement.getElementsByTagName("Punkte").item(0).getTextContent()));
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return curBeruf;
    }
    
    public int GetBerufCount()
    {         
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBeruf";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
           return nList.getLength();
           
       } catch (Exception e) {
           e.printStackTrace();
       }    
         
       return 0;
    }
    
    public void SetBeruf(Beruf curBeruf)
    {
            
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBeruf";
           NodeList nList = m_Doc.getElementsByTagName(tableName);                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                                                   
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));                                                          
                           if(curId == curBeruf.getId())
                           {
                                Integer curPunkte = new Integer(curBeruf.getPunkte());
                                eElement.getElementsByTagName("Punkte").item(0).setTextContent(curPunkte.toString());                                
                           }
                   }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
           
       } catch (Exception e) {
           e.printStackTrace();                  
       }
    }
    
    public int AddBeruf(Beruf newBeruf)
    {
         Integer pID = -1;
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBeruf";
             
           NodeList nList = m_Doc.getElementsByTagName("tableBeruf");
           
           for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);                  
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        Element entryBeruf = m_Doc.createElement(tableName);
                        eElement.appendChild(entryBeruf);
        
                      
                        // P_ID
                        pID = GetBerufCount();
                        entryBeruf.setAttribute("P_ID", pID.toString());

                        // FK_TYP_ID elements
                        Integer TypID = newBeruf.getTypID();
                        Element fkTypID = m_Doc.createElement("FK_TYP_ID");
                        fkTypID.appendChild(m_Doc.createTextNode(TypID.toString()));
                        entryBeruf.appendChild(fkTypID);

                        // FK_Mitglied_ID elements
                        Integer MitgliedID = newBeruf.getMitgliedID();
                        Element fkMitgliedID = m_Doc.createElement("FK_Mitglied_ID");
                        fkMitgliedID.appendChild(m_Doc.createTextNode(MitgliedID.toString()));
                        entryBeruf.appendChild(fkMitgliedID);

                        // Punkte elements
                        Integer pkt = newBeruf.getPunkte();
                        Element punkte = m_Doc.createElement("Punkte");
                        punkte.appendChild(m_Doc.createTextNode(pkt.toString()));
                        entryBeruf.appendChild(punkte);

                        
                    }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
                       
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return pID;
    }
    
    public void RemoveBeruf(int berufID)
    {
        try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBeruf";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));    
                           if(curId == berufID)
                           {                                                              
                               nNode.getParentNode().removeChild(eElement);
                               return;
                           }                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }                       
    }
    //////////////////////////
            
    
    /////Berufstyp
    //////////////////////////
     public Berufstyp GetBerufstyp(int berufstypID)
    {
        Berufstyp curBerufstyp = new Berufstyp(-1, "", -1, -1, -1, -1, 1);
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBerufstyp";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);
                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           
                           
                           Integer pID = Integer.parseInt(eElement.getAttribute("P_ID"));
                           if(pID == berufstypID)
                           {
                                curBerufstyp.setId(pID);
                                curBerufstyp.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                                curBerufstyp.setSk1(Integer.parseInt(eElement.getElementsByTagName("SK1").item(0).getTextContent()));
                                curBerufstyp.setSk2(Integer.parseInt(eElement.getElementsByTagName("SK2").item(0).getTextContent()));
                                curBerufstyp.setSk3(Integer.parseInt(eElement.getElementsByTagName("SK3").item(0).getTextContent()));
                                curBerufstyp.setSk4(Integer.parseInt(eElement.getElementsByTagName("SK4").item(0).getTextContent()));
                                curBerufstyp.setSk5(Integer.parseInt(eElement.getElementsByTagName("SK5").item(0).getTextContent()));
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return curBerufstyp;
    }
    
    public int GetBerufstypCount()
    {         
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBerufstyp";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
           return nList.getLength();
           
       } catch (Exception e) {
           e.printStackTrace();
       }    
         
       return 0;
    }
    
    public void SetBerufstyp(Berufstyp curBerufstyp)
    {
            
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBerufstyp";
           NodeList nList = m_Doc.getElementsByTagName(tableName);                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                                                   
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));                                                          
                           if(curId == curBerufstyp.getId())
                           {
                               eElement.getElementsByTagName("Name").item(0).setTextContent(curBerufstyp.getName());     
                               
                                Integer curSK1 = new Integer(curBerufstyp.getSk1());                                 
                                eElement.getElementsByTagName("SK1").item(0).setTextContent(curSK1.toString());
                                
                                Integer curSK2 = new Integer(curBerufstyp.getSk2());                                 
                                eElement.getElementsByTagName("SK2").item(0).setTextContent(curSK2.toString());
                                
                                Integer curSK3 = new Integer(curBerufstyp.getSk3());                                 
                                eElement.getElementsByTagName("SK3").item(0).setTextContent(curSK3.toString());
                                
                                Integer curSK4 = new Integer(curBerufstyp.getSk4());                                 
                                eElement.getElementsByTagName("SK4").item(0).setTextContent(curSK4.toString());
                                
                                Integer curSK5 = new Integer(curBerufstyp.getSk5());                                 
                                eElement.getElementsByTagName("SK5").item(0).setTextContent(curSK5.toString());                                
                           }
                   }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
           
       } catch (Exception e) {
           e.printStackTrace();                  
       }
    }
    
    public int AddBerufstyp(Berufstyp newBerufstyp)
    {
         Integer pID = -1;
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBerufstyp";
             
           NodeList nList = m_Doc.getElementsByTagName("tableBerufstyp");
           
           for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);                  
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        Element entryBerufstyp = m_Doc.createElement(tableName);
                        eElement.appendChild(entryBerufstyp);
        
                      
                        // P_ID
                        pID = GetBerufCount();
                        entryBerufstyp.setAttribute("P_ID", pID.toString());
                        
                        String strName = newBerufstyp.getName();
                        Element nameNode = m_Doc.createElement("Name");
                        nameNode.appendChild(m_Doc.createTextNode(strName));
                        entryBerufstyp.appendChild(nameNode);
                        
                        Integer strSK1 = newBerufstyp.getSk1();
                        Element sk1Node = m_Doc.createElement("SK1");
                        sk1Node.appendChild(m_Doc.createTextNode(strSK1.toString()));
                        entryBerufstyp.appendChild(sk1Node);

                         Integer strSK2 = newBerufstyp.getSk2();
                        Element sk2Node = m_Doc.createElement("SK2");
                        sk1Node.appendChild(m_Doc.createTextNode(strSK2.toString()));
                        entryBerufstyp.appendChild(sk2Node);
                        
                         Integer strSK3 = newBerufstyp.getSk3();
                        Element sk3Node = m_Doc.createElement("SK3");
                        sk1Node.appendChild(m_Doc.createTextNode(strSK3.toString()));
                        entryBerufstyp.appendChild(sk3Node);
                        
                         Integer strSK4 = newBerufstyp.getSk4();
                        Element sk4Node = m_Doc.createElement("SK4");
                        sk4Node.appendChild(m_Doc.createTextNode(strSK4.toString()));
                        entryBerufstyp.appendChild(sk4Node);
                        
                         Integer strSK5 = newBerufstyp.getSk5();
                        Element sk5Node = m_Doc.createElement("SK5");
                        sk1Node.appendChild(m_Doc.createTextNode(strSK5.toString()));
                        entryBerufstyp.appendChild(sk5Node);                        
                    }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
                       
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return pID;
    }
    
    public void RemoveBerufstyp(int berufstypID)
    {
        try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryBerufstyp";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));    
                           if(curId == berufstypID)
                           {                                                              
                               nNode.getParentNode().removeChild(eElement);
                               return;
                           }                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }                       
    }
    //////////////////////////
    
    
    //////// Dorf
    //////////////////////////
    public Dorf GetDorf(int dorfID)
    {
        Dorf curDorf = new Dorf(-1, "");
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryDorf";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);
                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           
                           
                           Integer pID = Integer.parseInt(eElement.getAttribute("P_ID"));
                           if(pID == dorfID)
                           {
                                curDorf.setId(pID);
                                curDorf.setName(eElement.getElementsByTagName("FK_TYP_ID").item(0).getTextContent());                        
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return curDorf;
    }
    
    public int GetDorfCount()
    {         
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryDorf"; // Edit--Marker
           NodeList nList = m_Doc.getElementsByTagName(tableName);
           return nList.getLength();
           
       } catch (Exception e) {
           e.printStackTrace();
       }    
         
       return 0;
    }
    
    public void SetDorf(Dorf curDorf)
    {
            
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryDorf";
           NodeList nList = m_Doc.getElementsByTagName(tableName);                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;                                                   
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));                                                          
                           if(curId == curDorf.getId())
                           {                                
                                eElement.getElementsByTagName("Name").item(0).setTextContent(curDorf.getName());                                
                           }
                   }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
           
       } catch (Exception e) {
           e.printStackTrace();                  
       }
    }
    
    public int AddDorf(Dorf newDorf)
    {
         Integer pID = -1;
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryDorf"; 
             
           NodeList nList = m_Doc.getElementsByTagName("tableDorf");
           
           for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);                  
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        Element entryDorf = m_Doc.createElement(tableName);
                        eElement.appendChild(entryDorf);
                            
                        // P_ID
                        pID = GetBerufCount();
                        entryDorf.setAttribute("P_ID", pID.toString());
                    
                        String strName = newDorf.getName();
                        Element nameNode = m_Doc.createElement("Name");
                        nameNode.appendChild(m_Doc.createTextNode(strName));
                        entryDorf.appendChild(nameNode);
                    }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
                       
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return pID;
    }
    
    public void RemoveDorf(int dorfID)
    {
        try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryDorf"; // Edit--Marker
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));    
                           if(curId == dorfID)
                           {                                                              
                               nNode.getParentNode().removeChild(eElement);
                               return;
                           }                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }                       
    }
    //////////////////////////
    
    
    //////// Lager
    //////////////////////////
    public Lager GetLager(int lagerID)
    {
        Lager curLager = new Lager(-1, -1, "");
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLager";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);
                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;

                           Integer pID = Integer.parseInt(eElement.getAttribute("P_ID"));
                           if(pID == lagerID)
                           {
                                curLager.setId(pID);
                                curLager.setDorfId(Integer.parseInt(eElement.getElementsByTagName("FK_DORF_ID").item(0).getTextContent()));
                                curLager.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());                                
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return curLager;
    }
    
    public int GetLagerCount()
    {         
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLager";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
           return nList.getLength();
           
       } catch (Exception e) {
           e.printStackTrace();
       }    
         
       return 0;
    }
    
    public void SetLager(Lager curLager)
    {            
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLager"; 
           NodeList nList = m_Doc.getElementsByTagName(tableName);                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                                                   
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));                                                          
                           if(curId == curLager.getId())
                           {
                                Integer fkDorfID = new Integer(curLager.getDorfId());
                                eElement.getElementsByTagName("FK_DORF_ID").item(0).setTextContent(fkDorfID.toString());                 
                                
                                eElement.getElementsByTagName("Name").item(0).setTextContent(curLager.getName());                 
                           }
                   }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
           
       } catch (Exception e) {
           e.printStackTrace();                  
       }
    }
    
    public int AddLager(Lager newLager)
    {
         Integer pID = -1;
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLager";
             
           NodeList nList = m_Doc.getElementsByTagName("tableLager");
           for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);                  
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        Element entryLager = m_Doc.createElement(tableName);
                        eElement.appendChild(entryLager);
                              
                        // P_ID
                        pID = GetBerufCount();
                        entryLager.setAttribute("P_ID", pID.toString());
                       
                        Integer fkDorfID = newLager.getDorfId();
                        Element DorfID = m_Doc.createElement("FK_DORF_ID");
                        DorfID.appendChild(m_Doc.createTextNode(fkDorfID.toString()));
                        entryLager.appendChild(DorfID);

                        String strName = newLager.getName();
                        Element nameNode = m_Doc.createElement(strName);
                        nameNode.appendChild(m_Doc.createTextNode(strName));
                        entryLager.appendChild(nameNode);
                    }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
                       
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return pID;
    }
    
    public void RemoveLager(int lagerID)
    {
        try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLager"; // Edit--Marker
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));    
                           if(curId == lagerID)
                           {                                                              
                               nNode.getParentNode().removeChild(eElement);
                               return;
                           }                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }                       
    }
    //////////////////////////
    
    
    //////// LagerBestand
    //////////////////////////
    public LagerBestand GetLagerBestand(int lagerbestandID)
    {
        LagerBestand curLagerBestand = new LagerBestand(-1, -1, -1, -1);
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLagerBestand";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);
                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           
                           
                           Integer pID = Integer.parseInt(eElement.getAttribute("P_ID"));
                           if(pID == lagerbestandID)
                           {
                                curLagerBestand.setId(pID);
                                curLagerBestand.setResId(Integer.parseInt(eElement.getElementsByTagName("FK_RES_ID").item(0).getTextContent()));
                                curLagerBestand.setLagerId(Integer.parseInt(eElement.getElementsByTagName("FK_LAGER_ID").item(0).getTextContent()));
                                curLagerBestand.setMenge(Integer.parseInt(eElement.getElementsByTagName("Menge").item(0).getTextContent()));
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return curLagerBestand;
    }
    
    public int GetLagerBestandCount()
    {         
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLagerBestand";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
           return nList.getLength();
           
       } catch (Exception e) {
           e.printStackTrace();
       }    
         
       return 0;
    }
    
    public void SetLagerBestand(LagerBestand curLagerBestand)
    {
            
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLagerBestand";
           NodeList nList = m_Doc.getElementsByTagName(tableName);                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                                                   
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));                                                          
                           if(curId == curLagerBestand.getId())
                           {
                                Integer curMenge = new Integer(curLagerBestand.getMenge());
                                eElement.getElementsByTagName("Menge").item(0).setTextContent(curMenge.toString());                                
                           }
                   }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
           
       } catch (Exception e) {
           e.printStackTrace();                  
       }
    }
    
    public int AddLagerBestand(LagerBestand newLagerBestand)
    {
         Integer pID = -1;
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLagerBestand";
             
           NodeList nList = m_Doc.getElementsByTagName("tableLagerBestand");
           
           for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);                  
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        Element entryLagerBestand = m_Doc.createElement(tableName);
                        eElement.appendChild(entryLagerBestand);
        
                      
                        // P_ID
                        pID = GetBerufCount();
                        entryLagerBestand.setAttribute("P_ID", pID.toString());
                        
                        Integer resID = newLagerBestand.getResId();
                        Element fkResID = m_Doc.createElement("FK_Res_ID");
                        fkResID.appendChild(m_Doc.createTextNode(resID.toString()));
                        entryLagerBestand.appendChild(fkResID);
                        
                        Integer lagerID = newLagerBestand.getLagerId();
                        Element fkLagerID = m_Doc.createElement("FK_Lager_ID");
                        fkLagerID.appendChild(m_Doc.createTextNode(lagerID.toString()));
                        entryLagerBestand.appendChild(fkLagerID);
                        
                        Integer menge = newLagerBestand.getMenge();
                        Element mengeNode = m_Doc.createElement("Menge");
                        mengeNode.appendChild(m_Doc.createTextNode(menge.toString()));
                        entryLagerBestand.appendChild(mengeNode);                        
                    }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
                       
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return pID;
    }
    
    public void RemoveLagerBestand(int lagerbestandID)
    {
        try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryLagerBestand";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));    
                           if(curId == lagerbestandID)
                           {                                                              
                               nNode.getParentNode().removeChild(eElement);
                               return;
                           }                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }                       
    }
    //////////////////////////
    
    
    //////// Mitglied
    //////////////////////////
    public Mitglied GetMitglied(int mitgliedID)
    {
        Mitglied curMitglied = new Mitglied(-1, -1, "");
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryMitglied";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);
                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           
                           
                           Integer pID = Integer.parseInt(eElement.getAttribute("P_ID"));
                           if(pID == mitgliedID)
                           {
                                curMitglied.setId(pID);
                                curMitglied.setDorfID(Integer.parseInt(eElement.getElementsByTagName("FK_DORF_ID").item(0).getTextContent()));
                                curMitglied.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return curMitglied;
    }
    
    public int GetMitgliedCount()
    {         
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryMitglied";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
           return nList.getLength();
           
       } catch (Exception e) {
           e.printStackTrace();
       }    
         
       return 0;
    }
    
    public void SetMitglied(Mitglied curMitglied)
    {
            
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryMitglied";
           NodeList nList = m_Doc.getElementsByTagName(tableName);                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                                                   
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));                                                          
                           if(curId == curMitglied.getId())
                           {                                
                                eElement.getElementsByTagName("Name").item(0).setTextContent(curMitglied.getName());
                           }
                   }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
           
       } catch (Exception e) {
           e.printStackTrace();                  
       }
    }
    
    public int AddMitglied(Mitglied newMitglied)
    {
         Integer pID = -1;
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryMitglied";
             
           NodeList nList = m_Doc.getElementsByTagName("tableMitglied");
           
           for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);                  
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        Element entryMitglied = m_Doc.createElement(tableName);
                        eElement.appendChild(entryMitglied);
        
                      
                        // P_ID
                        pID = GetBerufCount();
                        entryMitglied.setAttribute("P_ID", pID.toString());

                        Integer dorfID = newMitglied.getDorfID();
                        Element fkDorfID = m_Doc.createElement("FK_DORF_ID");
                        fkDorfID.appendChild(m_Doc.createTextNode(dorfID.toString()));
                        entryMitglied.appendChild(fkDorfID);
                       
                        String strName = newMitglied.getName();
                        Element nameNode = m_Doc.createElement("Name");
                        nameNode.appendChild(m_Doc.createTextNode(strName.toString()));
                        entryMitglied.appendChild(nameNode);                    
                    }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
                       
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return pID;
    }
    
    public void RemoveMitglied(int mitgliedID)
    {
        try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryMitglied";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));    
                           if(curId == mitgliedID)
                           {                                                              
                               nNode.getParentNode().removeChild(eElement);
                               return;
                           }                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }                       
    }
    //////////////////////////
    
    
    //////// Ressource
    //////////////////////////
    public Ressource GetRessource(int ressourceID)
    {
        Ressource curRessource = new Ressource(-1, "", -1, -1);
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryRessource";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);
                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           
                           
                           Integer pID = Integer.parseInt(eElement.getAttribute("P_ID"));
                           if(pID == ressourceID)
                           {
                                curRessource.setId(pID);
                                curRessource.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                                curRessource.setGewicht(Integer.parseInt(eElement.getElementsByTagName("Gewicht").item(0).getTextContent()));
                                curRessource.setPreis(Integer.parseInt(eElement.getElementsByTagName("Preis").item(0).getTextContent()));
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return curRessource;
    }
    
    public int GetRessourceCount()
    {         
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryRessource";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
           return nList.getLength();
           
       } catch (Exception e) {
           e.printStackTrace();
       }    
         
       return 0;
    }
    
    public void SetRessource(Ressource curRessource)
    {
            
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryRessource";
           NodeList nList = m_Doc.getElementsByTagName(tableName);                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                                                   
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));                                                          
                           if(curId == curRessource.getId())
                           {
                                eElement.getElementsByTagName("Name").item(0).setTextContent(curRessource.getName());                                
                               
                                Integer curGewicht = new Integer(curRessource.getGewicht());
                                eElement.getElementsByTagName("Gewicht").item(0).setTextContent(curGewicht.toString());                                
                                
                                Integer curPreis = new Integer(curRessource.getPreis());
                                eElement.getElementsByTagName("Preis").item(0).setTextContent(curPreis.toString());                                
                           }
                   }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
           
       } catch (Exception e) {
           e.printStackTrace();                  
       }
    }
    
    public int AddRessource(Ressource newRessource)
    {
         Integer pID = -1;
         try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryRessource";
             
           NodeList nList = m_Doc.getElementsByTagName("tableBeruf");
           
           for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);                  
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        Element entryRessource = m_Doc.createElement(tableName);
                        eElement.appendChild(entryRessource);
        
                      
                        // P_ID
                        pID = GetBerufCount();
                        entryRessource.setAttribute("P_ID", pID.toString());

                        
                        String strName = newRessource.getName();
                        Element nameNode = m_Doc.createElement("Name");
                        nameNode.appendChild(m_Doc.createTextNode(strName));
                        entryRessource.appendChild(nameNode);

                        
                        Integer gewicht = newRessource.getGewicht();
                        Element gewichtNode = m_Doc.createElement("Gewicht");
                        gewichtNode.appendChild(m_Doc.createTextNode(gewicht.toString()));
                        entryRessource.appendChild(gewichtNode);

                        
                        Integer preis = newRessource.getPreis();
                        Element preisNode = m_Doc.createElement("Preis");
                        preisNode.appendChild(m_Doc.createTextNode(preis.toString()));
                        entryRessource.appendChild(preisNode);   
                    }
           }
           
           // Veränderung in XML-Datei übernehmen
           WriteChangetoXMLFile();
                       
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return pID;
    }
    
    public void RemoveRessource(int ressourceID)
    {
        try {
           
           //optional, but recommended
           //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
           m_Doc.getDocumentElement().normalize();
    
           String tableName = "entryRessource";
           NodeList nList = m_Doc.getElementsByTagName(tableName);
                      
           for (int temp = 0; temp < nList.getLength(); temp++) {

                   Node nNode = nList.item(temp);                  
                   if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                           Element eElement = (Element) nNode;
                           int curId = Integer.parseInt(eElement.getAttribute("P_ID"));    
                           if(curId == ressourceID)
                           {                                                              
                               nNode.getParentNode().removeChild(eElement);
                               return;
                           }                          
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }                       
    }
    //////////////////////////
}

