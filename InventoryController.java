package q4aaproj_pho_asuncionbosimanuel;

import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

public class InventoryController {

    private ImageView draggedItem;
    private ImageView selectedCell;
    private final Map<ImageView, Image> cellOriginalImages = new HashMap<>();

    // Inventory cells
    @FXML private ImageView inventoryCell1, inventoryCell2, inventoryCell3, inventoryCell4, inventoryCell5, inventoryCell6, inventoryCell7,
                            inventoryCell8, inventoryCell9, inventoryCell10, inventoryCell11, inventoryCell12, inventoryCell13, inventoryCell14,
                            inventoryCell15, inventoryCell16, inventoryCell17, inventoryCell18, inventoryCell19, inventoryCell20, inventoryCell21,
                            inventoryCell22, inventoryCell23, inventoryCell24, inventoryCell25, inventoryCell26, inventoryCell27, inventoryCell28,
                            inventoryCell29, inventoryCell30, inventoryCell31, inventoryCell32, inventoryCell33, inventoryCell34, inventoryCell35, inventoryCell36;

    // Armor cells
    @FXML private ImageView armorCell1, armorCell2, armorCell3, armorCell4, armorCell5, armorCell6;

    @FXML
    public void initialize() {
        ImageView[] allCells = {
            inventoryCell1, inventoryCell2, inventoryCell3, inventoryCell4, inventoryCell5, inventoryCell6, inventoryCell7, inventoryCell8,
            inventoryCell9, inventoryCell10, inventoryCell11, inventoryCell12, inventoryCell13, inventoryCell14, inventoryCell15, inventoryCell16,
            inventoryCell17, inventoryCell18, inventoryCell19, inventoryCell20, inventoryCell21, inventoryCell22, inventoryCell23, inventoryCell24,
            inventoryCell25, inventoryCell26, inventoryCell27, inventoryCell28, inventoryCell29, inventoryCell30, inventoryCell31, inventoryCell32,
            inventoryCell33, inventoryCell34, inventoryCell35, inventoryCell36,
            armorCell1, armorCell2, armorCell3, armorCell4, armorCell5, armorCell6
        };

        for (ImageView cell : allCells) {
            setupCellDragAndDrop(cell);
            cellOriginalImages.put(cell, cell.getImage());
            addEffects(cell); // ✅ Apply effects to ALL cells now
        }
        setupEscapeKeyHandler();
    }

    private void setupCellDragAndDrop(ImageView cell) {
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
                Image temp = cell.getImage();
                cell.setImage(draggedItem.getImage());
                draggedItem.setImage(temp);
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            draggedItem = null;
            event.consume();
        });

        cell.setOnDragDone(DragEvent::consume);
    }

    private void addEffects(ImageView cell) {
        if (cell != null) {
            // Hover effect
            ColorAdjust hoverEffect = new ColorAdjust();
            hoverEffect.setBrightness(-0.2);

            // Selection effect
            ColorAdjust selectionEffect = new ColorAdjust();
            selectionEffect.setBrightness(-0.4);

            cell.setOnMouseEntered(event -> {
                if (cell != selectedCell) {
                    cell.setEffect(hoverEffect);
                }
            });

            cell.setOnMouseExited(event -> {
                if (cell != selectedCell) {
                    cell.setEffect(null);
                }
            });

            cell.setOnMouseClicked(event -> {
                if (selectedCell != null) {
                    selectedCell.setEffect(null);
                }
                selectedCell = cell;
                cell.setEffect(selectionEffect);
            });
        }
    }
    private void setupEscapeKeyHandler() {
    // Use Platform.runLater to ensure scene is ready
    javafx.application.Platform.runLater(() -> {
        inventoryCell1.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // Get the stage and close it
                ((Stage) inventoryCell1.getScene().getWindow()).close();
            }
        });
    });
    }
}