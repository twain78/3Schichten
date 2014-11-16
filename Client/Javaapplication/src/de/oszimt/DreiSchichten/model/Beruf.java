
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Beruf {
    private int id;
    private int typID;
    private int mitgliedID;
    private int punkte;

    public Beruf(){
        super();
    }
    
    public Beruf(int id){
        this();
        this.setId(id);
    }
    
    public Beruf(int id, int typID){
        this(id);
        this.setTypID(typID);
    }
    
    public Beruf(int id, int typID, int mitgliedID){
        this(id, typID);
        this.setMitgliedID(mitgliedID);
    }
    
    public Beruf(int id, int typID, int mitgliedID, int punkte){
        this(id, typID, mitgliedID);
        this.setPunkte(punkte);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the typID
     */
    public int getTypID() {
        return typID;
    }

    /**
     * @param typID the typID to set
     */
    public void setTypID(int typID) {
        this.typID = typID;
    }

    public int getMitgliedID() {
        return mitgliedID;
    }
    
    public void setMitgliedID(int mitgliedID) {
        this.mitgliedID = mitgliedID;
    }
    
    /**
     * @return the punkte
     */
    public int getPunkte() {
        return punkte;
    }

    /**
     * @param punkte the punkte to set
     */
    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }
    
    
}
