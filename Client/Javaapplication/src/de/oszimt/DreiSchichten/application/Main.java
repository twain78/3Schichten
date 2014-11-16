
package de.oszimt.DreiSchichten.application;

import de.oszimt.DreiSchichten.controller.DBAccess;
import de.oszimt.DreiSchichten.controller.XMLAccess;
import de.oszimt.DreiSchichten.model.Dorf;
import de.oszimt.DreiSchichten.model.Beruf;
import javax.swing.JFrame;

/**
 * LastChange: 09.11.2014
 * @author Konstantin Görlitz, Robert Schardt
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                    
        // 1a. DBAcess.Object initialisieren / hauptsächlich Verbindung herstellen -- Data
        /*DBAccess dbAccess = new DBAccess();
        if(dbAccess.isInitialized())
        {
            Dorf testDorf = dbAccess.getDorf(1);
        }  */            
        
        // 1b. XML-DB erstellen, wenn nötig -- Data
        /*
        XMLAccess domParser = new XMLAccess();
        domParser.CreateDatabase();
         */    
   
        // 2. Counts der Tabellen entsprechend auslesen und Klassen-Arrays erstellen -- Modell
        
        // 3. Klassen befüllen mit Daten, DB --> Modell
        
        // 4a. GUI erstellen -- View
         
        // 4b. TUI erstellen -- View
        
        // 5. Daten in GUI/TUI anzeigen lassen Modell --> GUI/TUI
        
        // ...
        JFrame frame = new JFrame("BerufsDB");
        frame.setSize(600,800);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}