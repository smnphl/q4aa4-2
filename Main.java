package q2aa2_pho_asuncion.bosi.manuel;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //NPC-Player
        Player bella = new Player("Bella", "Bee", 0, "Bzz.", new double[]{0,0}, false, 1000, new InventoryManager(), 100, new ArrayList<NPC>(), new ArrayList<NPC>(), new ArrayList<Structure>());

        NPC angel = new NPC("Angel", "Ant", 0, "...Who are you?", new double[]{-30, 23}, false, new InventoryManager());
        NPC annielid = new NPC("Annie Lid", "Worm", 0, "Have I met you before, dear?", new double[]{-30, 23}, true, new InventoryManager());
        NPC antony = new NPC("Antony", "Ant", 0, "I don't believe I've encountered you before!", new double[]{-30, 23}, false, new InventoryManager());
        NPC aurantia = new NPC("Aurantia", "Spider", 0, "...", new double[]{-30, 23}, false, new InventoryManager());
        NPC beeatrice = new NPC("Beeatrice", "Bee", 0, "Hey.", new double[]{-30, 23}, false, new InventoryManager());
        NPC chantelle = new NPC("Chantelle", "Ant", 0, "Hii! What's your name?", new double[]{-30, 23}, false, new InventoryManager());
        NPC donna = new NPC("Donna", "Ladybug", 0, "Oh! What are you....", new double[]{-30, 23}, false, new InventoryManager());
        NPC dungcan = new NPC("Dungcan", "Beetle", 0, "Yo, what's up?", new double[]{-30, 23}, false, new InventoryManager());
        NPC phiddipus = new NPC("Phiddipus", "Spider", 0, "Ah, a newcomer?", new double[]{-30, 23}, false, new InventoryManager());  
        NPC shelbee = new NPC("Shelbee", "Bee", 0, "Hey! We've never met, I'm Shelbee!", new double[]{-30, 23}, false, new InventoryManager());
        NPC william = new NPC("William", "worm", 0, "eh?", new double[]{-30, 23}, false, new InventoryManager());

        Item nectar = new Food("Nectar", 100.0, "uncommon");
        Item pollen = new Food("Pollen", 100.0, "common");
        Item grass = new Food("Grass", 50.0, "common");
        Item flower = new Food("Flower", 50.0, "common");
        Item seed = new Food("Seed", 50.0, "uncommon");
        Item leaf = new Food("Leaf", 50.0, "common");
        Item paint = new Tool("Paint", 1, 1, 10, false);
        Item hammer = new Tool("Hammer", 2, 3, 1, false);
        
        Plant dandelion = new Plant("Dandelion", "common", 1, 80, false, false);
        Plant lavender = new Plant("Lavender", "common", 1, 1050, false, false);
        
        Structure bellaHouse = new House("Bella's House", new ArrayList<Character>(), 0, "You live here", 0.0, 0.0, 26000.0, new ArrayList<Item>(), 0, new ArrayList<String>());
        Structure antonyHouse = new Structure("antony's House", new ArrayList<Character>(), 0, "antony lives here", -30.0, 23.0, 13000.0, new ArrayList<Item>(), 0, new ArrayList<String>());
        Structure angelHouse = new Structure("angel's House", new ArrayList<Character>(), 0, "angel lives here", 60.0, 104.0, 10000.0, new ArrayList<Item>(), 0, new ArrayList<String>());
        Structure emptyPlot = new Structure("Empty plot", new ArrayList<Character>(), 0, "Empty plot", 20.6, -13.0, 0.0, new ArrayList<Item>(), 0, new ArrayList<String>());

        bella.addLoveInterest(antony);
        bella.places.add(bellaHouse);
        bella.places.add(antonyHouse);
        bella.places.add(angelHouse);
        bella.places.add(emptyPlot);
        antonyHouse.addInhabitant(antony);
        angelHouse.addInhabitant(antony);
        
        emptyPlot.addMaterial(paint);
        emptyPlot.addMaterial(hammer);
        
        try {
            bella.inventory.addToInventory(nectar, 5);
            bella.inventory.addToInventory(pollen, 20);
            bella.inventory.addToInventory(dandelion, 1);
            bella.inventory.addToInventory(lavender, 1);
        } catch (InventoryFull e) {
            System.out.println("\nCould not add items to Bella's inventory: " + e.getMessage());
        }

        try {
            antony.inventory.addToInventory(grass, 30);
            antony.inventory.addToInventory(flower, 15);
            antony.inventory.addToInventory(seed, 10);
        } catch (InventoryFull e) {
            System.out.println("\nCould not add items to antony's inventory: " + e.getMessage());
        }
        
        try {
            angel.inventory.addToInventory(leaf, 20);
        } catch (InventoryFull e) {
            System.out.println("\nCould not add items to " + angel.name + "'s inventory: " + e.getMessage());
        }

        bella.walk();
        bella.interact = true;
        dandelion.interact();
        bella.interact = true;
        lavender.interact();
        
        bella.walk();
        bella.interact = true;
        bella.interactWithNPC(antony);
        
        bella.walk();
        bella.interact = true;
        bella.interactWithNPC(angel);
        
        bella.walk();
        bella.interact = true;
        emptyPlot.interact();
    }
}
