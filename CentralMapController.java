
package q4aaproj_pho_asuncionbosimanuel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class CentralMapController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void changeScene(ActionEvent event, String fxmlFile) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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
    public void backToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainGame.fxml")); 
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    public void goToMainGameWithBackground(ActionEvent event, String bgName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGame.fxml"));
        Parent root = loader.load();

        MainGameController controller = loader.getController();
        controller.setBackground(bgName);  

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @FXML
    private void handleButton1(ActionEvent event) throws IOException {
        goToMainGameWithBackground(event, "mushroom");
    }

    @FXML
    private void handleButton5(ActionEvent event) throws IOException {
        goToMainGameWithBackground(event, "stump");
    }

}
