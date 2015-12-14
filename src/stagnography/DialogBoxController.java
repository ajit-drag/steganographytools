/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stagnography;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AZT
 */
public class DialogBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static Stage stage;
    static String str;
    static int length;
    @FXML
    TextArea text;
    @FXML
    void closeStage(){
        stage.close();
    }
    @FXML
    private void saveTextButtonAction(){
        str=text.getText();
        //System.out.println(str);
        mainDocController.textToEmbed=str;
        stage.close();
    }
    @FXML
    private void clearTextButtonAction(){
        text.clear();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         text.setText(str);
    }    
    
}
