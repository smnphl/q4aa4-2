
package q4aaproj_pho_asuncionbosimanuel;


import javafx.scene.image.Image;

import javafx.scene.image.Image;

public class Item {
    private String name;
    private Image image;
    private String type;  // (Optional) Type of item: weapon, armor, consumable, etc.

    public Item(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
