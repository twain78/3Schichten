/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oszimt.DreiSchichten.controller;

import java.util.List;

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
<<<<<<< HEAD
  public List<Beruf> getBerufe (int mitgliedId);
  public int getBerufCount();  
  public void setBeruf(Beruf curBeruf);
  public int addBeruf(Beruf newBeruf); 
=======
  public int getBerufCount();
  public Beruf[] getBerufe(int mitgliedId);
  public void setBeruf(Beruf curBeruf);
  public int addBeruf(Beruf newBeruf);
  public void updateBeruf(int id, String berufsname, int punkte);
>>>>>>> upstream/master
  public void remBeruf(int berufId);

  
  public Berufstyp getBerufstyp(int berufstypId);
<<<<<<< HEAD
  public List<Berufstyp> getBerufstypen();
  public int getBerufstypCount();  
=======
  public int getBerufstypCount();
  public Berufstyp[] getBerufstypen();
>>>>>>> upstream/master
  public void setBerufstyp(Berufstyp curBerufstyp);
  public int addBerufstyp(Berufstyp newBerufstyp);
  public void remBerufstyp(int berufstypId);
  
  
  public Dorf getDorf(int dorfId);
<<<<<<< HEAD
  public List<Dorf> getDorfListe();                  
=======
  public Dorf[] getDorfliste();                  
>>>>>>> upstream/master
  public int getDorfCount();
  public void setDorf(Dorf curDorf);
  public int addDorf(Dorf newDorf);
  public void remDorf(int dorfId);

  
  public Lager getLager(int lagerId);
<<<<<<< HEAD
  public List<Lager> getLagerListe(int dorfId);
=======
  public Lager[] getLagerliste(int dorfId);
>>>>>>> upstream/master
  public int getLagerCount();
  public void setLager(Lager curLager);
  public int addLager(Lager newLager);
  public void remLager(int lagerId);

 
  public LagerBestand getLagerBestand(int lagerbestandId);
<<<<<<< HEAD
  public List<LagerBestand> getLagerBestaende(int lagerId);
  public int getLagerBestandCount();  
=======
  public int getLagerBestandCount();
  public LagerBestand[] getLagerBestände(int lagerId);
  public void updateLagerbestand(int id, String ressourcename, int menge);
>>>>>>> upstream/master
  public void setLagerBestand(LagerBestand curLagerBestand);
  public int addLagerBestand(LagerBestand newLagerBestand);
  public void remLagerBestand(int lagerbestandId);

  
  public Mitglied getMitglied(int mitgliedId);
  public List<Mitglied> getMitglieder();
  public int getMitgliedCount();
  public void setMitglied(Mitglied curMitglied);
  public int addMitglied(Mitglied newMitglied);
  public void remMitglied(int mitgliedId);


  public Ressource getRessource(int ressourceId);
<<<<<<< HEAD
  public List<Ressource> getRessourcen();
=======
  public Ressource[] getRessourcen();
>>>>>>> upstream/master
  public int getRessourceCount();
  public void setRessource(Ressource curRessource);
  public int addRessource(Ressource newRessource);
  public void remRessource(int ressourceId);
}
