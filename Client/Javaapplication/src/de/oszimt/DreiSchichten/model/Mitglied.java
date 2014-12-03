
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author Konstantin Görlitz
 */
public class Mitglied {
    private int id;
    private int dorfID;
    private String name;
    private int[] berufIDs;

    public Mitglied(){
        super();
    }
    
    public Mitglied(int id){
        this();
        this.setId(id);
    }
    
    public Mitglied(int id, int dorfID){
        this(id);
        this.setDorfID(dorfID);
    }
    
    public Mitglied(int id, int dorfID, String name){
        this(id, dorfID);
        this.setName(name);
    }
    
    public Mitglied(int id, int dorfID, String name, int[] berufIDs){
        this(id, dorfID, name);
        this.setBerufIDs(berufIDs);
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
     * @return the dorfID
     */
    public int getDorfID() {
        return dorfID;
    }

    /**
     * @param dorfID the dorfID to set
     */
    public void setDorfID(int dorfID) {
        this.dorfID = dorfID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the berufIDs
     */
    public int[] getBerufIDs() {
        return berufIDs;
    }

    /**
     * @param berufIDs the berufIDs to set
     */
    public void setBerufIDs(int[] berufIDs) {
        this.berufIDs = berufIDs;
    }
}
