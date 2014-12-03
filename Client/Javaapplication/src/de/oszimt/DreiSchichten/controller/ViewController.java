package de.oszimt.DreiSchichten.controller;

import de.oszimt.DreiSchichten.model.Beruf;
import de.oszimt.DreiSchichten.model.Berufstyp;
import de.oszimt.DreiSchichten.model.Dorf;
import de.oszimt.DreiSchichten.model.Lager;
import de.oszimt.DreiSchichten.model.LagerBestand;
import de.oszimt.DreiSchichten.model.Mitglied;
import de.oszimt.DreiSchichten.model.Ressource;
import de.oszimt.DreiSchichten.view.Dorfliste;
import de.oszimt.DreiSchichten.view.Lagerliste;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Konstantin Görlitz
 */
public class ViewController {
    private JFrame frame;
    private IAccess db;
    private JPanel aktuellesPanel;
    private Bucket bucket;
    
    public ViewController(){
        super();
    }
    
    public ViewController(JFrame frame, DBAccess db){
        this();
        this.frame = frame;
        this.db = db;
        this.bucket = new Bucket();
        init();
    }
    
    void init(){
        this.aktuellesPanel=new Dorfliste();
        frame.add(aktuellesPanel);
        frame.setVisible(true);
        Dorfliste d = (Dorfliste)aktuellesPanel;
        d.setDörfer(db.getDorfListe());
    }
    
    public void changePanel(String value, int id){
        switch(value){
            case "Dorfliste":
                this.aktuellesPanel=new Dorfliste(db.getDorfListe());
                frame.add(aktuellesPanel);
                break;
            case "Lagerliste":
                this.aktuellesPanel=new Lagerliste(db.getLagerListe(id));
                frame.add(aktuellesPanel);
                break;
            default:
                break;
        }
    }
    
    public void deleteDorf(int id){
        db.remDorf(id);
    }
    
    public void deleteLager(int id){
        db.remLager(id);
    }
    
    public void deleteMitglied(int id){
        db.remMitglied(id);
    }
    
    public void deleteLagerbestand(int id){
        db.remLagerBestand(id);
    }
    
    public void deleteBeruf(int id){
        db.remBeruf(id);
    }
    
    public String[] getBerufnamen(int[] ids){
        String[] berufe = new String[ids.length];
        int i = 0;
        for(int id: ids){
            Beruf beruf = (Beruf)db.getBeruf(id);
            berufe[i]=db.getBerufstyp(beruf.getTypID()).getName();
            i++;
        }
        return berufe;
    }
    
    public String getRessourceName(int id){
        return db.getRessource(id).getName();
    }
    
    public List<Ressource> getRessourcen(){
        return db.getRessourcen();
    }
    
    public List<Berufstyp> getBerufstypen(){
        return db.getBerufstypen();
    }
    
    public void updateLagerbestand(LagerBestand lagerbestand){
        db.setLagerBestand(lagerbestand);
    }
    
    public void updateBeruf(Beruf beruf){
        db.setBeruf(beruf);
    }
    
    public List<LagerBestand> getLagerbestand(int lagerid){
        return db.getLagerBestaende(lagerid);
    }
    
    public String getBerufName(int id){
        return db.getBerufstyp(id).getName();
    }
    
    public List<Beruf> getBerufe(int mitgliedid){
        return db.getBerufe(mitgliedid);
    }
    
    public void setBucketDorf(int id){
        bucket.setDorf(db.getDorf(id));
    }
    
    public Dorf getBucketDorf(){
        return bucket.getDorf();
    }
    
    public void addDorf(Dorf dorf){
        db.addDorf(dorf);
    }
    
    public void setDorf(Dorf dorf){
        db.setDorf(dorf);
    }
    
    public void setMitglied(Mitglied mitglied){
        db.setMitglied(mitglied);
    }
    
    public void addMitglied(Mitglied mitglied){
        db.addMitglied(mitglied);
    }
    
    public void setLager(Lager lager){
        db.setLager(lager);
    }
    
    public void addLager(Lager lager){
        db.addLager(lager);
    }
}
