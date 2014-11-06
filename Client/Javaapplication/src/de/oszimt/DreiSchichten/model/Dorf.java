
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Dorf {
    private int id;
    private String name;
    private int[] lagerIDs;
    private int[] mitgliederIDs;
    
    public Dorf(){
        super();
    }
    
    public Dorf(int id){
        this();
        this.setId(id);
    }
    
    public Dorf(int id, String name){
        this();
        this.setId(id);
        this.setName(name);
    }
    
    public Dorf(int id, String name, int[] lagerIDs, int[] mitgliederIDs){
        this(id, name);
        this.setLagerIDs(lagerIDs);
        this.setMitgliederIDs(mitgliederIDs);
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
     * @param name the name to set
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
     * @return the lagerIDs
     */
    public int[] getLagerIDs() {
        return lagerIDs;
    }

    /**
     * @param lagerIDs the lagerIDs to set
     */
    public void setLagerIDs(int[] lagerIDs) {
        this.lagerIDs = lagerIDs;
    }

    /**
     * @return the mitgliederIDs
     */
    public int[] getMitgliederIDs() {
        return mitgliederIDs;
    }

    /**
     * @param mitgliederIDs the mitgliederIDs to set
     */
    public void setMitgliederIDs(int[] mitgliederIDs) {
        this.mitgliederIDs = mitgliederIDs;
    }
}
