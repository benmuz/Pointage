/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import modele.Agent;


/**
 * FXML Controller class
 *
 * @author Ben MUZINGU
 */
public class ProfController implements Initializable {

    @FXML
    private StackPane contenteurPrefet;

    @FXML
    private JFXDrawer drawer2;

    @FXML
    private VBox box2;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private VBox box;

    
    @FXML
    private Circle cir2;

    @FXML
    private JFXButton menuCalender;

    @FXML
    private ImageView menuCalendrier;

    @FXML
    private JFXButton menuHoraire;

    @FXML
    private JFXButton menuPoint;

    @FXML
    private JFXButton menuEleve;

    @FXML
    private JFXButton menuPromotion;

    @FXML
    private JFXButton menuAgent;

    @FXML
    private JFXButton menuEpreuve;

    @FXML
    private JFXHamburger humberger;

     @FXML
    private JFXComboBox combo_utilitaire;
    boolean show;
    InputStream stream;
    private Image image;
    FXMLLoader loadHoraire, loadCours, loadClasse, loadParam, loadEpreuve,loadProfile;
    
    Agent agent;
    AnchorPane pane,panPoint, panPromo, panParametre, panEpreuve,panAgent,panProfile;
    String style = "-fx-background-color: #cfd8dc; -fx-text-fill:black;";
    String styleInit = "-fx-background-color:  #90a4ae; -fx-text-fill:black;";
    
    @FXML
    private void menuAgent(){
        resetStyle();
        contenteurPrefet.getChildren().clear();
        contenteurPrefet.getChildren().add(panAgent);
        menuAgent.setStyle(style);
    }

    @FXML
    void menuCalender() {
        
    }

    @FXML
    private void menuPoint(){
        resetStyle();
        contenteurPrefet.getChildren().clear();
        contenteurPrefet.getChildren().add(panPoint);
        menuPoint.setStyle(style);
        
    }

    @FXML
    void menuEpreuve() {
        resetStyle();
        contenteurPrefet.getChildren().clear();
        
    }

    @FXML
    void menuHoraire() {
        resetStyle();
        contenteurPrefet.getChildren().clear();
        contenteurPrefet.getChildren().add(pane);
        menuHoraire.setStyle(style);
    }

    @FXML
    void menuPromotion() {
        resetStyle();
        contenteurPrefet.getChildren().clear();
        contenteurPrefet.getChildren().add(panPromo);
        menuPromotion.setStyle(style);
    }
    @FXML
    void menuParametre() {
        resetStyle();
        contenteurPrefet.getChildren().clear();
        contenteurPrefet.getChildren().add(panParametre);
    }
    @FXML
    void menuProfile() {
        resetStyle();
        contenteurPrefet.getChildren().clear();
        contenteurPrefet.getChildren().add(panProfile);
    }
    
    public void resetStyle(){
       
        menuAgent.setStyle(styleInit);
        menuPoint.setStyle(styleInit);
        
    }
    
     @FXML
    private void more(){
        if(show){
            show = false;
            drawer2.close();
            contenteurPrefet.getChildren().remove(drawer2);
        }else{
            contenteurPrefet.getChildren().add(drawer2);
            drawer2.open();
            show =true;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            panPoint = FXMLLoader.load(getClass().getResource("/vue/Pointage.fxml"));
            panAgent = FXMLLoader.load(getClass().getResource("/vue/AgentPrefet.fxml"));
            contenteurPrefet.getChildren().clear();
            contenteurPrefet.getChildren().add(panPoint);
            menuPoint.setStyle(style);
        } catch (IOException ex) {
            Logger.getLogger(ProfController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadProf(Agent cl){
        combo_utilitaire.setPromptText(cl.getNom());
        agent = cl;
        
        panProfile = loadProfile.getRoot();
        contenteurPrefet.getChildren().add(pane);
        stream = cl.getAgentById(cl.getIdAgent()).getPhoto();
        cl.setPhoto(stream);
        try {
            image = SwingFXUtils.toFXImage(ImageIO.read(stream), null);
            cir2.setFill(new ImagePattern(image));
            } catch (IOException ex) {
                System.err.println("echec dans la lecture de donner binaire");
            }catch (NullPointerException ex) {
                System.err.println("pas d'image disponible\n"+ex.getMessage());


            }catch (IllegalArgumentException ex) {
                System.err.println("echec dans la recuperation de l'image\n"+ex.getMessage());
            }

        }
}
