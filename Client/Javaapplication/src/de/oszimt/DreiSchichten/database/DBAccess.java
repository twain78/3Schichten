package de.oszimt.DreiSchichten.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

// Model-Imports
import de.oszimt.DreiSchichten.model.Beruf;
import de.oszimt.DreiSchichten.model.Berufstyp;
import de.oszimt.DreiSchichten.model.Dorf;
import de.oszimt.DreiSchichten.model.Lager;
import de.oszimt.DreiSchichten.model.Mitglied;

/**
 * LastChange: 25.10.2014
 * @author Robert Schardt
 */


//// ToDo:
// eigene Methode zum Verbindungsaufbau (done)
// und mit prepared Statements arbeiten 
// vernünftige Rückgabe Klasse für die anderen Module bauen
   // - Befüllung von Modell-Klassen die an den Controller zurückgegeben werden
// Trigger implementieren
// Mitgliedername Methoden noch implementieren


public class DBAccess {
  private Connection m_Connection;
  private ResultSet m_ResultSet;
  
  // Beruf-Table
  private PreparedStatement m_GetBeruf;
  private PreparedStatement m_SetBeruf;
  private PreparedStatement m_GetBerufCount;
  // Berufstyp-Table
  private PreparedStatement m_GetBerufstyp;
  private PreparedStatement m_SetBerufstyp;
  private PreparedStatement m_GetBerufstypCount;
  // Dorf-Table
  private PreparedStatement m_GetDorf;
  private PreparedStatement m_SetDorf;
  private PreparedStatement m_GetDorfCount;
  // Lager-Table
  private PreparedStatement m_GetLager;
  private PreparedStatement m_SetLager;
  private PreparedStatement m_GetLagerCount;
  // LagerBestand-Table
  private PreparedStatement m_GetLagerBestand;
  private PreparedStatement m_SetLagerBestand;
  private PreparedStatement m_GetLagerBestandCount;
  // Mitglied-Table
  private PreparedStatement m_GetMitglied;
  private PreparedStatement m_SetMitglied;
  private PreparedStatement m_GetMitgliedCount;
  // Ressource-Table
  private PreparedStatement m_GetRessource;
  private PreparedStatement m_SetRessource;
  private PreparedStatement m_GetRessourceCount;
 
  public DBAccess() {
    m_Connection = null;
    m_ResultSet = null;
    
    InitConnection();
    InitPrepStatements();
  }
  
  
  // Verbindung intialisieren
  public void InitConnection() {
    try {
      // Treiber zuweisen
      Class.forName("org.sqlite.JDBC");
      
      // Verbindung zur Datenbank aufbauen
      m_Connection = DriverManager.getConnection("jdbc:sqlite:lif_DB.db");  
    } catch (Exception e) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
  }
  
  public void InitPrepStatements()
  {
      try {
      
      //// Beruf-Statements
      m_GetBeruf = m_Connection
              .prepareStatement("SELECT P_ID, FK_TYP_ID, Punkte FROM lif_DB.Beruf WHERE P_ID = ?");
      m_SetBeruf = m_Connection
              .prepareStatement("UPDATE lif_DB.Beruf SET Punkte = ? WHERE P_ID = ?");
      m_GetBerufCount = m_Connection
              .prepareStatement("SELECT COUNT(*) FROM lif_DB.Beruf");      
      ////
      
      //// Berufstyp-Statements
      m_GetBerufstyp = m_Connection
              .prepareStatement("SELECT P_ID, Name, SK1, SK2, SK3, SK4, SK5 FROM lif_DB.Berufstyp WHERE P_ID = ?");
      m_SetBerufstyp = m_Connection
              .prepareStatement("UPDATE lif_DB.Berufstyp SET Name = ?, SET SK1 = ?, SK2 = ?, SK3 = ?, SK4 = ?, SK5 = ? WHERE P_ID = ?");
      m_GetBerufstypCount = m_Connection        
              .prepareStatement("SELECT COUNT(*) FROM lif_DB.Berufstyp");
      ////
      
      //// Dorf-Statements
      m_GetDorf = m_Connection
              .prepareStatement("SELECT P_ID, Name FROM lif_DB.Dorf WHERE P_ID = ?");
      m_SetDorf = m_Connection
              .prepareStatement("UPDATE lif_DB.Dorf SET Name = ? WHERE P_ID = ?");
      m_GetDorfCount = m_Connection
              .prepareStatement("SELECT COUNT(*) FROM lif_DB.Dorf");
      ////
     
      //// Lager-Statements
      m_GetLager = m_Connection
              .prepareStatement("SELECT P_ID, FK_DORF_ID, Name FROM lif_DB.Lager WHERE P_ID = ?");
      m_SetLager = m_Connection
              .prepareStatement("UPDATE lif_DB.Lager SET Name = ? WHERE P_ID = ?");
      m_GetLagerCount = m_Connection
              .prepareStatement("SELECT COUNT(*) FROM lif_DB.Lager");   
      ////
      
      //// LagerBestand-Statements
      m_GetLagerBestand = m_Connection
              .prepareStatement("SELECT P_ID, FK_RES_ID, FK_Lager_ID, Menge  FROM lif_DB.LagerBestand WHERE P_ID = ?");
      m_SetLagerBestand = m_Connection
              .prepareStatement("UPDATE lif_DB.LagerBestand SET Menge = ? WHERE P_ID = ?");
      m_GetLagerBestandCount = m_Connection
              .prepareStatement("SELECT COUNT(*) FROM lif_DB.LagerBestand");    
      ////
      
      //// Mitglied-Statements
      m_GetMitglied = m_Connection
              .prepareStatement("SELECT P_ID, FK_DORF_ID, Name FROM lif_DB.Mitglied WHERE P_ID = ?");
      m_SetMitglied = m_Connection
              .prepareStatement("UPDATE lif_DB.Mitglied SET Name = ? WHERE P_ID = ?");
      m_GetMitgliedCount = m_Connection
              .prepareStatement("SELECT COUNT(*) FROM lif_DB.Mitglied");    
      ////
      
      //// Ressource-Statements
      m_GetRessource = m_Connection
              .prepareStatement("SELECT P_ID, FK_TYP_ID, Punkte FROM lif_DB.Beruf WHERE P_ID = ?");
      m_SetRessource = m_Connection
              .prepareStatement("UPDATE lif_DB.Beruf SET Punkte = ? WHERE P_ID = ?");
      m_GetRessourceCount = m_Connection
              .prepareStatement("SELECT COUNT(*) FROM lif_DB.Beruf");    
       ////
      
      
      // Anm.: vllt geht mit dem Count auch sowas "SELECT COUNT(*) FROM ?" , dann könnte man das sehr viel mehr vereinfachen
      
      } catch (SQLException e)
      {     
      }
    
  }
  
  ///// die Frage ist, ob man überhaupt soviel Macht dem Anwender geben will:
  // AddBeruf, Object ohne Id wird reingegeben und mit Id wieder ausgespuckt
  // RemBeruf, 
  // UpdateBeruf
  public Beruf getBeruf(int berufId) 
  {
      Beruf curBeruf = new Beruf();
      try {
      
      long tmpPID, FK_TYP_ID, Punkte;
     
      m_GetBeruf.setString(1, String.valueOf(berufId));
      m_ResultSet = m_GetBeruf.executeQuery();
      
      tmpPID = m_ResultSet.getLong(1);
      FK_TYP_ID = m_ResultSet.getLong(2);
      Punkte = m_ResultSet.getLong(3);
      
      curBeruf = new Beruf((int)tmpPID, (int)FK_TYP_ID, (int)Punkte);
      
      return curBeruf;
      
      } catch (SQLException e)
      {     
          return curBeruf;
      }
  }
  
  public void setBeruf(Beruf curBeruf)
  {
      try {
     
      m_SetBeruf.setString(1, String.valueOf(curBeruf.getPunkte()));
      m_SetBeruf.setString(2, String.valueOf(curBeruf.getId()));
      m_ResultSet = m_SetBeruf.executeQuery();
      } catch (SQLException e)
      {     
 
      }
  }
  
  public int GetBerufCount()
  {
      try {
          m_ResultSet = m_GetBerufCount.executeQuery();
          return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  
  public Berufstyp GetBerufstyp(int berufstypId)
  {
      Berufstyp curBerufstyp = new Berufstyp();
      try {
      
      long tmpPID, SK1, SK2, SK3, SK4, SK5;
      String Name = "";
      
      m_GetBerufstyp.setString(1, String.valueOf(berufstypId));
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
  
  
  public void SetBerufstyp(Berufstyp curBerufstyp)
  {
      try {
     
      m_SetBerufstyp.setString(1, String.valueOf(curBerufstyp.getName()));
      m_SetBerufstyp.setString(2, String.valueOf(curBerufstyp.getSk1()));
      m_SetBerufstyp.setString(3, String.valueOf(curBerufstyp.getSk2()));
      m_SetBerufstyp.setString(4, String.valueOf(curBerufstyp.getSk3()));
      m_SetBerufstyp.setString(5, String.valueOf(curBerufstyp.getSk4()));
      m_SetBerufstyp.setString(6, String.valueOf(curBerufstyp.getSk5()));
      m_SetBerufstyp.setString(7, String.valueOf(curBerufstyp.getId()));
      
      m_ResultSet = m_SetBerufstyp.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  public int GetBerufstypCount()
  {
      try {
          m_ResultSet = m_GetBerufstypCount.executeQuery();
          return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public Dorf GetDorf(int dorfId)
  {
      Dorf curDorf = new Dorf();
      try {
      
      long tmpPID;
      String Name;
      
      m_GetDorf.setString(1, String.valueOf(dorfId));
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
  
  public void SetDorf(Dorf curDorf)
  {
      try {
   
      m_SetBerufstyp.setString(1, String.valueOf(curDorf.getName()));
      m_SetBerufstyp.setString(2, String.valueOf(curDorf.getId()));
      
      m_ResultSet = m_SetBerufstyp.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  public int GetDorfCount()
  {
      try {
      m_ResultSet = m_GetDorfCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  public Lager GetLager(int lagerId)
  {
      Lager curLager = new Lager();
      try {
      
      long tmpPID;
      String Name;
      
      m_GetDorf.setString(1, String.valueOf(lagerId));
      m_ResultSet = m_GetDorf.executeQuery();
      
      tmpPID = m_ResultSet.getLong(1);
      Name = m_ResultSet.getObject(2).toString();
      
      curLager = new Lager((int)tmpPID, Name);
      
      return curLager;
      
      } catch (SQLException e)
      {     
          return curLager;
      }
  }
  
  public void SetLager(Lager curLager)
  {
    try {
   
      m_SetLager.setString(1, curLager.getName());
      m_SetLager.setString(2, String.valueOf(curLager.getId()));
      
      m_ResultSet = m_SetLager.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  public int GetLagerCount()
  {
      try {
      m_ResultSet = m_GetLagerCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  /*
  public LagerBestand GetLagerBestand(int lagerBestandId)
  {
           
  }
  
  public void SetLagerBestand(LagerBestand curLagerBestand)
  {
  }
  
  public int GetLagerBestandCount()
  {
  }
  */
  
  public Mitglied GetMitglied(int mitgliedId)
  {
      Mitglied curMitglied = new Mitglied();
      try {
      
      long tmpPID;
      long FK_DORF_ID;
      String Name;
      
      m_GetMitglied.setString(1, String.valueOf(mitgliedId));
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
  
  public void SetMitglied(Mitglied curMitglied)
  {
      try {
   
      m_SetMitglied.setString(1, curMitglied.getName());
      m_SetMitglied.setString(2, String.valueOf(curMitglied.getId()));
      
      m_ResultSet = m_SetMitglied.executeQuery();
      
      } catch (SQLException e)
      {     
 
      }
  }
  
  public int GetMitgliedCount()
  {
      try {
      m_ResultSet = m_GetMitgliedCount.executeQuery();
      return (int)m_ResultSet.getLong(1);
          
      } catch (SQLException e)
      {     
          return 0;
      }
  }
  
  /*
  public Ressource GetRessource(int ressourceId)
  {
  
  }
  
  public void SetRessource(Ressource curRessource)
  {
      
  }
  
  public int GetRessourceCount()
  {
 
  }
  */
}
