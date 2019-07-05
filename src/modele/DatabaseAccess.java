package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ben MUZINGU
 */
public class DatabaseAccess {
    public static Connection cnx;
    public static java.sql.Statement  St;
    public static ResultSet Rs;
//    public static String m_user     = "pomba";
//    public static String m_password = "pomba";
    public static String ip = lectureFichier("C:\\ProgramData\\pointage\\config.txt");
//    public static String m_url      = "jdbc:mysql://"+ip+":3306/school_bd";
//    
    public static String m_user     = "root";
    public static String m_password = "";
    public static String m_url      = "jdbc:mysql://"+ip+":3306/pointage";
    
    public static Connection connecterDB(){
       return DatabaseAccess.GetInstance(); 
    }
    
    // creer une instance de la connexion  
    public static Connection GetInstance(){
        
       if(cnx == null){ //verification  de la connexion existante
            try{
                Class.forName("com.mysql.jdbc.Driver");
                cnx = DriverManager.getConnection(m_url, m_user, m_password);
                St  = cnx.createStatement();
            }catch (ClassNotFoundException ec){
                ec.getMessage();
                ec.printStackTrace();
            }catch(SQLException e){
                if(e.getErrorCode()==0){
                    
                    ip = JOptionPane.showInputDialog(null,"Impossible de se connecter au serveur\n"+ e.getMessage()+"\nVeuillez en informer a votre administrateur ou \nEntrer l'adresse IP du serveur et réessayer");
                    ecrireFichier("C:\\ProgramData\\SchoolManager\\config.txt");
                }else
                    JOptionPane.showMessageDialog(null, "Erreur de connexion\nVous n'etes pas connecter a la base de donnée\nVeuillez activer la base de donnée ou en informer à votre administrateur\n"+e.getMessage()+"\nRéesayer une fois que la base de donnée sera activé");
            }
        }
        return cnx;
    }
    public static void ecrireFichier(String filePath){
        FileWriter fw;
        String valeurs="";
        File fichier= new File(filePath);
        try {
            fw = new FileWriter(fichier);
            fw.write(ip); 
            fw.close();
            JOptionPane.showMessageDialog(null,"Veuillez relancer l'application");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    public static String lectureFichier(String filePath) {
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
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                lecture.close();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return valeurs;
    }

}
