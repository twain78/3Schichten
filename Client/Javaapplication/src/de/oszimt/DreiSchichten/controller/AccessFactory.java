/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oszimt.DreiSchichten.controller;

/**
 *
 * @author keinMensch
 */

public class AccessFactory {
    
    public IAccess getAccess(AccessType accessType)
    {
        switch(accessType)
        {
            case DATABASE_SQLITE:
                return new DBAccess();                
            
            default:
            case XML_FILE:            
                return new XMLAccess();                                            
        }        
    }
}
