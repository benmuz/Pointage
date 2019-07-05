/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modele.OfficeInternetAcces;
import modele.ProxyInternetAcces;


/**
 *
 * @author Ben MUZINGU
 */
public class LoginController extends Stage implements Initializable {
    
    @FXML
    private Label lbl_ErrorMsg;
    @FXML
    private JFXTextField txt_UserName;
    @FXML
    private JFXPasswordField txtp_Password;
    @FXML
    private AnchorPane cardPane;
    @FXML
    private JFXProgressBar loadProgress;

    
    @FXML
    private void log(){
        String uName=txt_UserName.getText();
        String pass=txtp_Password.getText();
        System.out.println(uName+"\n"+pass);

        OfficeInternetAcces acces = new ProxyInternetAcces(uName, pass);
        if(acces.grandInternetAcces().equals("Login Incorrect")){
           
           lbl_ErrorMsg.setText(acces.grandInternetAcces());
           loadProgress.setVisible(true);
           
        }else{
           loadProgress.setVisible(true);
        }  
       
    }
    @FXML
    @Override
    public void close(){
        System.exit(0);
    }
    @FXML
    public void entrer(KeyEvent evt){
        if(evt.getCode().equals(KeyCode.ENTER)) 
            log();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(cardPane,2);
        txtp_Password.setText(null);
    } 
    public boolean login(String username, String pwd){
        boolean test=false;
        String parametres=lectureFichier("src/config.txt");
        //séparation de username et pwd
        String nom_user="";
        String pwd_user="";
        
        nom_user=parametres.substring(0,parametres.indexOf(";") );
        pwd_user=parametres.substring(parametres.indexOf(";")+1);
        
        System.out.println(" Lecture "+parametres);
        System.out.println(" Username = "+nom_user);
        System.out.println(" password = "+pwd_user);
        
        if(username.equals(nom_user)&&pwd.equals(pwd_user)){

            
            test=true;
        }else{
            test =false;
            
        }
            
        
        return test;
    }
    public String lectureFichier(String filePath) {
        BufferedReader lecture=null;
        String valeurs="";
        File fichier= new File(filePath);
        try {
            
            lecture = new BufferedReader(
                    new FileReader(fichier));
            
//            while(true){
//             if((valeurs=lecture.readLine()).equals(null))
//                 break;
//            }
            valeurs=lecture.readLine();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                lecture.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
       return valeurs;
    }
  
}
