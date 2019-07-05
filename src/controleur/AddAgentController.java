/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import javafx.scene.shape.Circle;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;
import modele.Agent;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

import modele.Utilisateur;
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
public class AddAgentController extends Stage implements Initializable {

    /**
     * Initializes the controller class.
     */
    private FileChooser fileChooser;
    @FXML
    private Circle cir2;
    
    @FXML
    private JFXTextField txt_nom;

    @FXML
    private JFXTextField txt_postnom;

    @FXML
    private JFXTextField txt_prenom;

    @FXML
    private ImageView portrait;

    @FXML
    private JFXRadioButton homme;

    @FXML
    private JFXRadioButton femme;

    @FXML
    private JFXComboBox txt_titre;

    @FXML
    private JFXTextField txt_phone;

    @FXML
    private JFXTextField txt_mail, matricule;

    @FXML
    private JFXTextField txt_adresse;

    @FXML
    private JFXTextField txt_lieu;

    @FXML
    private JFXTextField txt_natio;

    @FXML
    private JFXButton btn_cancel;

    @FXML
    private JFXButton btn_save;

    @FXML
    private DatePicker txt_date;
    
    private Agent agent;
    private File fichier;
    InputStream stream;
    private Image image;
    private Utilisateur utilisateur;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agent = new Agent();
        cir2.setStroke(Color.SEAGREEN);
        utilisateur = new Utilisateur();
        final ToggleGroup group = new ToggleGroup();
        homme.setToggleGroup(group);
        femme.setToggleGroup(group);
        
      
        txt_titre.getItems().addAll(new Agent().getAllTitre());
        
        if (!txt_titre.getItems().contains("prefet")) {
            txt_titre.getItems().add("prefet");
        }
        txt_titre.getItems().add("Nouveau");
        txt_titre.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ("Nouveau".equals(newValue)) {
                    try{
                        
                        txt_titre.setEditable(true);
                        
                    }catch(IndexOutOfBoundsException e){
                        
                    }
                    
                    //txt_titre.cellFactoryProperty();
                }
            }
        });
        
    
       fileChooser = new FileChooser();
    }

    public void displayUpdate(String matricule, String nom, String postnom, String prenom, String genre, String titre, String tel, String mail, String adresse, String dateNaissance, String lieuNaissance, String nationalite){
        
        this.matricule.setText(matricule);
        txt_nom.setText(nom);
        txt_postnom.setText(postnom); 
        txt_prenom.setText(prenom);
        if(homme.getText().equals("Homme"))
            homme.setSelected(true);
        else femme.setSelected(true);
        txt_titre.setValue(titre);
        txt_phone.setText(tel);
        txt_mail.setText(mail);
        txt_adresse.setText(adresse);
        btn_save.setOnAction(e -> modifier());
        btn_cancel.setOnAction(event -> AgentPrefetController.close());
        txt_date.setValue(LocalDate.of(Integer.parseInt(dateNaissance.substring(0,4)), Integer.parseInt(dateNaissance.substring(5,7)), Integer.parseInt(dateNaissance.substring(8,10))));
        txt_lieu.setText(lieuNaissance);
        txt_natio.setText(nationalite);
        stream = agent.getAgentByMatricule(matricule).getPhoto();
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
    public void ajouter(){
        if(isValide()){
            utilisateur.ajouter(txt_nom.getText()+Calendar.YEAR+agent.Afficher().size(), txt_nom.getText()+Calendar.YEAR+agent.Afficher().size());
            new Agent().ajouter(matricule.getText(), txt_nom.getText(), txt_postnom.getText(), txt_prenom.getText(), getGenre(), txt_titre.getValue().toString(), txt_phone.getText(), txt_mail.getText(), txt_adresse.getText(), txt_date.getValue().toString(), txt_lieu.getText(), txt_natio.getText(), txt_nom.getText()+Calendar.YEAR+agent.Afficher().size());
            agent.modifierImage(matricule.getText(),(FileInputStream) stream, fichier);
            AgentPrefetController.close();
        }
    }
    String getGenre(){
        if(homme.isSelected())
            return "Homme";
        else return "Femme";
    }
    @FXML
    public void annuler(){
        AgentPrefetController.close();
    }
    public void modifier(){
        
        if(isValide()){
            try {
                new Agent().modifier(matricule.getText(), 
                        txt_nom.getText(), txt_postnom.getText(), 
                        txt_prenom.getText(), getGenre(), 
                        txt_titre.getValue().toString(), 
                        txt_phone.getText(), 
                        txt_mail.getText(), 
                        txt_adresse.getText(), 
                        txt_date.getValue().toString(), 
                        txt_lieu.getText(), 
                        txt_natio.getText());
                agent.modifierImage(matricule.getText(),(FileInputStream) stream, fichier);
            
                AgentPrefetController.close();
                
                
            } catch (SQLException ex) {
                
                JOptionPane.showMessageDialog(null, "Erreur lors de la modification");
                
            }
        }
            
    }
    @FXML
    public void enregistrer(){
        ajouter();
    }
    public boolean isValide(){
        boolean test = true;
        
        if(txt_nom.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez Saisir le nom SVP !");
            test = false;
        }
        else if(matricule.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez Saisir le matricule SVP!");
            test = false;
        }
        else if(txt_prenom.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Veuillez Saisir le prenom SVP!");
            test = false;
        }
        else if(txt_titre.getValue() == null){
            JOptionPane.showMessageDialog(null, "Veuillez choisir le titre de l'agent !");
            test = false;
        }
        else{
            //JOptionPane.showMessageDialog(null, "");
            test = true;
        }
        return test;
    }
    
    @FXML
    public void imprimer() 
    {
        try 
          {
            
            Map<String, Object> parameters = new HashMap<>();
            java.awt.Image image2 = null;
            java.awt.Image photo = null;
            try {
                photo = ImageIO.read(agent.getAgentByMatricule(matricule.getText()).getPhoto());
            } catch (IOException ex) {
                Logger.getLogger(AgentPrefetController.class.getName()).log(Level.SEVERE, null, ex);
            }
            parameters.put("logo", image2);
            parameters.put("photo", photo);
            parameters.put("matricule",matricule.getText());
            parameters.put("nom", txt_nom.getText());
            parameters.put("postnom", txt_postnom.getText());
            parameters.put("prenom", txt_prenom.getText());
            parameters.put("genre", getGenre());
           
            parameters.put("telephone",txt_phone.getText());
            parameters.put("mail", txt_mail.getText());
            parameters.put("adresse", txt_adresse.getText());
            parameters.put("date_naissance", txt_date.getValue().toString());
            parameters.put("lieu_naissance", txt_lieu.getText());
            parameters.put("nationalite", txt_natio.getText());
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
    public void openDialog(){
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(null);
        
        if (file != null) {
            fichier = file;
            System.out.println(file.getName()+"\n"+file.getAbsolutePath());
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                stream = new FileInputStream(file);
                
                            
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                
                cir2.setFill(new ImagePattern(image));
            } catch (IOException ex) {
                
            }
            
        }
    }
    private static void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );                 
            fileChooser.getExtensionFilters().addAll(
                
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
    } 
}
