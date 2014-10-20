
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Lager {
    private int id;
    private String name;
    private int[] ressource;
    private int[] bestand;
    
    public Lager(){
        super();
    }
    
    public Lager(int id){
        this();
        this.setId(id);
    }
    
    public Lager(int id, String name){
        this(id);
        this.setName(name);
    }
    
    public Lager(int id, String name, int[] ressource, int[] bestand){
        this(id, name);
        this.setRessource(ressource);
        this.setBestand(bestand);
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
     * @return the bestand
     */
    public int[] getBestand() {
        return bestand;
    }

    /**
     * @param bestand the bestand to set
     */
    public void setBestand(int[] bestand) {
        this.bestand = bestand;
    }

    /**
     * @return the ressource
     */
    public int[] getRessource() {
        return ressource;
    }

    /**
     * @param ressource the ressource to set
     */
    public void setRessource(int[] ressource) {
        this.ressource = ressource;
    }
    
}
