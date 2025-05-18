package q2aa2_pho_asuncion.bosi.manuel;
import java.util.Scanner;

public abstract class Character implements Interactable {
    protected String name;
    protected String species;
    protected int relationship;
    protected String dialogue;
    protected double location; 
    protected boolean inARelationship;
    protected InventoryManager inventory = new InventoryManager();
    protected static final Scanner sc = new Scanner(System.in);
    
    boolean interact = true;

    public Character(String name, String species, int relationship, String dialogue, boolean inARelationship, InventoryManager inventory) {
        this.name = name;
        this.species = species;
        this.relationship = relationship;
        this.dialogue = dialogue;
        this.inARelationship = inARelationship;
        this.inventory = new InventoryManager();
    }

    public String getName() {
        return name;
    }

    public String getDialogue() {
        return dialogue;
    }

    public String getSpecies() {
        return species;
    }

    public int getRelationship() {
        return relationship;
    }    

    public double getLocation() {
        return location;
    }

    public boolean inARelationship() {
        return inARelationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public void addToInventory(Item item, int quantity) throws InventoryFull {
        inventory.addToInventory(item, quantity);
    }

    public void removeFromInventory(Item item, int quantity) {
        inventory.removeFromInventory(item, quantity);
    }

    public void printInventory() {
        inventory.printInventory();
    }
    
    @Override
    public void interact() {
        System.out.println(name + " interacts.");
    }
}