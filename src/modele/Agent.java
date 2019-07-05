/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import static modele.DatabaseAccess.Rs;
import static modele.DatabaseAccess.St;
import java.io.InputStream;
import java.sql.PreparedStatement;
import static modele.DatabaseAccess.cnx;

/**
 *
 * @author Ben MUZINGU
 */
public class Agent {
    private int idAgent;
    private String matricule;
    private String nom;
    private String postnom;
    private String prenom;
    private String genre;
    private String titre;
    private String tel;
    private String mail;
    private String adresse;
    private String dateNaissance;
    private String lieuNaissance;
    private String nationalite;
   private InputStream photo;
    private Utilisateur utilisateur;

    public Agent(String matricule, String nom, String postnom, String prenom, String genre,String titre, String tel, String mail, String adresse, String dateNaissance, String lieuNaissance, String nationalite) {
        this.matricule = matricule;
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
        this.genre = genre;
        this.titre = titre;
        this.tel = tel;
        this.mail = mail;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.nationalite = nationalite;
        ajouter(matricule, nom, postnom, prenom, genre, titre, tel, mail, adresse, dateNaissance, lieuNaissance, nationalite, genre);
    }

    public Agent(String nom, String postnom, String prenom) {
        this.nom = nom;
        this.postnom = postnom;
        this.prenom = prenom;
    }

    public Agent(int idAgent) {
        this.idAgent = idAgent;
        utilisateur = new Utilisateur();
        
    }
    

    
    public Agent() {
        utilisateur = new Utilisateur();
    }
    public ObservableList<Agent> search(String mot){
        ObservableList<Agent> agent = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT * FROM `agent` WHERE `prenom` like '%"+mot+"%' or nom like '%"+mot+"%' or adresse like '%"+mot+"%' or mail like '%"+mot+"%' or genre like '%"+mot+"%' or matricule like '%"+mot+"%' or titre like '%"+mot+"%';");
            while(Rs.next()){
                Agent cl = new Agent(); 
                cl.setMatricule(Rs.getString(1));
                cl.setNom(Rs.getString(2));
                cl.setPostnom(Rs.getString(3));
                cl.setPrenom(Rs.getString(4));
                cl.setGenre(Rs.getString(5));
                cl.setTitre(Rs.getString(6));
                cl.setTel(Rs.getString(7));
                cl.setMail(Rs.getString(8));
                cl.setAdresse(Rs.getString(9));
                cl.setDateNaissance(Rs.getString(10));
                cl.setLieuNaissance(Rs.getString(11));
                cl.setNationalite(Rs.getString(12));
                
                agent.add(cl);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return agent;
    }
    public Agent getAgentByUser(String username, String passeword){
        Agent cl = new Agent();
        try{        
            Rs = St.executeQuery("SELECT a.`idAgent`, a.`matricule`, a.`nom`, a.`postnom`, a.`prenom`, a.`genre`, a.`titre`, a.`tel`, a.`mail`, a.`adresse`, a.`dateNaissance`, a.`lieuNaisssance`, a.`nationalite`, a.`username`, a.`photo` "
                    + "FROM `agent` a, utilisateur u "
                    + "WHERE a.username = u.username and u.username = '"+username+"' and u.motsPasse = '"+passeword+"';");
            while(Rs.next()){
                cl.setIdAgent(Rs.getInt(1));
                cl.setMatricule(Rs.getString(2));
                cl.setNom(Rs.getString(3));
                cl.setPostnom(Rs.getString(4));
                cl.setPrenom(Rs.getString(5));
                cl.setGenre(Rs.getString(6));
                cl.setTitre(Rs.getString(7));
                cl.setTel(Rs.getString(8));
                cl.setMail(Rs.getString(9));
                cl.setAdresse(Rs.getString(10));
                cl.setDateNaissance(Rs.getString(11));
                cl.setLieuNaissance(Rs.getString(12));
                cl.setNationalite(Rs.getString(13));
                utilisateur.setUsername(Rs.getString(14));
                utilisateur.setPassword(passeword);
                cl.setPhoto(Rs.getBinaryStream(15));
                cl.setUtilisateur(utilisateur);
                
                
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cl;
    }
    public Agent getAgentById(int id){
        Agent cl = new Agent();
        try{        
            Rs = St.executeQuery("SELECT * FROM `agent` where idAgent = '"+id+"';");
            while(Rs.next()){
                cl.setIdAgent(Rs.getInt(1));
                cl.setMatricule(Rs.getString(2));
                cl.setNom(Rs.getString(3));
                cl.setPostnom(Rs.getString(4));
                cl.setPrenom(Rs.getString(5));
                cl.setGenre(Rs.getString(6));
                cl.setTitre(Rs.getString(7));
                cl.setTel(Rs.getString(8));
                cl.setMail(Rs.getString(9));
                cl.setAdresse(Rs.getString(10));
                cl.setDateNaissance(Rs.getString(11));
                cl.setLieuNaissance(Rs.getString(12));
                cl.setNationalite(Rs.getString(13));
                cl.setPhoto(Rs.getBinaryStream("photo"));
                
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cl;
    }
    public Agent getAgentByMatricule(String matricule){
        Agent cl = new Agent();
        
        try{        
            Rs = St.executeQuery("SELECT * FROM `agent` where matricule = '"+matricule+"';");
            while(Rs.next()){
                cl.setIdAgent(Rs.getInt(1));
                cl.setMatricule(Rs.getString(2));
                
                cl.setNom(Rs.getString(3));
                cl.setPostnom(Rs.getString(4));
                cl.setPrenom(Rs.getString(5));
                cl.setGenre(Rs.getString(6));
                cl.setTitre(Rs.getString(7));
                cl.setTel(Rs.getString(8));
                cl.setMail(Rs.getString(9));
                cl.setAdresse(Rs.getString(10));
                cl.setDateNaissance(Rs.getString(11));
                cl.setLieuNaissance(Rs.getString(12));
                cl.setNationalite(Rs.getString(13));
                cl.setUtilisateur(new Utilisateur(Rs.getString("username"), "ben"));
                cl.setPhoto(Rs.getBinaryStream("photo"));
                
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cl;
    }
    public ObservableList<Agent> Afficher(){
        ObservableList<Agent> agent = FXCollections.observableArrayList();
        try{        
            Rs = St.executeQuery("SELECT * FROM `agent`;");
            while(Rs.next()){
                Agent cl = new Agent(); 
                cl.setMatricule(Rs.getString(2));
                cl.setNom(Rs.getString(3));
                cl.setPostnom(Rs.getString(4));
                cl.setPrenom(Rs.getString(5));
                cl.setGenre(Rs.getString(6));
                cl.setTitre(Rs.getString(7));
                cl.setTel(Rs.getString(8));
                cl.setMail(Rs.getString(9));
                cl.setAdresse(Rs.getString(10));
                cl.setDateNaissance(Rs.getString(11));
                cl.setLieuNaissance(Rs.getString(12));
                cl.setNationalite(Rs.getString(13));
                cl.setPhoto(Rs.getBinaryStream("photo"));
                agent.add(cl);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return agent;
    }
   
    public LinkedList getAllTitre(){
        LinkedList tit = new LinkedList();
        try{        
            Rs = St.executeQuery("SELECT DISTINCT `titre` FROM agent;");
            while(Rs.next()){
                tit.add(Rs.getString("titre"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tit;
    }
    public void ajouter(String matricule, String nom, String postnom, String prenom, String genre, String titre, String tel, String mail, String adresse, String dateNaissance, String lieuNaissance, String nationalite, String username){
        try {
            St.executeUpdate("INSERT INTO `school_bd`.`agent` ( `matricule`, `nom`, `postnom`, `prenom`, `genre`, `titre`, `tel`, `mail`, `adresse`, `dateNaissance`, `lieuNaisssance`, `nationalite`, `username`) VALUES ('"+matricule+"', '"+nom+"', '"+postnom+"', '"+prenom+"', '"+genre+"', '"+titre+"', '"+tel+"', '"+mail+"', '"+adresse+"', '"+dateNaissance+"', '"+lieuNaissance+"', '"+nationalite+"', '"+username+"');");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "erruer dans l'insertion de l'agent\n"+ex.getMessage());
            ex.printStackTrace();
            
        }
    }
    public void modifier(String matricule, String nom, String postnom, String prenom, String genre, String titre, String tel, String mail, String adresse, String dateNaissance, String lieuNaissance, String nationalite) throws SQLException{
         if(JOptionPane.showConfirmDialog(null, "Confirmer la modification", "Modification", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
                St.executeUpdate("UPDATE `agent` SET `nom`='"+nom+"',`postnom`='"+postnom+"',`prenom`='"+prenom+"',`genre`='"+genre+"',`titre`='"+titre+"',"
                        + "`tel`='"+tel+"',`mail`='"+mail+"', adresse = '"+adresse+"', `dateNaissance`='"+dateNaissance+"',`lieuNaisssance`='"+lieuNaissance+"',"
                        + "`nationalite`='"+nationalite+"' WHERE `matricule` = '"+matricule+"';");     
            }           
        
    }
    public void modifierImage(String matricule,FileInputStream photo, File file){
        try{
            try(PreparedStatement ps = cnx.prepareStatement("UPDATE `agent` SET `photo`= ? WHERE matricule = '"+matricule+"';")) {
                ps.setBinaryStream(1, photo, (int)file.length());
                ps.executeUpdate();
            }catch(NullPointerException e){
                
            }
          
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Taille de l'image trop grande \nVeuillez reduire l'image jusqu'a 64ko\n"+e.getMessage());
        }
    }
    public void supprimer(String matricule) throws SQLException, MySQLIntegrityConstraintViolationException{
        
        if(JOptionPane.showConfirmDialog(null, "Confirmer la suppression", "Suppression", JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
            St.executeUpdate("DELETE FROM `agent` WHERE matricule ='"+matricule+"';");      
        }           
    }

    public int getIdAgent() {
        return idAgent;
    }

    public void setIdAgent(int idAgent) {
        this.idAgent = idAgent;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return postnom;
    }

    public void setPostnom(String postnom) {
        this.postnom = postnom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }
    
    
}

