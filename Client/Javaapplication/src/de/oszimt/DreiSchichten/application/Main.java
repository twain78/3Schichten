
package de.oszimt.DreiSchichten.application;

import de.oszimt.DreiSchichten.database.DBAccess;

/**
 *
 * @author b-kg104
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // 1. DBAcess.Object initialisieren
        DBAccess dbAccess = new DBAccess();
        dbAccess.InitConnection();
        
    }
    

}