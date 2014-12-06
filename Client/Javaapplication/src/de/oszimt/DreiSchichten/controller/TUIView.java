package de.oszimt.DreiSchichten.controller;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Robert Schardt
 */
public class TUIView {
    
    private IAccess db;     
    
    public TUIView(){
        super();
    }
    
    public TUIView(DBAccess db){
        this();       
        this.db = db;
        init();
    }
    
    void init(){
        createMenu();                                
    }
    
    private void createMenu() {
        Scanner scan = new Scanner(System.in);
        boolean bQuit = false;        
        
        printMenuText();        
        String input;
        do {            
            input = scan.next();            
            if(checkInput(input) == false)
                continue;
                
            bQuit = doAction(input);
        } while (!bQuit);
      }
    
    public void printMenuText() 
    {        
        printHeader();            
        System.out.println("\n");
        System.out.println("###################   Menü   ######################");            

        printOptions();
        System.out.println("\n");
        System.out.println("Warte auf Eingabe: ");
    }
    
    public void printHeader() {
        System.out.println("OSZIMT:AS:FI/Anwendungsentwicklung        5.Halbjahr\n");
        System.out.println("GRUPPE 5       Konstantin Görlitz und Robert Schardt\n");
    }
    
    public void printOptions() {            
        printShowOptions();
        printAddOptions();
        printModifyOptions();        
        printMenuOptions();        
    }
    
    public void printShowOptions()
    {
        System.out.println("###################   Anzeigen  ###################");      
        System.out.println("# Dorfliste anzeigen                           (aa)#");
        System.out.println("# Lagerliste anzeigen                          (ab)#");
        System.out.println("# Berufsliste anzeigen                         (ac)#");
        System.out.println("# Lagerinhalt anzeigen                         (ad)#");
        System.out.println("# Mitgliederliste anzeigen                     (ae)#");
    
    }
    
    public void printAddOptions()
    {
        System.out.println("###################  Hinzufügen ###################");
        System.out.println("# Dorf hinzufügen                              (ba)#");
        System.out.println("# Lager hinzufügen                             (bb)#");
        System.out.println("# Mitglied hinzufügen                          (bc)#");  
    }
        
    public void printModifyOptions()
    {
        System.out.println("###################  Bearbeiten ###################");
        System.out.println("# Dorf bearbeiten                              (ca)#");
        System.out.println("# Lager bearbeiten                             (cb)#");
        System.out.println("# Mitglied bearbeiten                          (cc)#");       
    }
    
    public void printMenuOptions()
    {
        System.out.println("###################################################");
        System.out.println("# Menue                                         (m)#");        
        System.out.println("# zurück                                        (z)#");
        System.out.println("# Programm beenden                              (q)#");
        System.out.println("###################################################");
    }
    
    boolean checkInput(String input) {
        return !(input.length() < 1 || input.length() > 2);
    }
    
    public boolean doAction(String input){
        
        char action = input.charAt(0);
        switch(action){
           case 'a':       // anzeigen
               showTable(input);
               break;
           case 'b':       // hinzufügen
               addTable(input);
               break;
           case 'c':       // ändern
               modifyTable(input);
               break;

           case 'm':       // menü
               printMenuText();
           case 'z':       // zurück
               break;
           case 'q':       // beenden
               return true;

           default:
               break;
        }
             
        return false;
    }
    
    public void showTable(String input)
    {
        char table = input.charAt(1);
        switch(table){
           case 'a':       // Dorfliste
               showDorflist();
               break;
           case 'b':       // Berufsliste
               showBerufslist();
               break;
           case 'c':       // Lagerinhalt
               showLagerinhalt();
               break;
           case 'd':       // Mitgliederliste
               showMitgliederliste();
               break;

           default:
               break;
        }
    }
    
    public void showDorflist()
    {    
    }
    
    public void showBerufslist()
    {
    }
    
    public void showLagerinhalt()
    {    
    }
    
    public void showMitgliederliste()
    {    
    }
    
    public void addTable(String input)
    {
        char table = input.charAt(1);
        switch(table){
           case 'a':       // Dorf
               addDorf();
               break;
           case 'b':       // Lager
               addLager();
               break;
           case 'c':       // Mitglied
               addMitglied();
               break;

           default:
               break;
        }
    }
    
    public void addDorf()
    {    
    }
    
    public void addLager()
    {
    }
    
    public void addMitglied()
    {    
    }

    public void modifyTable(String input)
    {
        char table = input.charAt(1);
             switch(table){
                case 'a':       // Dorf
                    modifyDorf();
                    break;
                case 'b':       // Lager
                    modifyLager();
                    break;
                case 'c':       // Mitglied
                    modifyMitglied();
                    break;
                           
                default:
                    break;
        }
    }
    
    public void modifyDorf()
    {    
    }
    
    public void modifyLager()
    {
    }
    
    public void modifyMitglied()
    {    
    }
}


