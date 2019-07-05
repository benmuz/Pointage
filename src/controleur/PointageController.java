/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import modele.Agent;
import modele.Pointage;


/**
 * FXML Controller class
 *
 * @author JOKISS
 */
public class PointageController implements Initializable {

     @FXML
    private JFXTextField txt_mat;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXButton btn_ok;

    @FXML
    private TableView<Pointage> tab_point;

    @FXML
    private TableColumn col_id;

    @FXML
    private TableColumn colAg;

    @FXML
    private TableColumn colDate;

    @FXML
    private TableColumn colArr;

    @FXML
    private TableColumn colSortie;

    @FXML
    private TableColumn colIdAg;

    Agent agent;
    Pointage pointage;
    @FXML
    void recherche(KeyEvent event) {

    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       agent = new Agent();
       pointage = new Pointage();
       col_id.setCellValueFactory(new PropertyValueFactory<Agent,String>("id"));
       colAg.setCellValueFactory(new PropertyValueFactory<Agent,String>("ag"));
       colDate.setCellValueFactory(new PropertyValueFactory<Agent,String>("dateJ"));
       colSortie.setCellValueFactory(new PropertyValueFactory<Agent,String>("sortie"));
       colArr.setCellValueFactory(new PropertyValueFactory<Agent,String>("arrive"));
       colIdAg.setCellValueFactory(new PropertyValueFactory<Agent,String>("idAg"));
       tab_point.setItems(pointage.afficher());
       
      
    } 
    @FXML
    public void supprimer(){
        ObservableList<Pointage> setected;
        setected = tab_point.getSelectionModel().getSelectedItems();
        Pointage p = setected.get(0);
        pointage.supprimer(p.getId());
        tab_point.setItems(pointage.afficher());
    }
    @FXML
    public void pointer(){
        agent =agent.getAgentByMatricule(txt_mat.getText());
        
        if(agent.getIdAgent()!=0){
            try {
                if (pointage.getLastPointageByAgent(agent).getSortie()!=null) {
                    pointage.pointageEntrer(agent);
                }else{
                    
                    
                    pointage.pointageSorti(agent);
                }
                
                txt_mat.setText(null);

                tab_point.setItems(pointage.afficher());
                System.out.println("pointer");
            } catch (SQLException ex) {
                Logger.getLogger(PointageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "le matricule que vous avez saisi ne pas valide veuillez saisir un matricule valide\n");
            
        }
    }
    
}
