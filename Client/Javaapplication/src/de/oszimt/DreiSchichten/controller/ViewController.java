package de.oszimt.DreiSchichten.controller;

import de.oszimt.DreiSchichten.view.Dorfliste;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Konstantin Görlitz
 */
public class ViewController {
    JFrame frame;
    DBAccess db;
    JPanel aktuellesPanel;
    
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
        d.setDörfer(db.getDörfer());
    }
    
    public void changePanel(String value, int id){
        switch(value){
            case "Dörfer":
                this.aktuellesPanel=new Dorfliste(db.getDörfer());
                frame.add(aktuellesPanel);
                break;
            case "Lager":
                this.aktuellesPanel=new Lager(db.getLager);
                frame.add(aktuellesPanel);
                break;
            default:
                break;
        }
    }
}
