package de.oszimt.DreiSchichten.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


// Model-Imports
import de.oszimt.DreiSchichten.model.Beruf;
import de.oszimt.DreiSchichten.model.Berufstyp;
import de.oszimt.DreiSchichten.model.Dorf;
import de.oszimt.DreiSchichten.model.Lager;
import de.oszimt.DreiSchichten.model.LagerBestand;
import de.oszimt.DreiSchichten.model.Mitglied;
import de.oszimt.DreiSchichten.model.Ressource;



/**
 * LastChange: 08.11.2014
 * @author Robert Schardt
 */


//// ToDo:    
// - Datenbank-Pfad anpassen
// - Trigger in DB implementieren
// - Verbindungsaufbau- bzw abbau - Methode

public class DBAccess {
  private Connection m_Connection;
  private ResultSet m_ResultSet;
  private boolean m_bInitialized;
  
  // Beruf-Table
  private PreparedStatement m_GetBeruf;
  private PreparedStatement m_GetBerufCount;
  private PreparedStatement m_SetBeruf;
  private PreparedStatement m_AddBeruf;
  private PreparedStatement m_RemBeruf;
 
  // Berufstyp-Table
  private PreparedStatement m_GetBerufstyp;
  private PreparedStatement m_GetBerufstypCount;
  private PreparedStatement m_SetBerufstyp;
  private PreparedStatement m_AddBerufstyp;
  private PreparedStatement m_RemBerufstyp;
  
  // Dorf-Table
  private PreparedStatement m_GetDörfer;
  private PreparedStatement m_GetDorf;
  private PreparedStatement m_GetDorfCount;
  private PreparedStatement m_SetDorf;
  private PreparedStatement m_AddDorf;
  private PreparedStatement m_RemDorf;
  
  // Lager-Table
  private PreparedStatement m_GetLager;
  private PreparedStatement m_GetLagerCount;
  private PreparedStatement m_SetLager;
  private PreparedStatement m_AddLager;
  private PreparedStatement m_RemLager;
  
  // LagerBestand-Table
  private PreparedStatement m_GetLagerBestand;
  private PreparedStatement m_SetLagerBestand;
  private PreparedStatement m_GetLagerBestandCount;
  private PreparedStatement m_AddLagerBestand;
  private PreparedStatement m_RemLagerBestand;
  
  // Mitglied-Table
  private PreparedStatement m_GetMitglied;
  private PreparedStatement m_SetMitglied;
  private PreparedStatement m_GetMitgliedCount;
  private PreparedStatement m_AddMitglied;
  private PreparedStatement m_RemMitglied;
  
  // Ressource-Table
  private PreparedStatement m_GetRessource;
  private PreparedStatement m_GetRessourceCount;
  private PreparedStatement m_SetRessource;
  private PreparedStatement m_AddRessource;
  private PreparedStatement m_RemRessource;
  
 
  public DBAccess() {
    m_Connection = null;
    m_ResultSet = null;
    m_bInitialized = false;
    
    initConnection();
    initPrepStatements();
  }
  
  /*
  public void test()
  {
      try
      {
        Statement testStatement = m_Connection.createStatement();
        testStatement.setQueryTimeout(30);
        
        ResultSet rs = testStatement.executeQuery("SELECT * FROM Dorf");
        
        while(rs.next())
        {
            System.out.println("pla = " + rs.getString("name"));
        }
      }
      catch(SQLException e)
      {
        int peter = 1;
      }
  }
  */
  
  public boolean isInitialized() {
      return m_bInitialized;
  }
  
  // Verbindung intialisieren
  public void initConnection() {
    try {
      // Treiber zuweisen
      Class.forName("org.sqlite.JDBC");
      
      // Verbindung zur Datenbank aufbauen -- entsprechend anpassen
      m_Connection = DriverManager.getConnection("jdbc:sqlite:D:/lif_DB.db");  
    } catch (Exception e) {
      m_bInitialized = false;
    }
  }
  
  public void initPrepStatements()
  {
     
      try 
      {

        //// Beruf-Statements
        m_GetBeruf = m_Connection
                .prepareStatement("SELECT P_ID, FK_TYP_ID, FK_Mitglied_ID, Punkte FROM Beruf WHERE P_ID = ?");
        m_GetBerufCount = m_Connection
                .prepareStatement("SELECT COUNT(*) FROM Beruf");      
        m_SetBeruf = m_Connection
                .prepareStatement("UPDATE Beruf SET Punkte = ? WHERE P_ID = ?");
        m_AddBeruf = m_Connection                                                          // allerdings muss man hier einen Primary Key zurückgeben also entweder Int oder ein Object der Klasse
                .prepareStatement("INSERT INTO Beruf (FK_TYP_ID, FK_Mitglied_ID, Punkte) VALUES(?, ?)");   // auto_increment sollte den primary_Key selbst hochzählen bzw. Trigger mit Verantwortung (comment1)
        m_RemBeruf = m_Connection                                                   
                .prepareStatement("DELETE FROM Beruf WHERE P_ID = ?");                     // Trigger muss hier alle Abhängigkeiten auflösen (comment2)

        ////

        //// Berufstyp-Statements
        m_GetBerufstyp = m_Connection
                .prepareStatement("SELECT P_ID, Name, SK1, SK2, SK3, SK4, SK5 FROM Berufstyp WHERE P_ID = ?");
        m_GetBerufstypCount = m_Connection        
                .prepareStatement("SELECT COUNT(*) FROM Berufstyp");
        m_SetBerufstyp = m_Connection
                .prepareStatement("UPDATE Berufstyp SET Name = ?, SK1 = ?, SK2 = ?, SK3 = ?, SK4 = ?, SK5 = ? WHERE P_ID = ?");
        m_AddBerufstyp = m_Connection
                .prepareStatement("INSERT INTO Berufstyp(Name, SK1, SK2, SK3, SK4, SK5) VALUES(?, ?, ?, ?, ?, ?)");  // *comment1
        m_RemBerufstyp = m_Connection
                .prepareStatement("DELETE FROM Berufstyp WHERE P_ID = ?");                                           // *comment2
        ////

        //// Dorf-Statements
        m_GetDörfer = m_Connection
                .prepareStatement("SELECT * FROM Dorf");
        m_GetDorf = m_Connection
                .prepareStatement("SELECT P_ID, Name FROM Dorf WHERE P_ID = ?");
        m_GetDorfCount = m_Connection
                .prepareStatement("SELECT COUNT(*) FROM Dorf");
        m_SetDorf = m_Connection
                .prepareStatement("UPDATE Dorf SET Name = ? WHERE P_ID = ?");
        m_AddDorf = m_Connection
                .prepareStatement("INSERT INTO Dorf (Name) VALUES(?)");  // *comment1
        m_RemDorf = m_Connection
                .prepareStatement("DELETE FROM Dorf WHERE P_ID = ?");    // *comment2
        ////

        //// Lager-Statements
        m_GetLager = m_Connection
                .prepareStatement("SELECT P_ID, FK_DORF_ID, Name FROM Lager WHERE P_ID = ?");
        m_GetLagerCount = m_Connection
                .prepareStatement("SELECT COUNT(*) FROM Lager");   
        m_SetLager = m_Connection
                .prepareStatement("UPDATE Lager SET Name = ? WHERE P_ID = ?");
        m_AddLager = m_Connection
                .prepareStatement("INSERT INTO Lager (FK_DORF_ID, Name) VALUES(?, ?)");  // *comment1
        m_RemLager = m_Connection
                .prepareStatement("DELETE FROM Lager WHERE P_ID = ?");                   // *comment2
        ////

        //// LagerBestand-Statements
        m_GetLagerBestand = m_Connection                
                .prepareStatement("SELECT P_ID, FK_RES_ID, FK_Lager_ID, Menge FROM LagerBestand WHERE P_ID = ?");
        m_GetLagerBestandCount = m_Connection
                .prepareStatement("SELECT COUNT(*) FROM LagerBestand");    
        m_SetLagerBestand = m_Connection
                .prepareStatement("UPDATE LagerBestand SET Menge = ? WHERE P_ID = ?");
        m_AddLagerBestand = m_Connection
                .prepareStatement("INSERT INTO LagerBestand(FK_RES_ID, FK_LAGER_ID, Menge) VALUES(?, ?, ?)");  // *comment1
        m_RemLagerBestand = m_Connection
                .prepareStatement("DELETE FROM LagerBestand WHERE P_ID = ?");                                  // *comment2
        ////  

        //// Mitglied-Statements
        m_GetMitglied = m_Connection
                .prepareStatement("SELECT P_ID, FK_DORF_ID, Name FROM Mitglied WHERE P_ID = ?");
        m_GetMitgliedCount = m_Connection
                .prepareStatement("SELECT COUNT(*) FROM Mitglied");    
        m_SetMitglied = m_Connection
                .prepareStatement("UPDATE Mitglied SET Name = ? WHERE P_ID = ?");
        m_AddMitglied = m_Connection
                .prepareStatement("INSERT INTO Mitglied (FK_DORF_ID, Name) VALUES(?, ?)");  // *comment1
        m_RemMitglied = m_Connection
                .prepareStatement("DELETE FROM Mitglied WHERE P_ID = ?");                   // *comment2
        ////

        //// Ressource-Statements
        m_GetRessource = m_Connection
                .prepareStatement("SELECT P_ID, Name, Gewicht, Preis FROM Ressource WHERE P_ID = ?");
        m_GetRessourceCount = m_Connection
                .prepareStatement("SELECT COUNT(*) FROM Ressource");    
        m_SetRessource = m_Connection
                .prepareStatement("UPDATE Ressource SET Name = ?, Gewicht = ?, Preis = ? WHERE P_ID = ?");
        m_AddRessource = m_Connection
                .prepareStatement("INSERT INTO Ressource(Name, Gewicht, Preis) VALUES(?, ?, ?)");  // *comment1
        m_RemRessource = m_Connection
                .prepareStatement("DELETE FROM Ressource WHERE P_ID = ?");                         // *comment2
        ////


        m_bInitialized = true;
      
      // Anm.: vllt geht mit dem Count auch sowas "SELECT COUNT(*) FROM ?" , dann könnte man das sehr viel mehr vereinfachen      
      } catch (SQLException e)
      {     
          m_bInitialized = false;
      }
    
  }
  
  public Beruf getBeruf(int berufId) 
  {
      Beruf curBeruf = new Beruf();
      
      try 
      {
        long tmpPID, FK_TYP_ID, FK_Mitglied_ID, Punkte;

        m_GetBeruf.setInt(1, berufId);
        m_ResultSet = m_GetBeruf.executeQuery();

        tmpPID = m_ResultSet.getLong(1);
        FK_TYP_ID = m_ResultSet.getLong(2);
        FK_Mitglied_ID = m_ResultSet.getLong(3);   
        Punkte = m_ResultSet.getLong(4);

        curBeruf = new Beruf((int)tmpPID, (int)FK_TYP_ID, (int)FK_Mitglied_ID, (int)Punkte);

        return curBeruf;

      } catch (SQLException e)
      {     
          return curBeruf;
      }
  }
  
  public int getBerufCount()
  {
      try 
      {
          m_ResultSet = m_GetBerufCount.executeQuery();
          return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public void setBeruf(Beruf curBeruf)
  {
      try 
      {     
        m_SetBeruf.setInt(1, curBeruf.getPunkte());
        m_SetBeruf.setInt(2, curBeruf.getId());
        m_ResultSet = m_SetBeruf.executeQuery();
      } catch (SQLException e)
      {     
 
      }
  }
  
  // gibt den primary_Key zurück, da man diesen vor dem Anlegen in der Datenbank,
  // noch nicht wissen kann
  public int addBeruf(Beruf newBeruf)
  {
      try 
      {
         m_AddBeruf.setInt(1, newBeruf.getTypID());
         m_AddBeruf.setInt(2, newBeruf.getMitgliedID());       
         m_AddBeruf.setInt(3, newBeruf.getPunkte());
         m_AddBeruf.executeQuery();
         
         return getBerufCount() -1;     // da Sqllite anscheinend immer einen "leeren" ersten Eintrag anlegt
         
      } catch (SQLException e)
      {
         return -1;
      }
  }
  
  public void remBeruf(int berufId)
  {
      try 
      {
          m_RemBeruf.setInt(1, berufId);
          m_RemBeruf.executeQuery();
      } catch (SQLException e)
      {
          
      }
  }
  
  
  public Berufstyp getBerufstyp(int berufstypId)
  {
      Berufstyp curBerufstyp = new Berufstyp();
      try {
      
      long tmpPID, SK1, SK2, SK3, SK4, SK5;
      String Name = "";
      
      m_GetBerufstyp.setInt(1, berufstypId);
      m_ResultSet = m_GetBerufstyp.executeQuery();
      
      tmpPID = m_ResultSet.getLong(1);
      Name = m_ResultSet.getObject(2).toString();
      SK1 = m_ResultSet.getLong(3);
      SK2 = m_ResultSet.getLong(4);
      SK3 = m_ResultSet.getLong(5);
      SK4 = m_ResultSet.getLong(6);
      SK5 = m_ResultSet.getLong(7);
      
      curBerufstyp = new Berufstyp((int)tmpPID, Name, (int)SK1, (int)SK2, (int)SK3, (int)SK4, (int)SK5);
      
      return curBerufstyp;
      
      } catch (SQLException e)
      {     
          return curBerufstyp;
      }
  }
  
  public int getBerufstypCount()
  {
      try {
          m_ResultSet = m_GetBerufstypCount.executeQuery();
          return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public void setBerufstyp(Berufstyp curBerufstyp)
  {
      try {
     
      m_SetBerufstyp.setString(1, String.valueOf(curBerufstyp.getName()));
      m_SetBerufstyp.setInt(2, curBerufstyp.getSk1());
      m_SetBerufstyp.setInt(3, curBerufstyp.getSk2());
      m_SetBerufstyp.setInt(4, curBerufstyp.getSk3());
      m_SetBerufstyp.setInt(5, curBerufstyp.getSk4());
      m_SetBerufstyp.setInt(6, curBerufstyp.getSk5());
      m_SetBerufstyp.setInt(7, curBerufstyp.getId());
      
      m_ResultSet = m_SetBerufstyp.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  // gibt den primary_Key zurück, da man diesen vor dem Anlegen in der Datenbank,
  // noch nicht wissen kann
  public int addBerufstyp(Berufstyp newBerufstyp)
  {
      try 
      {
          m_AddBerufstyp.setString(1, newBerufstyp.getName());
          m_AddBerufstyp.setInt(2, newBerufstyp.getSk1());
          m_AddBerufstyp.setInt(3, newBerufstyp.getSk2());
          m_AddBerufstyp.setInt(4, newBerufstyp.getSk3());
          m_AddBerufstyp.setInt(5, newBerufstyp.getSk4());
          m_AddBerufstyp.setInt(6, newBerufstyp.getSk5());
          
          m_AddBerufstyp.executeQuery();
          
          return getBerufstypCount() -1;    // da Sqllite anscheinend immer einen "leeren" ersten Eintrag anlegt
          
      } catch (SQLException e)
      {
          return -1;
      }
  }
  
  public void remBerufstyp(int berufstypId)
  {
      try 
      {
          m_RemBerufstyp.setInt(1, berufstypId);
          m_RemBerufstyp.executeQuery();
      
      } catch (SQLException e)
      {
          
      }
  }
  
  public Dorf[] getDörfer()
  {
      //todo m_GetDörfer
  }
  
  
  public Dorf getDorf(int dorfId)
  {
      Dorf curDorf = new Dorf();
      try {
      
      long tmpPID;
      String Name;
      
      m_GetDorf.setInt(1, dorfId);
      m_ResultSet = m_GetDorf.executeQuery();
      
      tmpPID = m_ResultSet.getLong(1);
      Name = m_ResultSet.getObject(2).toString();
      
      curDorf = new Dorf((int)tmpPID, Name);
      
      return curDorf;
      
      } catch (SQLException e)
      {     
          return curDorf;
      }
  }
  
  public int getDorfCount()
  {
      try {
      m_ResultSet = m_GetDorfCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public void setDorf(Dorf curDorf)
  {
      try {
   
      m_SetDorf.setString(1, String.valueOf(curDorf.getName()));
      m_SetDorf.setInt(2, curDorf.getId());
      
      m_ResultSet = m_SetDorf.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  // gibt den primary_Key zurück, da man diesen vor dem Anlegen in der Datenbank,
  // noch nicht wissen kann
  public int addDorf(Dorf newDorf)
  {
      try 
      {
          m_AddDorf.setInt(1, newDorf.getId());
          m_AddDorf.setString(2, newDorf.getName());
          
          m_AddDorf.executeQuery();
      
          return getDorfCount() -1; // da Sqllite anscheinend immer einen "leeren" ersten Eintrag anlegt
          
      } catch (SQLException e)
      {
          return -1;
      }
  }
  
  public void remDorf(int dorfId)
  {
      try 
      {
          m_RemDorf.setInt(1, dorfId);
          m_RemDorf.executeQuery();
      
      } catch (SQLException e)
      {
          
      }
  }

  
  public Lager getLager(int lagerId)
  {
      Lager curLager = new Lager();
      try {
      
      long tmpPID;
      long tmpFKDorfId;
      String Name;
      
      m_GetLager.setInt(1, lagerId);
      m_ResultSet = m_GetLager.executeQuery();

      tmpPID = m_ResultSet.getLong(1);
      tmpFKDorfId = m_ResultSet.getLong(2);
      Name = m_ResultSet.getObject(3).toString();
            
      curLager = new Lager((int)tmpPID, (int)tmpFKDorfId, Name);
      
      return curLager;
      
      } catch (SQLException e)
      {     
          return curLager;
      }
  }
  
  public int getLagerCount()
  {
      try {
      m_ResultSet = m_GetLagerCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public void setLager(Lager curLager)
  {
    try {
   
      m_SetLager.setString(1, curLager.getName());
      m_SetLager.setInt(2, curLager.getId());
      
      m_ResultSet = m_SetLager.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  // gibt den primary_Key zurück, da man diesen vor dem Anlegen in der Datenbank,
  // noch nicht wissen kann
  public int addLager(Lager newLager)
  {
      try 
      {
          m_AddLager.setInt(1, newLager.getDorfId());
          m_AddLager.setString(2, newLager.getName());        
          m_AddLager.executeQuery();
          
          return getLagerCount() -1; // da Sqllite anscheinend immer einen "leeren" ersten Eintrag anlegt
      
      } catch (SQLException e)
      {
          return -1;
      }
  }
  
  public void remLager(int lagerId)
  {
      try 
      {
          m_RemLager.setInt(1, lagerId);
          m_RemLager.executeQuery();
      
      } catch (SQLException e)
      {
          
      }
  }
  
 
  public LagerBestand getLagerBestand(int lagerbestandId)
  {
      LagerBestand curLagerBestand = new LagerBestand();
      try {
      
      long tmpPID;
      long tmpFKResID;
      long tmpFKLagerID;
      long Menge;
      
      m_GetLagerBestand.setInt(1, lagerbestandId);
      m_ResultSet = m_GetLagerBestand.executeQuery();

      tmpPID = m_ResultSet.getLong(1);
      tmpFKResID = m_ResultSet.getLong(2);
      tmpFKLagerID = m_ResultSet.getLong(3);
      Menge = m_ResultSet.getLong(4);
      
      curLagerBestand = new LagerBestand((int)tmpPID, (int)tmpFKResID, (int)tmpFKLagerID, (int)Menge);
      
      return curLagerBestand;
      
      } catch (SQLException e)
      {     
          return curLagerBestand;
      }
  }
 
  public int getLagerBestandCount()
  {
      try {
      m_ResultSet = m_GetLagerBestandCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  
  public void setLagerBestand(LagerBestand curLagerBestand)
  {
    try {
   
      m_SetLagerBestand.setInt(1, curLagerBestand.getMenge());
      m_SetLagerBestand.setInt(2, curLagerBestand.getId());
      
      m_ResultSet = m_SetLagerBestand.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  
  // gibt den primary_Key zurück, da man diesen vor dem Anlegen in der Datenbank,
  // noch nicht wissen kann
  public int addLagerBestand(LagerBestand newLagerBestand)
  {
      try 
      {
          m_AddLagerBestand.setInt(1, newLagerBestand.getResId());
          m_AddLagerBestand.setInt(2, newLagerBestand.getLagerId());
          m_AddLagerBestand.setInt(3, newLagerBestand.getMenge());
          m_AddLagerBestand.executeQuery();
          
          return getLagerBestandCount() -1; // da Sqllite anscheinend immer einen "leeren" ersten Eintrag anlegt
      
      } catch (SQLException e)
      {
          return -1;
      }
  }
  
  public void remLagerBestand(int lagerbestandId)
  {
      try 
      {
          m_RemLagerBestand.setInt(1, lagerbestandId);
          m_RemLagerBestand.executeQuery();
      
      } catch (SQLException e)
      {
          
      }
  }
  
  
 
  
  public Mitglied getMitglied(int mitgliedId)
  {
      Mitglied curMitglied = new Mitglied();
      try {
      
      long tmpPID;
      long FK_DORF_ID;
      String Name;
      
      m_GetMitglied.setInt(1, mitgliedId);
      m_ResultSet = m_GetMitglied.executeQuery();
      
      tmpPID = m_ResultSet.getLong(1);
      FK_DORF_ID = m_ResultSet.getLong(2);
      Name = m_ResultSet.getObject(2).toString();
      
      curMitglied = new Mitglied((int)tmpPID, (int)FK_DORF_ID, Name);
      
      return curMitglied;
      
      } catch (SQLException e)
      {     
          return curMitglied;
      }
  }
  
  public int getMitgliedCount()
  {
      try {
      m_ResultSet = m_GetMitgliedCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public void setMitglied(Mitglied curMitglied)
  {
      try {
   
      m_SetMitglied.setString(1, curMitglied.getName());
      m_SetMitglied.setInt(2, curMitglied.getId());
      
      m_ResultSet = m_SetMitglied.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
    public int addMitglied(Mitglied newMitglied)
  {
      try 
      {
          m_AddMitglied.setInt(1, newMitglied.getDorfID());
          m_AddMitglied.setString(2, newMitglied.getName());
          
          m_ResultSet = m_AddMitglied.executeQuery();
          
          return getMitgliedCount() -1; // da Sqllite anscheinend immer einen "leeren" ersten Eintrag anlegt
      
      } catch (SQLException e)
      {
          return -1;
      }
  }
  
  public void remMitglied(int mitgliedId)
  {
      try 
      {
          m_RemMitglied.setInt(1, mitgliedId);
          m_RemMitglied.executeQuery();
      
      } catch (SQLException e)
      {
          
      }
  }
  
 
  
  public Ressource getRessource(int ressourceId)
  {
      Ressource curRessource = new Ressource();
      try {
      
      long tmpPID;
      String Name;
      long Gewicht;
      long Preis;
           
      m_GetRessource.setInt(1, ressourceId);
      m_ResultSet = m_GetRessource.executeQuery();
      
      tmpPID = m_ResultSet.getLong(1);
      Name = m_ResultSet.getObject(2).toString();
      Gewicht = m_ResultSet.getLong(3);
      Preis = m_ResultSet.getLong(4);
      
      curRessource = new Ressource((int)tmpPID, Name, (int)Gewicht, (int)Preis);
      
      return curRessource;
      
      } catch (SQLException e)
      {     
          return curRessource;
      }
  }
 
  public int getRessourceCount()
  {
      try {
      m_ResultSet = m_GetRessourceCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public void setRessource(Ressource curRessource)
  {
      try {
      m_SetRessource.setString(1, curRessource.getName());
      m_SetRessource.setInt(2, curRessource.getGewicht());
      m_SetRessource.setInt(3, curRessource.getPreis());
      m_SetRessource.setInt(4, curRessource.getId());
      
      m_ResultSet = m_SetMitglied.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
 
  public int addRessource(Ressource newRessource)
  {
      try 
      {
          m_AddRessource.setString(1, newRessource.getName());
          m_AddRessource.setInt(2, newRessource.getGewicht());
          m_AddRessource.setInt(3, newRessource.getPreis());
          
          m_ResultSet = m_AddRessource.executeQuery();
      
          return getRessourceCount() -1; // da Sqllite anscheinend immer einen "leeren" ersten Eintrag anlegt
          
      } catch (SQLException e)
      {
          return -1;
      }
  }
  
  public void remRessource(int ressourceId)
  {
      try 
      {
          m_RemRessource.setInt(1, ressourceId);
          m_RemRessource.executeQuery();
      
      } catch (SQLException e)
      {
        
      }
  }
  
}
