/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;


import static java.lang.System.err;
import controleur.Main;




/**
 *
 * @author Ben MUZINGU
 */
class RealInternetAcces implements OfficeInternetAcces{
    private final String employerName;
    private final String employerType;
    private String employerPass;
    /**
     * constructeur
     * @param empName 
     */
    public RealInternetAcces(String empName, String employerPass){
        this.employerName= empName;
        this.employerPass=employerPass;
        this.employerType ="";
    }    

    
    @Override
    public String grandInternetAcces() {

        new Main().lance(employerName,employerPass); 
        
        return "connection reussie";
       // }else return "";
         
    }
}
