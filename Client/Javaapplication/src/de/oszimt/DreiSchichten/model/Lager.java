
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Lager {
    private int id;
    private int dorfID;
    private String name;
    
    
    public Lager(){
        super();
    }
    
    public Lager(int id){
        this();
        this.setId(id);
    }
    
    public Lager(int id, int dorfID){
        this(id);
        this.setDorfId(dorfID);
    }
      
    public Lager(int id, int dorfID, String name) {
        this(id, dorfID);
        this.setName(name);
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public int getDorfId() {
        return dorfID;
    }

    public void setDorfId(int dorfID) {
        this.dorfID = dorfID;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }   
}
