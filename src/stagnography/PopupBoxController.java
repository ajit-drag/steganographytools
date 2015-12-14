/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stagnography;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AZT
 */
public class PopupBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static Stage popupStage;
    public static String popupMessage;
    @FXML
    Text thisText;
    @FXML
    private void exit(){
        popupStage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        thisText.setText(popupMessage);
    }    
    
}
