package de.oszimt.DreiSchichten.controller;

import de.oszimt.DreiSchichten.model.Beruf;
import de.oszimt.DreiSchichten.model.Berufstyp;
import de.oszimt.DreiSchichten.model.Dorf;
import de.oszimt.DreiSchichten.model.LagerBestand;
import de.oszimt.DreiSchichten.model.Mitglied;
import de.oszimt.DreiSchichten.model.Ressource;
import de.oszimt.DreiSchichten.view.Dorfliste;
import de.oszimt.DreiSchichten.view.Lagerliste;
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
        d.setDörfer(db.getDorfliste());
    }
    
    public void changePanel(String value, int id){
        switch(value){
            case "Dorfliste":
                this.aktuellesPanel=new Dorfliste(db.getDorfliste());
                frame.add(aktuellesPanel);
                break;
            case "Lagerliste":
                this.aktuellesPanel=new Lagerliste(db.getLagerliste(id));
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
    
    public Ressource[] getRessourcen(){
        return db.getRessourcen();
    }
    
    public Berufstyp[] getBerufstypen(){
        return db.getBerufstypen();
    }
    
    public void updateLagerbestand(int id, String ressourcename, int menge){
        db.updateLagerbestand(id, ressourcename, menge);
    }
    
    public void updateBeruf(int id, String berufsname, int punkte){
        db.updateBeruf(id, berufsname, punkte);
    }
    
    public LagerBestand[] getLagerbestand(int lagerid){
        return db.getLagerBestände(lagerid);
    }
    
    public String getBerufName(int id){
        return db.getBerufstyp(id).getName();
    }
    
    public Beruf[] getBerufe(int mitgliedid){
        return db.getBerufe(mitgliedid);
    }
    
    public void setBucketDorf(int id){
        bucket.setDorf(db.getDorf(id));
    }
    
    public Dorf getBucketDorf(){
        return bucket.getDorf();
    }
    
    public void addDorf(String dorfname){
        db.addDorf(new Dorf(0, dorfname));
    }
    
    public void addMitglied(String mitgliedsname){
        db.addMitglied(new Mitglied(0, bucket.getDorf().getId(), mitgliedsname));
    }
}
