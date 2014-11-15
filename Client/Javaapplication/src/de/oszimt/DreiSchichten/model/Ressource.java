package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Ressource {
    private int id;
    private String name;
    private int gewicht;
    private int preis;
    
    public Ressource(){
        super();
    }
    
    public Ressource(int id){
        this();
        this.id=id;
    }
    
    public Ressource(int id, String name){
        this(id);
        this.name=name;
    }
    
    public Ressource(int id, String name, int gewicht){
        this(id, name);
        this.gewicht = gewicht;
    }
    
    public Ressource(int id, String name, int gewicht, int preis){
        this(id, name, gewicht);        
        this.preis = preis;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getGewicht() {
        return gewicht;
    }
    
    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }
    
    public int getPreis() {
        return preis;
    }
    
    public void setPreis(int preis) {
        this.preis = preis;
    }
}
