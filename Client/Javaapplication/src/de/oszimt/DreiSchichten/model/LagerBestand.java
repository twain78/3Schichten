/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oszimt.DreiSchichten.model;

/**
 *
 * @author keinMensch
 */
public class LagerBestand {
    private int id;
    private int resID;
    private int lagerID;
    private int menge;
    
    public LagerBestand(){
        super();
    }
    
    public LagerBestand(int id){
        this();
        this.setId(id);
    }
    
    public LagerBestand(int id, int resID){
        this(id);
        this.setResId(resID);
    }
    
    public LagerBestand(int id, int resID, int lagerID){
        this(id, resID);
        this.setLagerId(lagerID);    
    }
    
    public LagerBestand(int id, int resID, int lagerID, int menge){
        this(id, resID, lagerID);
        this.setMenge(menge);    
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getResId() {
        return resID;
    }

    public void setResId(int resId) {
        this.resID = resID;
    }
    
    public int getLagerId() {
        return lagerID;
    }

    public void setLagerId(int lagerId) {
        this.id = id;
    }
    
    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }
}
