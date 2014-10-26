
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author konstantin.görlitz
 */
public class Berufstyp {
    private int id;
    private String name;
    private int sk1;
    private int sk2;
    private int sk3;
    private int sk4;
    private int sk5;
    
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
    
    public Berufstyp(int id, String name, int sk1, int sk2, int sk3, int sk4, int sk5){
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
    public int getSk1() {
        return sk1;
    }

    /**
     * @param sk1 the sk1 to set
     */
    public void setSk1(int sk1) {
        this.sk1 = sk1;
    }

    /**
     * @return the sk2
     */
    public int getSk2() {
        return sk2;
    }

    /**
     * @param sk2 the sk2 to set
     */
    public void setSk2(int sk2) {
        this.sk2 = sk2;
    }

    /**
     * @return the sk3
     */
    public int getSk3() {
        return sk3;
    }

    /**
     * @param sk3 the sk3 to set
     */
    public void setSk3(int sk3) {
        this.sk3 = sk3;
    }

    /**
     * @return the sk4
     */
    public int getSk4() {
        return sk4;
    }

    /**
     * @param sk4 the sk4 to set
     */
    public void setSk4(int sk4) {
        this.sk4 = sk4;
    }

    /**
     * @return the sk5
     */
    public int getSk5() {
        return sk5;
    }

    /**
     * @param sk5 the sk5 to set
     */
    public void setSk5(int sk5) {
        this.sk5 = sk5;
    }
}
