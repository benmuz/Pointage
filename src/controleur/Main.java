/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.Agent;
import static modele.DatabaseAccess.connecterDB;
import modele.Utilisateur;

/**
 *
 * @author JOKISS
 */
public class Main extends Application {
    
    Parent root;
    public static Stage un, stage;
    private Utilisateur utilisateur;
    @Override
    public void start(Stage stage) throws Exception {
        utilisateur = new Utilisateur();
        root = FXMLLoader.load(getClass().getResource("/vue/Login.fxml"));
        String dirName = "C:\\ProgramData\\pointage\\";
        File dir = new File(dirName);
        if(dir.exists()){
            System.out.println("Le dosier existe : "+dir.getAbsolutePath());
            File destinationFile= new File(dirName+"config.txt");
            if(!destinationFile.exists())
                if(destinationFile.createNewFile()){
                    System.out.println("Creation du fichier config reussi : "+dir.getAbsolutePath());

                }else
                    System.out.println("Echec sur le fichier config : "+dir.getAbsolutePath());

        }else{
            if(dir.mkdir()){
                System.out.println("Creation du dossier reussi : "+dir.getAbsolutePath());
                File destinationFile= new File(dirName+"config.txt");
                if(!destinationFile.exists())
                    if(destinationFile.createNewFile()){
                        System.out.println("Creation du fichier config reussi : "+dir.getAbsolutePath());
                
                    }else
                        System.out.println("Echec sur le fichier config : "+dir.getAbsolutePath());
                
                
        
            }else
               System.out.println("Echec sur le dossier dossier  : "+dir.getAbsolutePath());
    
        }
   connecterDB();
        
        un = new Stage();
        Scene scene = new Scene(root);
        
        un.initStyle(StageStyle.TRANSPARENT);
        un.setScene(scene);
        if(utilisateur.Afficher().isEmpty()){
            Parent prefet = FXMLLoader.load(getClass().getResource("prof.fxml"));
            Scene scenePref = new Scene(prefet);
            stage = new Stage();
            stage.setScene(scenePref);
            stage.show();
        }else
            un.show();
        
    }
   public void close(){
//        try {
            //Thread.sleep(2000);
            un.close();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

public void lance(String username, String passeWord){
        
        try {
            close();
            Parent roote = FXMLLoader.load(getClass().getResource("/vue/prof.fxml"));
            stage = new Stage();
            Scene scene = new Scene(roote);
            stage.setScene(scene);
        
           
            stage.show();
            
           
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void closeStage(){
        stage.close();
        un.show();
        System.out.println("Deconnection active");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
