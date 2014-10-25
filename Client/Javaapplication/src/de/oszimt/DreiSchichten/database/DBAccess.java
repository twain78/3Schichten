import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

package de.oszimt.DreiSchichten.database;

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
    private Connection m_Connect;
  private Statement m_Statement;
  private PreparedStatement m_PreparedStatement;
  private ResultSet m_ResultSet;
  
  
  public DBAccess() {
    m_Connect = null;
    m_Statement = null;
    m_PreparedStatement = null;
    m_ResultSet = null;
    
    // Verbindung intialisieren
    initConnection();
  }
  
  // Verbindung intialisieren
  public void initConnection() throws Exception {
    try {
      // Treiber laden
      Class.forName("com.mysql.jdbc.Driver");
      
      // Verbindung zur Datenbank aufbauen
      connect = DriverManager
      .getConnection("jdbc:mysql://localhost/feedback?"
      + "user=sqluser&password=sqluserpw");  
    }
    catch (Exception e) {
      throw e;
    }
    finally {
      close();
    }
  }
  
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
  
  // Edit-Marker
  /*
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
  
  /// Dummy-Funktion zum Anschauen von Beispiel-Code
  public void readDataBase() throws Exception {
    try {
      // Treiber laden
      Class.forName("com.mysql.jdbc.Driver");
      // Verbindung zur Datenbank aufbauen
      connect = DriverManager
      .getConnection("jdbc:mysql://localhost/feedback?"
      + "user=sqluser&password=sqluserpw");
      
      // statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // resultSet gets the result of the SQL query
      resultSet = statement
      .executeQuery("select * from Mitglied where FK_Dorf_ID = 1");
      writeResultSet(resultSet);
      
      // preparedStatements can use variables and are more efficient
      preparedStatement = connect
      .prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
      // "myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
      // parameters start with 1
      preparedStatement.setString(1, "Test");
      preparedStatement.setString(2, "TestEmail");
      preparedStatement.setString(3, "TestWebpage");
      preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
      preparedStatement.setString(5, "TestSummary");
      preparedStatement.setString(6, "TestComment");
      preparedStatement.executeUpdate();
      
      preparedStatement = connect
      .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from FEEDBACK.COMMENTS");
      resultSet = preparedStatement.executeQuery();
      writeResultSet(resultSet);
      
      // remove again the insert comment
      preparedStatement = connect
      .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
      preparedStatement.setString(1, "Test");
      preparedStatement.executeUpdate();
      
      resultSet = statement
      .executeQuery("select * from FEEDBACK.COMMENTS");
      writeMetaData(resultSet);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }
    
  }
  
  // -- Edit-Marker
  private void writeMetaData(ResultSet resultSet) throws SQLException {
    // now get some metadata from the database
    System.out.println("The columns in the table are: ");
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }
  
  // -- Edit-Marker
  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // resultSet is initialised before the first data set
    while (resultSet.next()) {
      // it is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g., resultSet.getSTring(2);
      String user = resultSet.getString("myuser");
      String website = resultSet.getString("webpage");
      String summary = resultSet.getString("summary");
      Date date = resultSet.getDate("datum");
      String comment = resultSet.getString("comments");
      System.out.println("User: " + user);
      System.out.println("Website: " + website);
      System.out.println("Summary: " + summary);
      System.out.println("Date: " + date);
      System.out.println("Comment: " + comment);
    }
  }
  
  // you need to close all three to make sure
  private void close() {
    close(resultSet);
    close(statement);
    close(connect);
  }
  private void close(Closeable c) {
    try {
      if (c != null) {
        c.close();
      }
    } catch (Exception e) {
      // don't throw now as it might leave following closables in undefined state
    }
  }
}
