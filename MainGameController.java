
package q4aaproj_pho_asuncionbosimanuel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;

public class MainGameController implements Initializable {
    @FXML private ImageView player;
    @FXML private AnchorPane rootpane;
    @FXML private GridPane inventoryGrid;
    @FXML private ImageView settingsButton, centralButton, buildButton, inventoryButton;
    @FXML private Rectangle border1, border2, border3, border4;
    @FXML private ImageView inventorySlot1, inventorySlot2, inventorySlot3, inventorySlot4,
                         inventorySlot5, inventorySlot6, inventorySlot7, inventorySlot8,
                         inventorySlot9, inventorySlot10, inventorySlot11, inventorySlot12;
    @FXML private ImageView backgroundImage;
    private final double speed = 10;
    private final double scrollThreshold = 200;
    private final double scrollAmount = 10;
    private final double bgMinX = -220, bgMaxX = 220;
    private final double bgMinY = -1066, bgMaxY = 0;
    private boolean shiftDown = false;
    private Stage inventoryStage = null;
    private final List<Node> border = new ArrayList<>();
    private final Map<ImageView, Image> cellOriginalImages = new HashMap<>();
    private ImageView draggedItem, selectedCell;
    private final List<Node> borders = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tooltip.install(settingsButton, new Tooltip("Settings"));
        Tooltip.install(centralButton, new Tooltip("Central Map"));
        Tooltip.install(buildButton,   new Tooltip("Build"));
        Tooltip.install(inventoryButton, new Tooltip("Inventory (E)"));
        
      
        
        borders.addAll(Arrays.asList(border1, border2, border3, border4));
        
        ImageView[] allCells = {
            inventorySlot1, inventorySlot2, inventorySlot3, inventorySlot4,
            inventorySlot5, inventorySlot6, inventorySlot7, inventorySlot8,
            inventorySlot9, inventorySlot10, inventorySlot11, inventorySlot12
        };

        for (ImageView cell : allCells) {
            setupCellDragAndDrop(cell);
            cellOriginalImages.put(cell, cell.getImage());
            addEffects(cell);
        }
        setupEscapeKeyHandler();
        border.addAll(Arrays.asList(border1, border2, border3, border4));

        Platform.runLater(() -> {
            Scene scene = rootpane.getScene();
            if (scene != null) {
                scene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.SHIFT) {
                        shiftDown = true;
                    }
                    if (event.getCode() == KeyCode.E) {
                        openInventory();
                        return;
                    }
                    if (event.getCode() == KeyCode.ESCAPE) {
                        closeInventory();
                        return;
                    }
                    move(event);
                });
                
                scene.setOnKeyReleased(event -> {
                    if (event.getCode() == KeyCode.SHIFT) {
                        shiftDown = false;
                    }
                });
            }
        });
    }

    public void move(KeyEvent event) {
        double currentSpeed = shiftDown ? speed * 4 : speed;
        double deltaX = 0, deltaY = 0;

        switch (event.getCode()) {
            case W:
            case UP:
                deltaY = -currentSpeed;
                break;
            case S:
            case DOWN:
                deltaY = currentSpeed;
                break;
            case A:
            case LEFT:
                deltaX = -currentSpeed;
                break;
            case D:
            case RIGHT:
                deltaX = currentSpeed;
                break;
            case E:
                openInventory();
                return;
            default:
                return;
        }

        double newX = player.getTranslateX() + deltaX;
        double newY = player.getTranslateY() + deltaY;
        player.setTranslateX(newX);
        player.setTranslateY(newY);

        if (!isColliding()) {
            scrollMapIfNeeded(deltaX, deltaY);
        } else {
            player.setTranslateX(player.getTranslateX() - deltaX);
            player.setTranslateY(player.getTranslateY() - deltaY);
            boolean bgMoved = scrollBackground(deltaX, deltaY);
            if (!bgMoved) {
                System.out.println("Collision detected. Movement blocked.");
            }
        }
    }

    private void scrollMapIfNeeded(double dx, double dy) {
        Scene scene = rootpane.getScene();
        if (scene == null) return;
        Bounds b = player.localToScene(player.getBoundsInLocal());
        boolean scrolled = false;
        if (dx != 0 && b.getMinX() > scene.getWidth() * 0.4 && b.getMinX() < scene.getWidth() * 0.6) {
            scrolled = scrollBackground(dx, 0);
        }
        if (dy != 0 && b.getMinY() > scene.getHeight() * 0.4 && b.getMinY() < scene.getHeight() * 0.6) {
            scrolled = scrollBackground(0, dy) || scrolled;
        }
        if (scrolled) {
            player.setTranslateX(player.getTranslateX() - dx);
            player.setTranslateY(player.getTranslateY() - dy);
        }
    }

    private boolean scrollBackground(double dx, double dy) {
        double newBgX = backgroundImage.getTranslateX() - dx;
        double newBgY = backgroundImage.getTranslateY() - dy;
        newBgX = Math.min(Math.max(newBgX, bgMinX), bgMaxX);
        newBgY = Math.min(Math.max(newBgY, bgMinY), bgMaxY);
        boolean moved = newBgX != backgroundImage.getTranslateX() || newBgY != backgroundImage.getTranslateY();
        if (moved) {
            backgroundImage.setTranslateX(newBgX);
            backgroundImage.setTranslateY(newBgY);
        }
        return moved;
    }

    private boolean isColliding() {
        Bounds playerBounds = player.localToScene(player.getBoundsInLocal());

        for (Node borders : border) {
            Bounds borderBounds = null;

            if (borders instanceof Rectangle) {
                borderBounds = ((Rectangle) borders).localToScene(borders.getBoundsInLocal());
            }

            if (borderBounds != null && playerBounds.intersects(borderBounds)) {
                System.out.println("Collision detected with: " + borders.getId());
                return true; //movement blocked
            }
        }

        return false; //no collision
    }

    @FXML
    private void openInventory() {
        if (inventoryStage != null && inventoryStage.isShowing()) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/wisteriagrove/Inventory.fxml"));
            Parent invRoot = loader.load();
            Scene invScene = new Scene(invRoot, 1000, 600, Color.TRANSPARENT);
            inventoryStage = new Stage();
            inventoryStage.initOwner(rootpane.getScene().getWindow());
            inventoryStage.initModality(Modality.WINDOW_MODAL);
            inventoryStage.initStyle(StageStyle.TRANSPARENT);
            inventoryStage.setResizable(false);
            inventoryStage.setScene(invScene);
            inventoryStage.centerOnScreen();
            invScene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) closeInventory();
            });
            inventoryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeInventory() {
        if (inventoryStage != null) {
            inventoryStage.close();
            inventoryStage = null;
        }
    }

    @FXML
    public void openSettings(MouseEvent event) {
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("wisteriagrove/Settings.fxml"));
            Stage settingsStage = new Stage();
            settingsStage.initOwner(rootpane.getScene().getWindow());
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.initStyle(StageStyle.UNDECORATED);
            settingsStage.setScene(new Scene(settingsRoot));
            settingsStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openCentralMap(MouseEvent event) throws IOException {
        loadScene(event, "CentralMap.fxml");
    }

    @FXML
    public void openBuildingSelection(MouseEvent event) throws IOException {
        loadScene(event, "BuildingSelection.fxml");
    }

    public void loadScene(MouseEvent event, String fxmlFile) {
        try {
            Parent newScene = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(newScene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEffects(ImageView cell) {
        ColorAdjust hover = new ColorAdjust(); hover.setBrightness(-0.2);
        ColorAdjust select = new ColorAdjust(); select.setBrightness(-0.4);
        cell.setOnMouseEntered(ev -> { if (cell != selectedCell) cell.setEffect(hover); });
        cell.setOnMouseExited(ev -> { if (cell != selectedCell) cell.setEffect(null); });
        cell.setOnMouseClicked(ev -> {
            if (selectedCell != null) selectedCell.setEffect(null);
            selectedCell = cell;
            cell.setEffect(select);
        });
    }

    public void setupCellDragAndDrop(ImageView cell) {
        cell.setOnDragDetected(event -> {
            if (cell.getImage() != null) {
                Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putImage(cell.getImage());
                db.setContent(content);
                draggedItem = cell;
            }
            event.consume();
        });
        cell.setOnDragOver(event -> {
            if (event.getGestureSource() != cell && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        cell.setOnDragDropped(event -> {
            if (draggedItem == null) return;
            Dragboard db = event.getDragboard();
            if (db.hasImage()) {
                Image tmp = cell.getImage();
                cell.setImage(draggedItem.getImage());
                draggedItem.setImage(tmp);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            draggedItem = null;
            event.consume();
        });
        cell.setOnDragDone(DragEvent::consume);
    }

    public void setupEscapeKeyHandler() {
        Platform.runLater(() -> {
            if (inventorySlot1.getScene() != null) {
                inventorySlot1.getScene().setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        ((Stage)inventorySlot1.getScene().getWindow()).close();
                    }
                });
            }
        });
    }

    public void setBackground(String bgName) {
        String path;
        switch (bgName) {
            case "mushroom":
                path = "/imgs/mushroombg.png";
                break;
            case "stump":
                path = "/imgs/stumpbg.png";
                break;
            default:
                path = "/imgs/mushroombg.png";
                break;
        }
        backgroundImage.setImage(new Image(getClass().getResourceAsStream(path)));
    }
}
