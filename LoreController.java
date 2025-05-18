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
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoreController implements Initializable {
    @FXML private Button exitButton;
    @FXML private Button skipButton;
    @FXML private Button nextButton;
    @FXML private Button backButton;
    @FXML private Text loreText;
    
    private List<String> loreList = Arrays.asList("You blink awake in the middle of a vast garden with no idea how far you are from home…", "All you remember is a strong wind and someone calling back to you…", 
"You rub your eyes and shake your antennae to see colorful houses with all kinds of other insects walking around. It’s very different from where you came from.",  
"Hesitantly, you approach one of them and ask if they know what happened.", 
"“OH! A butterfly? You must be new,” they say with excitement. “The storm must have blown you in. We haven’t had newcomers in ages…”",
"You ask where you are.",
"“Oh of course! Welcome to Wisteria Grove! Feel free to go wherever you want and live anywhere, most houses are empty anyway…”");
    
    int loreIndex = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loreText.setText(loreList.get(loreIndex));
    }

    @FXML
    private void openMainGame (ActionEvent event) {
        loadScene(event, "MainGame.fxml");
    }
    
    @FXML
    private void openMainMenu (ActionEvent event) {
        loadScene(event, "MainMenu.fxml");
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
    @FXML
    private void onNextButtonClicked(ActionEvent event) {
        loreIndex++;
        if (loreIndex >= loreList.size()) {
            loadScene(event, "MainGame.fxml");
        } else {
            loreText.setText(loreList.get(loreIndex));
        }
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) {
        if (loreIndex > 0) {
            loreIndex--;
            loreText.setText(loreList.get(loreIndex));
        }
    }
}
