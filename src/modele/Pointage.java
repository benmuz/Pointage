/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import static modele.DatabaseAccess.Rs;
import static modele.DatabaseAccess.St;

/**
 *
 * @author JOKISS
 */
public class Pointage {
    int id;
    Agent agent;
    Date dateJ;
    Time arrive;
    Time sortie;
    String ag;
    int idAg;

    public Pointage() {
        agent = new Agent();
    }

    
    public Pointage(int id, Agent agent, Date dateJ, Time arrive, Time sortie) {
        this.id = id;
        this.agent = agent;
        this.dateJ = dateJ;
        this.arrive = arrive;
        this.sortie = sortie;
    }

    public ObservableList<Pointage> afficher(){
        ObservableList<Pointage> P = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT p.id_pointage, p.`idAgent`, concat(a.nom,' ',a.postnom,' ',a.prenom), p.`dateP`, p.arrive, p.sortie \n" +
                                "FROM pointage p, agent a\n" +
                                "WHERE a.`idAgent` = p.`idAgent` \n" +
                                "and p.`dateP` = CURDATE()\n" +
                                ";");
            while(Rs.next()){
                Pointage l = new Pointage();
                l.setId(Rs.getInt(1));
                l.setIdAg(Rs.getInt(2));
                l.setAg(Rs.getString(3));
                
                l.setDateJ(Rs.getDate(4));
                l.setArrive(Rs.getTime(5));
                l.setSortie(Rs.getTime(6));
                P.add(l);
               
            }
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return P;
    }
    
    public Pointage getLastPointageByAgent(Agent a){
        Pointage l = new Pointage();
        try{        
            Rs = St.executeQuery("SELECT * FROM `pointage` WHERE `idAgent` = '"+a.getIdAgent()+"' order by `idAgent` limit 1 ;");
            while(Rs.next()){
                
                l.setId(Rs.getInt(1));
                l.setIdAg(Rs.getInt(2));
               
                l.setDateJ(Rs.getDate(3));
                l.setArrive(Rs.getTime(4));
                l.setSortie(Rs.getTime(5));
                
               
            }
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return l;
    }
    public ObservableList<Pointage> getPresenceByAgentCurMonth(int idAgent){
        ObservableList<Pointage> P = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT p.id_pointage, p.`idAgent` contat(a.nom,' ',a.postnom,' ',a.prenom), p.`dateP`, p.arrive, p.sortie \n" +
                                "FROM pointage p, agent a\n" +
                                "WHERE a.`idAgent` = p.`idAgent` \n" +
                                "and p.`dateP` = CURDATE()\n" +
                                ";");
            while(Rs.next()){
                Pointage l = new Pointage();
                l.setId(Rs.getInt(1));
                
                l.setAg(Rs.getString(3));
               
                l.setDateJ(Rs.getDate(4));
                l.setArrive(Rs.getTime(5));
                l.setSortie(Rs.getTime(6));
                P.add(l);
               
            }
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return P;
    }
    public ObservableList<Pointage> getPresenceByAgentChoiceMonth(int idAgent){
        ObservableList<Pointage> P = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT p.id_pointage, p.`idAgent` contat(a.nom,' ',a.postnom,' ',a.prenom), p.`dateP`, p.arrive, p.sortie \n" +
                                "FROM pointage p, agent a\n" +
                                "WHERE a.`idAgent` = p.`idAgent` \n" +
                                "and p.`dateP` = CURDATE()\n" +
                                ";");
            while(Rs.next()){
                Pointage l = new Pointage();
                l.setId(Rs.getInt(1));
                
                l.setAg(Rs.getString(3));
               
                l.setDateJ(Rs.getDate(4));
                l.setArrive(Rs.getTime(5));
                l.setSortie(Rs.getTime(6));
                P.add(l);
               
            }
            
                
        }catch (SQLException e){
            e.printStackTrace();
        }
        return P;
    }
    
    public void condenserMensuel(Agent agent){
        
    }
    public void pointageEntrer( Agent agent) throws SQLException{
        
        St.executeUpdate("INSERT INTO `pointage`( `idAgent`, `dateP`, `arrive`) VALUES ('"+agent.getIdAgent()+"',CURDATE(),CURTIME())");

    }
    public void pointageSorti(Agent agent) throws SQLException{
        if(JOptionPane.showConfirmDialog(null, "Confirmer la modification", "Modifier", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            St.executeUpdate("UPDATE `pointage` SET `sortie`=CURTIME() WHERE `idAgent` = '"+agent.getIdAgent()+"' and `id_pointage` = '"+getLastPointageByAgent(agent).getId()+"' ;");      
        }           

    }
    public void supprimer(int id){
        if(JOptionPane.showConfirmDialog(null, "Confirmer la suppression", "Suppression", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            try {      
                St.executeUpdate("DELETE FROM `pointage` WHERE id_pointage ='"+id+"';");
            } catch (SQLException ex) {
                Logger.getLogger(Pointage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Date getDateJ() {
        return dateJ;
    }

    public void setDateJ(Date dateJ) {
        this.dateJ = dateJ;
    }

    public Time getArrive() {
        return arrive;
    }

    public void setArrive(Time arrive) {
        this.arrive = arrive;
    }

    public Time getSortie() {
        return sortie;
    }

    public void setSortie(Time sortie) {
        this.sortie = sortie;
    }

    public String getAg() {
        return ag;
    }

    public void setAg(String ag) {
        this.ag = ag;
    }

    public int getIdAg() {
        return idAg;
    }

    public void setIdAg(int idAg) {
        this.idAg = idAg;
    }
    
    
    
}
