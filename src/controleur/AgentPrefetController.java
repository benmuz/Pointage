/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.awt.Image;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import modele.Agent;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Ben MUZINGU
 */
public class AgentPrefetController implements Initializable {

    
    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane contenu1,contenu2;
    @FXML
    private TableColumn colMat;

    @FXML
    private TableColumn colNom;

    @FXML
    private TableColumn colPost;

    @FXML
    private TableColumn colPrenom;

    @FXML
    private TableColumn colGenre;

    @FXML
    private TableColumn colTitre;

    @FXML
    private TableColumn colTel;

    @FXML
    private TableColumn colMail;

    @FXML
    private TableColumn colAdresse;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colLieu;

     @FXML
    private JFXButton add;
     
    @FXML
    private TableColumn colNatio;

    @FXML
    private TableView<Agent> tab_agent;
    @FXML
    private JFXTextField txt_search;
    
    public static Stage stage;
    public static boolean blur;
    public Agent agent;
    @Override
    public void initialize(URL url, ResourceBundle b) {
       blur = false;
       stage = new Stage();
       colMat.setCellValueFactory(new PropertyValueFactory<Agent,String>("matricule"));
       colNom.setCellValueFactory(new PropertyValueFactory<Agent,String>("nom"));
       colPost.setCellValueFactory(new PropertyValueFactory<Agent,String>("postnom"));
       colPrenom.setCellValueFactory(new PropertyValueFactory<Agent,String>("prenom"));
       colGenre.setCellValueFactory(new PropertyValueFactory<Agent,String>("genre"));
       colTel.setCellValueFactory(new PropertyValueFactory<Agent,String>("tel"));
       colMail.setCellValueFactory(new PropertyValueFactory<Agent,String>("mail"));
       colAdresse.setCellValueFactory(new PropertyValueFactory<Agent,String>("adresse"));
       colDate.setCellValueFactory(new PropertyValueFactory<Agent,String>("dateNaissance"));
       colLieu.setCellValueFactory(new PropertyValueFactory<Agent,String>("lieuNaissance"));
       colNatio.setCellValueFactory(new PropertyValueFactory<Agent,String>("nationalite"));
       colTitre.setCellValueFactory(new PropertyValueFactory<Agent,String>("titre"));
       tab_agent.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       add.setTooltip(new Tooltip("Nouvel agent"));
       agent = new Agent();
       ObservableList value = new Agent().Afficher();
//      
       tab_agent.setItems(new Agent().Afficher());
        
    }
    @FXML
    public void actualiser(){
        
        
        if(txt_search.getText().isEmpty())
            tab_agent.setItems(agent.Afficher());
        
    }
    @FXML
    public void supprimer(){
        ObservableList<Agent> setected;
        setected = tab_agent.getSelectionModel().getSelectedItems();
        Agent ag = setected.get(0);
        try {
            agent.supprimer(ag.getMatricule());
            tab_agent.setItems(agent.Afficher());
        }catch (MySQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(null, "Vous ne pouvais supprimer un enseignant qui est titulaire ou un enseignant qui dispense des cours\nPour remediez a ce problème veuillez lui retirer les cours et le rendre professeur simple\n"+e.getMessage()); 
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur sur la supression\n"+ex.getMessage()); 
            ex.printStackTrace();
        }
    }
    
    @FXML
    public void recherche(){
        tab_agent.setItems(new Agent().search(txt_search.getText()));
        
    }
    @FXML
    public void clicIn(){
        ObservableList<Agent> setected;
        setected = tab_agent.getSelectionModel().getSelectedItems();
        Agent ag = setected.get(0);
        
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/vue/AddAgent.fxml"));
            load.load();
            AddAgentController modifier = load.getController();
            modifier.displayUpdate(ag.getMatricule(), ag.getNom(), ag.getPostnom(), ag.getPrenom(), ag.getGenre(), ag.getTitre(), ag.getTel(), ag.getMail(), ag.getAdresse(), ag.getDateNaissance(), ag.getLieuNaissance(), ag.getNationalite());
            stage = new Stage();
            Parent p = load.getRoot();
            stage.setScene(new Scene(p));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    e.consume();
                }
            });
            stage.setResizable(false);
            
            
            stage.show();
            
            stage.setResizable(false);
        } catch (IOException ex) {
            Logger.getLogger(AgentPrefetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    public void username() 
    {
        ObservableList<Agent> setected;
        setected = tab_agent.getSelectionModel().getSelectedItems();
        Agent ag = setected.get(0);
        JOptionPane.showMessageDialog(null, "Nom d'utilisateur : "+ag.getAgentByMatricule(ag.getMatricule()).getUtilisateur().getUsername());
        
    }
    @FXML
    public void imprimer() 
    {
        try 
          {
            ObservableList<Agent> setected;
            setected = tab_agent.getSelectionModel().getSelectedItems();
            Agent ag = setected.get(0);
            Map<String, Object> parameters = new HashMap<>();
            
            Image photo = null;
            try {
                
                photo = ImageIO.read(agent.getAgentByMatricule(ag.getMatricule()).getPhoto());
            } catch (IOException ex) {
                Logger.getLogger(AgentPrefetController.class.getName()).log(Level.SEVERE, null, ex);
            }catch(IllegalArgumentException e){
                
            }
            
            parameters.put("photo", photo);
            parameters.put("matricule",ag.getMatricule());
            parameters.put("nom", ag.getNom());
            parameters.put("postnom", ag.getPostnom());
            parameters.put("prenom", ag.getPrenom());
            parameters.put("genre", ag.getGenre());
            parameters.put("telephone", ag.getTel());
            parameters.put("mail", ag.getMail());
            parameters.put("adresse", ag.getAdresse());
            parameters.put("date_naissance", ag.getDateNaissance());
            parameters.put("lieu_naissance", ag.getLieuNaissance());
            parameters.put("nationalite", ag.getNationalite());
            java.io.InputStream file = ClassLoader.getSystemResourceAsStream("imprimer/agent.jrxml");
            JasperDesign jasperDesign;
            jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint;
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
            JasperViewer.viewReport(jasperPrint, false);
           } 
        catch (JRException ex) 
        {
            ex.printStackTrace();
        }
    }
    @FXML
    public void add(){
        
        
        try {
            
            Scene sceneProf = new Scene(FXMLLoader.load(getClass().getResource("/vue/AddAgent.fxml")));
            
            stage = new Stage();
            stage.setScene(sceneProf); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    e.consume();
                }
            });
            stage.setResizable(false);
            stage.show();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void close(){
        
        stage.close();
    }
    Timeline timeline;
    //Timer timer = FXTimer.runLater(
    
    
    
}
