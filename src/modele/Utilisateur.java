/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static modele.DatabaseAccess.Rs;
import static modele.DatabaseAccess.St;

/**
 *
 * @author Ben MUZINGU
 */
public class Utilisateur {
    private String username;
    private String password;

    public Utilisateur() {
    }
    
    public Utilisateur(String username, String password) {
        this.username = username;
        this.password = password;
        
    }
    public ArrayList<Utilisateur> search(String mot){
        ArrayList<Utilisateur> user = new ArrayList<>();
        try{        
            Rs = St.executeQuery("SELECT * FROM `utilisateur` WHERE `username` like '%"+mot+"%';");
            while(Rs.next()){
                Utilisateur cl = new Utilisateur(); 
                cl.setUsername(Rs.getString(1));
                cl.setPassword(Rs.getString(2));
                user.add(cl);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        
        return user;
    }
    public boolean isExist(String username){
        boolean ok = false;
        try{        
            Rs = St.executeQuery("SELECT * FROM `utilisateur` WHERE `username` like '%"+username+"%';");
            while(Rs.next()){
                if((username.equals(Rs.getString("username"))))
                    ok = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ok;
    }
    public ArrayList<Utilisateur> Afficher(){
        ArrayList<Utilisateur> user = new ArrayList<>();
        try{        
            Rs = St.executeQuery("SELECT * FROM `utilisateur`;");
            while(Rs.next()){
                Utilisateur cl = new Utilisateur(); 
                cl.setUsername(Rs.getString(1));
                cl.setPassword(Rs.getString(2));
                user.add(cl);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public boolean ajouter(String username, String password){
        try {
            if(!isExist(username)){
                St.executeUpdate("INSERT INTO `utilisateur`(`username`, `motsPasse`) VALUES ('"+username+"','"+password+"')");
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "L'utilisateur que vous essayer d'introdiure existe deja !\nVeuillez changer de nom d'utilisateur SVP !");
                return false;
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erruer dans l'insertion de l'agent\n"+ex.getMessage());
            return false;
        }
    }
    public void modifier(String username, String password){
        try{
            if(JOptionPane.showConfirmDialog(null, "Confirmer la modification", "Modification", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
                
                St.executeUpdate("UPDATE `utilisateur` SET `motsPasse`='"+password+"' WHERE `username`='"+username+"';");     
            }           
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erreur sur la modification\n"+e.getMessage()); 
        }
    }

    public void supprimer(String username){
        try{
            if(JOptionPane.showConfirmDialog(null, "Confirmer la suppression", "Suppression", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
                St.executeUpdate("DELETE FROM `utilisateur` WHERE `username`='"+username+"';");      
            }           
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Erreur sur la supression\n"+e.getMessage()); 
            e.printStackTrace();
        }
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
