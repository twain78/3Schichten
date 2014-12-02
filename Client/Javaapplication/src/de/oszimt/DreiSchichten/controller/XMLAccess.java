package de.oszimt.DreiSchichten.controller;

import java.util.ArrayList;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// w3c-Standards
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
import java.util.List;


/**
 * LastChange: 09.11.2014
 * @author Robert Schardt
 */

// To Do:
// - XML-Pfad anpassen
// - XML-Datei mit Testdaten füllen

public class XMLAccess implements IAccess {
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
    @Override
    public Beruf getBeruf(int berufId)
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
                           if(pID == berufId)
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
    
    @Override
    public List<Beruf> getBerufe (int mitgliedId)
    {
         List<Beruf> BerufList = new ArrayList<Beruf>();                
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
                           
                           
                           Integer FkMitgliedID = Integer.parseInt(eElement.getAttribute("FK_Mitglied_ID"));
                           if(FkMitgliedID == mitgliedId)
                           {
                                Beruf curBeruf = new Beruf();
                                curBeruf.setId(Integer.parseInt(eElement.getElementsByTagName("P_ID").item(0).getTextContent()));
                                curBeruf.setTypID(Integer.parseInt(eElement.getElementsByTagName("FK_TYP_ID").item(0).getTextContent()));
                                curBeruf.setMitgliedID(FkMitgliedID);
                                curBeruf.setPunkte(Integer.parseInt(eElement.getElementsByTagName("Punkte").item(0).getTextContent()));
                                
                                BerufList.add(curBeruf);
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return BerufList;
    }
    
    @Override
    public int getBerufCount()
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
    
    @Override
    public void setBeruf(Beruf curBeruf)
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
    
    @Override
    public int addBeruf(Beruf newBeruf)
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
                        pID = getBerufCount();
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
    
    @Override
    public void remBeruf(int berufId)
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
                           if(curId == berufId)
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
    @Override
    public Berufstyp getBerufstyp(int berufstypId)
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
                           if(pID == berufstypId)
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
    
    @Override
    public List<Berufstyp> getBerufstypen()
    {
        List<Berufstyp> BerufstypenListe = new ArrayList<Berufstyp>();
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

                        Berufstyp curBerufstyp = new Berufstyp();
                        curBerufstyp.setId(Integer.parseInt(eElement.getElementsByTagName("P_ID").item(0).getTextContent()));
                        curBerufstyp.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                        curBerufstyp.setSk1(Integer.parseInt(eElement.getElementsByTagName("SK1").item(0).getTextContent()));
                        curBerufstyp.setSk2(Integer.parseInt(eElement.getElementsByTagName("SK2").item(0).getTextContent()));
                        curBerufstyp.setSk3(Integer.parseInt(eElement.getElementsByTagName("SK3").item(0).getTextContent()));
                        curBerufstyp.setSk4(Integer.parseInt(eElement.getElementsByTagName("SK4").item(0).getTextContent()));
                        curBerufstyp.setSk5(Integer.parseInt(eElement.getElementsByTagName("SK5").item(0).getTextContent()));         
                        
                        BerufstypenListe.add(curBerufstyp);
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return BerufstypenListe;
    }
    
    @Override
    public int getBerufstypCount()
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
    
    @Override
    public void setBerufstyp(Berufstyp curBerufstyp)
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
    
    @Override
    public int addBerufstyp(Berufstyp newBerufstyp)
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
                        pID = getBerufstypCount();
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
    
    @Override
    public void remBerufstyp(int berufstypId)
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
                           if(curId == berufstypId)
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
    @Override
    public Dorf getDorf(int dorfId)
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
                           if(pID == dorfId)
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
    
   @Override
   public List<Dorf> getDorfListe()
   {
        List<Dorf> DorfList = new ArrayList<Dorf>();
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

                        Dorf curDorf = new Dorf();
                        curDorf.setId(Integer.parseInt(eElement.getAttribute("P_ID")));
                        curDorf.setName(eElement.getElementsByTagName("FK_TYP_ID").item(0).getTextContent());                                                   
                        
                        DorfList.add(curDorf);
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return DorfList;
   }
   
    @Override
    public int getDorfCount()
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
    
    @Override
    public void setDorf(Dorf curDorf)
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
    
    @Override
    public int addDorf(Dorf newDorf)
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
                        pID = getDorfCount();
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
    
    @Override
    public void remDorf(int dorfId)
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
                           if(curId == dorfId)
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
    @Override
    public Lager getLager(int lagerId)
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
                           if(pID == lagerId)
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
    
    @Override
    public List<Lager> getLagerListe(int dorfId)
    {
         List<Lager> LagerList = new ArrayList<Lager>();
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

                           Integer FkDorfId = Integer.parseInt(eElement.getAttribute("FK_DORF_ID"));
                           if(FkDorfId == dorfId)
                           {
                                Lager curLager = new Lager();
                                curLager.setId(Integer.parseInt(eElement.getElementsByTagName("P_ID").item(0).getTextContent()));
                                curLager.setDorfId(Integer.parseInt(eElement.getElementsByTagName("FK_DORF_ID").item(0).getTextContent()));
                                curLager.setName(eElement.getElementsByTagName("Name").item(0).getTextContent()); 
                                
                                LagerList.add(curLager);
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return LagerList;
    }
    
    @Override
    public int getLagerCount()
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
    
    @Override
    public void setLager(Lager curLager)
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
    
    @Override
    public int addLager(Lager newLager)
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
                        pID = getLagerCount();
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
    
    @Override
    public void remLager(int lagerId)
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
                           if(curId == lagerId)
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
    @Override
    public LagerBestand getLagerBestand(int lagerbestandId)
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
                           if(pID == lagerbestandId)
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
    
    @Override
    public List<LagerBestand> getLagerBestaende(int lagerId)
    {
         List<LagerBestand> LagerBestandList = new ArrayList<LagerBestand>();
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
                           
                           
                           Integer FKLagerId = Integer.parseInt(eElement.getAttribute("FK_LAGER_ID"));
                           if(FKLagerId == lagerId)
                           {
                                LagerBestand curLagerBestand = new LagerBestand();
                                curLagerBestand.setId(Integer.parseInt(eElement.getElementsByTagName("P_ID").item(0).getTextContent()));
                                curLagerBestand.setResId(Integer.parseInt(eElement.getElementsByTagName("FK_RES_ID").item(0).getTextContent()));
                                curLagerBestand.setLagerId(Integer.parseInt(eElement.getElementsByTagName("FK_LAGER_ID").item(0).getTextContent()));
                                curLagerBestand.setMenge(Integer.parseInt(eElement.getElementsByTagName("Menge").item(0).getTextContent()));
                                
                                LagerBestandList.add(curLagerBestand);
                           }
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return LagerBestandList;
    }
    
    @Override
    public int getLagerBestandCount()
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
    
    @Override
    public void setLagerBestand(LagerBestand curLagerBestand)
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
    
    @Override
    public int addLagerBestand(LagerBestand newLagerBestand)
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
                        pID = getLagerBestandCount();
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
    
    @Override
    public void remLagerBestand(int lagerbestandId)
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
                           if(curId == lagerbestandId)
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
    @Override
    public Mitglied getMitglied(int mitgliedId)
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
                           if(pID == mitgliedId)
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
    
    @Override
    public List<Mitglied> getMitglieder()
    {
        List<Mitglied> MitgliederListe = new ArrayList<Mitglied>();
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

                        Mitglied curMitglied = new Mitglied();
                        curMitglied.setId(Integer.parseInt(eElement.getElementsByTagName("P_ID").item(0).getTextContent()));
                        curMitglied.setDorfID(Integer.parseInt(eElement.getElementsByTagName("FK_DORF_ID").item(0).getTextContent()));
                        curMitglied.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                           
                        MitgliederListe.add(curMitglied);
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return MitgliederListe;
    }
    
    @Override
    public int getMitgliedCount()
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
    
    @Override
    public void setMitglied(Mitglied curMitglied)
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
    
    @Override
    public int addMitglied(Mitglied newMitglied)
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
                        pID = getMitgliedCount();
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
    
    @Override
    public void remMitglied(int mitgliedId)
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
                           if(curId == mitgliedId)
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
    @Override
    public Ressource getRessource(int ressourceId)
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
                           if(pID == ressourceId)
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
    
    @Override
    public List<Ressource> getRessourcen()
    {
         List<Ressource> RessourcenListe = new ArrayList<Ressource>();
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
                                       
                        Ressource curRessource = new Ressource();
                        curRessource.setId(Integer.parseInt(eElement.getElementsByTagName("P_ID").item(0).getTextContent()));
                        curRessource.setName(eElement.getElementsByTagName("Name").item(0).getTextContent());
                        curRessource.setGewicht(Integer.parseInt(eElement.getElementsByTagName("Gewicht").item(0).getTextContent()));
                        curRessource.setPreis(Integer.parseInt(eElement.getElementsByTagName("Preis").item(0).getTextContent()));
                        
                        RessourcenListe.add(curRessource);
                   }
           }
       } catch (Exception e) {
           e.printStackTrace();
       }         
       
       return RessourcenListe;
    }
    
    @Override
    public int getRessourceCount()
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
    
    @Override
    public void setRessource(Ressource curRessource)
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
    
    @Override
    public int addRessource(Ressource newRessource)
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
                        pID = getRessourceCount();
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
    
    @Override
    public void remRessource(int ressourceId)
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
                           if(curId == ressourceId)
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
}

