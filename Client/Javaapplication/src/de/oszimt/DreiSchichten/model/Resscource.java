package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Resscource {
    private int id;
    private String name;
    
    public Resscource(){
        super();
    }
    
    public Resscource(int id){
        this();
        this.id=id;
    }
    
    public Resscource(int id, String name){
        this(id);
        this.name=name;
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
    
}
