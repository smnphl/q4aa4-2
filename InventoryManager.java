package q2aa2_pho_asuncion.bosi.manuel;
import java.util.ArrayList;

public class InventoryManager {
    public static final int MAX_STACKS = 36; 
    public static final int MAX_ITEMS_PER_STACK = 100;
    private ArrayList<Item> itemNames = new ArrayList<>();
    private ArrayList<Integer> itemCounts = new ArrayList<>();

    public InventoryManager() {}

    public ArrayList<Item> getItemNames() {
        return itemNames;
    }
    
    public ArrayList<Integer> getItemCounts() {
        return new ArrayList<>(itemCounts);
    }

    public boolean addToInventory(Item item, int quantity) throws InventoryFull {
        for (int i = 0; i < itemNames.size(); i++) {
            if (itemNames.get(i).getName().equals(item.getName())) {
                int currentCount = itemCounts.get(i);
                int newCount = currentCount + quantity;

                if (newCount <= MAX_ITEMS_PER_STACK) {
                    itemCounts.set(i, newCount);
                    itemNames.get(i).setCount(newCount);
                    return true;
                } else {
                    System.out.println("\nCannot add more. Max stack size reached.");
                    return false;
                }
            }
        }

        if (itemNames.size() < MAX_STACKS) {
            item.setCount(quantity);
            itemNames.add(item);
            itemCounts.add(quantity);
            return true;
        }

        throw new InventoryFull("\nCannot add more items. Inventory full.");
    }

    public boolean removeFromInventory(Item item, int quantity) {
        for (int i = 0; i < itemNames.size(); i++) {
            if (itemNames.get(i).getName().equals(item.getName())) {
                int currentCount = itemNames.get(i).getCount();

                if (currentCount >= quantity) {
                    itemNames.get(i).setCount(currentCount - quantity);

                    if (itemNames.get(i).getCount() == 0)
                        itemNames.remove(i);
                    return true;
                } else {
                    System.out.println("\nNot enough quantity to remove.");
                    return false;
                }
            }
        }

        System.out.println("Item not found in inventory.");
        return false;
    }

    public void printInventory() {
        if (itemNames.isEmpty()) {
            System.out.println("\nInventory is empty!");
            return;
        }

        System.out.println("\nInventory:");
        for (int i = 0; i < itemNames.size(); i++) {
            System.out.println((i + 1) + ". " + itemNames.get(i).getName() + " - " + itemCounts.get(i) + " (Rarity: " + itemNames.get(i).getRarity() + ")");
        }
    }

    public boolean updateItemCount(Item item, int newCount) {
        for (int i = 0; i < itemNames.size(); i++) {
            if (itemNames.get(i).getName().equals(item.getName())) {
                if (newCount > 0) {
                    itemCounts.set(i, newCount);
                    itemNames.get(i).setCount(newCount);
                } else {
                    itemNames.remove(i);
                    itemCounts.remove(i);
                }
                return true;
            }
        }
        return false;
    }
}
