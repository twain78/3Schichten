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
      
      long tmpPID, FK_TYP_ID, Punkte;
     
      m_SetBeruf.setString(1, String.valueOf(curBeruf.getPunkte()));
      m_SetBeruf.setString(2, String.valueOf(curBeruf.getId()));
      m_ResultSet = m_SetBeruf.executeQuery();
      } catch (SQLException e)
      {     
 
      }
  }
  
  
 /*
  /// Rückgabe der ganzen Daten eines Dorfes anhand der ID -- Edit-Marker
  // Name eines spezifischen Dorfes zurückgeben
  public void readDorfName(int dorfId) throws Exception {
    try {
      // Name des Dorfes mit Id = ? zurückgeben
      getNamePrepStatement = connect
      .prepareStatement("SELECT Name FROM Dorf WHERE P_ID = ?; ");
      
      // parameters start with 1
      getNamePrepStatement.setString(1, dorfId);
      resultSet = getNamePrepStatement.executeQuery();
      writeResultSet(resultSet);
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  // Daten der Dörfer-Tabelle zusammenstellen
  public void getDoerferData() throws Exception {
    try {
      // Name des Dorfes mit Id = ? zurückgeben
      getNamePrepStatement = connect
      .prepareStatement("SELECT * FROM Dorf; ");
      
      //// Edit-Marker
      // Entweder mit cleveren Join bzw. SubSelect die Tabelle zusammenbauen
      
      // oder durch das ResultSet iterieren 
      // und 3SQL-Abfragen am Ende produzieren
      
      // parameters start with 1
      getNamePrepStatement.setString(1, dorfId);
      resultSet = getNamePrepStatement.executeQuery();
      writeResultSet(resultSet);
      
      
      // Bevölkerungsanazhl zu jedem Dorf
      
      // Lageranzahl zu jedem Dorf
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  
  // Anzahl der Dörfer zurückgeben
  public void readDorfCount() throws Exception {
    try {
      // Anzahl der Dörfer zurückgeben
      getDorfCountPrepStatement = connect
      .prepareStatement("SELECT COUNT(*) FROM Dorf; ");
      
      resultSet = getDorfCountPrepStatement.executeQuery();
      writeResultSet(resultSet);
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  

  // Mitgliedername Methoden noch implementieren
  //
  
  public void getMitgliedList() throws Exception {
    try {
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  } 
  
  public void addMitglied(string name, string beruf) throws Exception {
    try {
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  } 
  
  public void updateMitglied(string name, string beruf) throws Exception {
    try {
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  } 
  
  public void getRessourceData(int ressourceId) throws Exception {
    try {
      
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  public void addRessourceData(int ressourceId, int anzahl) throws Exception {
    try {
      
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  public void removeRessourceData(int ressourceId, int anzahl) throws Exception {
    try {
      
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  // amount wird immer durch Trigger inenrhalb der Datenbank neu berechnet
  public void addLager(string lagername)throws Exception {
    try {
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  // amount wird immer durch Trigger inenrhalb der Datenbank neu berechnet
  public void updateLager(int lagerId, string lagerName)throws Exception {
    try {
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  
  public void getLagerData() throws Exception {
    try {
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
  public void getLagerBestand() {
    try {
      
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  */
}
