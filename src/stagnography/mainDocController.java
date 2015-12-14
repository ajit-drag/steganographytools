/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stagnography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 *
 * @author Ajit Singh
 */
public class mainDocController implements Initializable {

    static Stage mainStage;
    static BufferedImage bufferedImage, finalEmbeddedImage;
    boolean isImagePresent, isTextDialogOpen = false;
    static String textToEmbed;
    FileChooser fileChooser = new FileChooser();
    FileChooser fileChooser2 = new FileChooser();
    static int msgLength;
    @FXML
    public Button uploadButton, convertButton, clearImageButton, exitButton, textButton;
    @FXML
    ImageView imageBox = new ImageView();

    @FXML
    private void uploadButtonAction(ActionEvent event) throws IOException {

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG,extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(mainStage);
        System.out.println(file);

        try {
            bufferedImage = ImageIO.read(file);
            MainAlgo.decodeImage(bufferedImage);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageBox.setImage(image);

        } catch (IOException ex) {
            //Logger.getLogger(JavaFXPixel.class.getName()).log(Level.SEVERE, null, ex);
        }
        isImagePresent = true;
        //
        clearImageButton.setMaxSize(25, 25);
        clearImageButton.setMinSize(25, 25);
        clearImageButton.setText("X");
        //
        uploadButton.setDisable(true);
        uploadButton.setMaxSize(0, 0);
        uploadButton.setMinSize(0, 0);
        convertButton.setDisable(false);
        callTextDialog();

    }

    static void getEmbededImage(BufferedImage embeddedImage) {
        finalEmbeddedImage = embeddedImage;

    }

    @FXML
    private void convertButtonAction() throws IOException {
        if (isImagePresent == true) {
            MainAlgo.embedImage(bufferedImage, textToEmbed);
            callPopup("Embedding Done");
        } else {
            System.out.println("No Image to Convert");
        }
    }

    @FXML
    private void clearImageButtonAction() {
        imageBox.setImage(null);
        isImagePresent = false;
        uploadButton.setDisable(false);
        uploadButton.setMaxSize(100, 100);
        uploadButton.setMinSize(100, 100);
        convertButton.setDisable(true);
        clearImageButton.setMaxSize(0, 0);
        clearImageButton.setMinSize(0, 0);
    }

    @FXML
    private void exitButtonAction() {
        Platform.exit();
    }

    @FXML
    private void minimizeButtonAction() {
        Stagnography.minimizeStage();
    }

    @FXML
    private void textButtonAction() throws IOException {
        callTextDialog();
    }

    private void callTextDialog() throws IOException {  //this method is used to call Text Dialog box
        DialogBoxController.str = textToEmbed;
        Parent parent = FXMLLoader.load(getClass().getResource("DialogBox.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        DialogBoxController.stage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void saveButtonAction() {
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser2.getExtensionFilters().addAll(extFilterPNG);
        File f = fileChooser2.showSaveDialog(mainStage);
        //System.out.println(f);

        try {
            if (f.exists()) {
                f.delete();
            }
            ImageIO.write(finalEmbeddedImage, "PNG", f);
        } catch (IOException ex) {
        }
        if (f != null) {
            // openNewImageWindow(file);
        }
    }
    static void decodedText(String decodedText){
        textToEmbed=decodedText;
    }
    public void callPopup(String popupMessage) throws IOException{
        PopupBoxController.popupMessage=popupMessage;
        Parent parent = FXMLLoader.load(getClass().getResource("popupBox.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        PopupBoxController.popupStage = stage;
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show(); 
    }
    @FXML
    private void linkButtonClicked(ActionEvent event) throws Exception {
        java.awt.Desktop.getDesktop().browse(new URI("http://www.coolstagnogrphy.com"));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearImageButton.setMaxSize(0, 0);
        clearImageButton.setMinSize(0, 0);
        convertButton.setDisable(true);

    }

}
