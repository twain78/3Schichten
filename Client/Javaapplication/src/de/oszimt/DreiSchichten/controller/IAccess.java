/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oszimt.DreiSchichten.controller;


// Model-Imports
import de.oszimt.DreiSchichten.model.Beruf;
import de.oszimt.DreiSchichten.model.Berufstyp;
import de.oszimt.DreiSchichten.model.Dorf;
import de.oszimt.DreiSchichten.model.Lager;
import de.oszimt.DreiSchichten.model.LagerBestand;
import de.oszimt.DreiSchichten.model.Mitglied;
import de.oszimt.DreiSchichten.model.Ressource;


/**
 * LastChange: 23.11.2014
 * @author Robert Schardt
 */
public interface IAccess {
   
  public Beruf getBeruf(int berufId);
  public int getBerufCount();
  public void setBeruf(Beruf curBeruf);
  public int addBeruf(Beruf newBeruf);
  public void remBeruf(int berufId);

  
  public Berufstyp getBerufstyp(int berufstypId);
  public int getBerufstypCount();
  public void setBerufstyp(Berufstyp curBerufstyp);
  public int addBerufstyp(Berufstyp newBerufstyp);
  public void remBerufstyp(int berufstypId);
  
  
  public Dorf getDorf(int dorfId);
  public Dorf[] getDörfer();                  
  public int getDorfCount();
  public void setDorf(Dorf curDorf);
  public int addDorf(Dorf newDorf);
  public void remDorf(int dorfId);

  
  public Lager getLager(int lagerId);
  public int getLagerCount();
  public void setLager(Lager curLager);
  public int addLager(Lager newLager);
  public void remLager(int lagerId);

 
  public LagerBestand getLagerBestand(int lagerbestandId);
  public int getLagerBestandCount();
  public void setLagerBestand(LagerBestand curLagerBestand);
  public int addLagerBestand(LagerBestand newLagerBestand);
  public void remLagerBestand(int lagerbestandId);

  
  public Mitglied getMitglied(int mitgliedId);
  public int getMitgliedCount();
  public void setMitglied(Mitglied curMitglied);
  public int addMitglied(Mitglied newMitglied);
  public void remMitglied(int mitgliedId);


  public Ressource getRessource(int ressourceId);
  public int getRessourceCount();
  public void setRessource(Ressource curRessource);
  public int addRessource(Ressource newRessource);
  public void remRessource(int ressourceId);
}
