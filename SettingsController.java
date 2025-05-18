package q4aaproj_pho_asuncionbosimanuel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

public class SettingsController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void changeScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
   
    @FXML
    private Button exitToTitleButton; 
    
    @FXML
    private Button openHelpButton;

    @FXML
    private Button backToMainGameButton;
    
    @FXML
    private void exitToTitle() {
        try {
            Stage currentStage = (Stage) exitToTitleButton.getScene().getWindow();
            currentStage.close(); 

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/q4aaproj_pho_asuncionbosimanuel/MainMenu.fxml"));
            Scene mainMenuScene = new Scene(loader.load());

            Stage mainMenuStage = new Stage();
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.setTitle("Main Menu");
            mainMenuStage.show();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }


    @FXML
    private void openHelp(ActionEvent event) throws IOException {
        Stage stage = (Stage) openHelpButton.getScene().getWindow();
        stage.close();  
        changeScene(event, "Help.fxml");
    }

    @FXML
    private void backToMainGame(ActionEvent event) throws IOException {
        Stage stage = (Stage) backToMainGameButton.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/MainGame.fxml"));
        Scene mainGameScene = new Scene(loader.load());

        Stage mainGameStage = new Stage();
        mainGameStage.setScene(mainGameScene);

        mainGameStage.show();
    }


}

