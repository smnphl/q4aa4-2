/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package q2aa2_pho_asuncion.bosi.manuel;

import java.io.IOException;
import java.io.InputStream;

public class Sprite {
    public Sprite getSpriteFor(NPC character) {
        String spritePath = "";
        switch (character.getName()) {
            case "Angel": spritePath = "/storageroom.imgs/flour.png"; break;
            case "Antony": spritePath = "/storageroom.imgs/sugar.png"; break;
            case "Annie Lid": spritePath = "/storageroom.imgs/egg.png"; break;
            case "Aurantia": spritePath = "/storageroom.imgs/egg.png"; break;            
            case "Donna": spritePath = "/storageroom.imgs/butter.png"; break;
            default:  break;
        }
        Sprite img = loadSprite(spritePath);
        return img != null ? img : getEmptySlotIcon(); // fallback if sprite missing
    }

    private static Sprite getEmptySlotIcon() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private static Sprite loadSprite(String path) {
        try {
            java.io.InputStream is = Sprite.class.getResourceAsStream(path);
            if (is == null) {
                System.out.println("Resource not found: " + path);
                return null;
            }
            return new Image(is);
        } catch (Exception e) {
            System.out.println("Error loading image: " + path);
            return null;
        }
    }
    
    
}
