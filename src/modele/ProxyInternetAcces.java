/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static modele.DatabaseAccess.Rs;
import static modele.DatabaseAccess.St;

/**
 *
 * @author Ben MUZINGU
 */
public class ProxyInternetAcces implements OfficeInternetAcces{
    private final String employerName;
    private RealInternetAcces realacces;
    private String employerType;
    private final String employerPasse;
    /**
     * constructeur .
     *
     * @param employerName
     * @param employerPasse
     */
    public ProxyInternetAcces(String employerName, String employerPasse){
        this.employerName= employerName;
        this.employerPasse= employerPasse;
    }
    public ProxyInternetAcces(){
        this.employerName= "";
        this.employerPasse= "";
    }
    /**
     * grang internet acces
     * 
     */
    
    /**
     * grang internet acces
     * @return
     */
    @Override
    public String grandInternetAcces() {
        if(islog2(employerName,employerPasse)==""){
            realacces = new RealInternetAcces(employerName,employerPasse);
            realacces.grandInternetAcces();
            //System.out.print("ok");
            System.out.print(employerName+" "+employerPasse);
            return "";
            
        }else{
            return "Login Incorrect";
        }
    }
    /**
     * 
     * @param employerName
     * @return 
     */
    
    public String islog2(String username, String pwd){           
        String ok="Login Incorrect";;
        try {
            
            DatabaseAccess.connecterDB();
            St=DatabaseAccess.cnx.createStatement();
            Rs = St.executeQuery("SELECT u.`username`, u.`motsPasse`, a.`titre` FROM `utilisateur` u, agent a WHERE u.`username` = '"+username+"' and u.`motsPasse`='"+pwd+"' and u.`username` = a.`username`;");
            while (Rs.next()) {
                // imprime les éléments du tuple
                String nom = Rs.getString("username");
                String passworde = Rs.getString("motsPasse");
                employerType = Rs.getString("titre");
                if((username.equals(Rs.getString("username")))&&pwd.equals(Rs.getString("motsPasse"))){
                    ok = "";
                   
                }
            }
            return ok;
        } catch (SQLException ex) {
            Logger.getLogger(ProxyInternetAcces.class.getName()).log(Level.SEVERE, null, ex);
            return username;
        }
        
    }  
}
