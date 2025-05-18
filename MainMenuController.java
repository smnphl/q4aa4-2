package q4aaproj_pho_asuncionbosimanuel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    @FXML
    private void openLore(ActionEvent event) {
        loadScene(event, "Lore.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void openMainGame (ActionEvent event) {
        loadScene(event, "MainGame.fxml");
    }

    @FXML
    private void openSettings(ActionEvent event) {
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("Settings.fxml"));
            Stage settingsStage = new Stage();
            settingsStage.setTitle("Settings");
            settingsStage.setScene(new Scene(settingsRoot));
            settingsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openHelp(ActionEvent event) {
        loadScene(event, "Help.fxml");
    }

    @FXML
    private void onExit(ActionEvent event) {
        exitApplication();
    }

    @FXML
    private void openAboutUs(ActionEvent event) {
        loadScene(event, "AboutUs.fxml");
    }
    

    private void loadScene(ActionEvent event, String fxmlFile) {
        try {
            Parent newScene = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(newScene);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitApplication() {
        Platform.exit();
        System.exit(0);
    }
}

