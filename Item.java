package q2aa2_pho_asuncion.bosi.manuel;
import java.util.Scanner;

public abstract class Item implements Interactable {
    protected String name;
    protected int count; //how many of the Item r in inventory
    protected boolean isTradeable;
    protected boolean isInventory;
    protected boolean isFood;
    protected boolean isMoney;
    protected boolean isPlant;
    protected String rarity;
    protected InventoryManager inventory = new InventoryManager();
    protected static final Scanner sc = new Scanner(System.in);
    
    boolean interact = true;
    
    public Item(String name, boolean isTradeable, boolean isInventory, boolean isFood, boolean isMoney, boolean isPlant, String rarity) {
        this.name = name;
        this.isTradeable = isTradeable;
        this.isInventory = isInventory;
        this.isMoney = isMoney;
        this.isFood = isFood;
        this.rarity = rarity;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("\nQuantity cannot be negative.");
        }
        this.count = count;
    }
    
    public boolean checkTradeable() {
        return true; 
    }

    public boolean checkInventory() {
        return true; 
    }

    public boolean checkFood() {
        return false; 
    }

    public boolean checkMoney() {
        return false; 
    }
    
    public String getRarity() {
        return rarity;
    }
    
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    
    @Override
    public void interact() {
        System.out.println("smth");
    }
}
