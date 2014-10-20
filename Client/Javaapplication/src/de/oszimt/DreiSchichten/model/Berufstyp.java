
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Berufstyp {
    private int id;
    private String name;
    private String sk1;
    private String sk2;
    private String sk3;
    private String sk4;
    private String sk5;
    
    public Berufstyp(){
        super();
    }
    
    public Berufstyp(int id){
        this();
        this.setId(id);
    }
    
    public Berufstyp(int id, String name){
        this(id);
        this.setName(name);
    }
    
    public Berufstyp(int id, String name, String sk1, String sk2, String sk3, String sk4, String sk5){
        this(id, name);
        this.setSk1(sk1);
        this.setSk2(sk2);
        this.setSk3(sk3);
        this.setSk4(sk4);
        this.setSk5(sk5);
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
     * @return the sk1
     */
    public String getSk1() {
        return sk1;
    }

    /**
     * @param sk1 the sk1 to set
     */
    public void setSk1(String sk1) {
        this.sk1 = sk1;
    }

    /**
     * @return the sk2
     */
    public String getSk2() {
        return sk2;
    }

    /**
     * @param sk2 the sk2 to set
     */
    public void setSk2(String sk2) {
        this.sk2 = sk2;
    }

    /**
     * @return the sk3
     */
    public String getSk3() {
        return sk3;
    }

    /**
     * @param sk3 the sk3 to set
     */
    public void setSk3(String sk3) {
        this.sk3 = sk3;
    }

    /**
     * @return the sk4
     */
    public String getSk4() {
        return sk4;
    }

    /**
     * @param sk4 the sk4 to set
     */
    public void setSk4(String sk4) {
        this.sk4 = sk4;
    }

    /**
     * @return the sk5
     */
    public String getSk5() {
        return sk5;
    }

    /**
     * @param sk5 the sk5 to set
     */
    public void setSk5(String sk5) {
        this.sk5 = sk5;
    }
    
    
}
