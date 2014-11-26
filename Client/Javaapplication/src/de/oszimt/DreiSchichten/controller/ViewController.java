package de.oszimt.DreiSchichten.controller;

import de.oszimt.DreiSchichten.model.Beruf;
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
    
    public ViewController(){
        super();
    }
    
    public ViewController(JFrame frame, DBAccess db){
        this();
        this.frame = frame;
        this.db = db;
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
    
    public String[] getBerufname(int[] ids){
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
    
    public void updateLagerbestand(int id, String ressourcename, int menge){
        db.updateLagerbestand(id, ressourcename, menge);
    }
}
