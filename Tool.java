package q2aa2_pho_asuncion.bosi.manuel;
import java.util.ArrayList;

public class Tool extends Item {
    private int level;
    private int breakSpeed;
    private int reach;
    private boolean isEquipped;
    private ArrayList<Item> breakableItems;
    
    public Tool(String name, int level, int breakSpeed, int reach, boolean isEquipped) {
        super(name, true, true, false, false, false, "common");
        this.level = level;
        this.breakSpeed = breakSpeed;
        this.reach = reach;
        this.isEquipped = isEquipped;
        this.breakableItems = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }
    
    public int getReach() {
        return reach;
    }
    
    public int getBreakSpeed() {
        return breakSpeed;
    }
    
    public boolean isEquipped() {
        return isEquipped;
    }
    
    public void equip() {
        if (isEquipped) {
            System.out.println("\n" + name + " is already equipped.");
        } else {
            isEquipped = true;
            System.out.println("\nYou equipped the " + name + ".");
        }
    }
    
    public void unequip() {
        if (!isEquipped) {
            System.out.println("\n" + name + " is not currently equipped.");
        } else {
            isEquipped = false;
            System.out.println("\nYou unequipped the " + name + ".");
        }
    }
    
    public void upgrade() {
        level++;
        breakSpeed += 1;
        reach += 1;
        
        System.out.println("\n" + name + " upgraded to level " + level + ". Break speed is now " + breakSpeed + ", reach is now " + reach + ".");
    }
    
    public void breakItem(Item item, int distance) {
        if (distance > this.reach) {
            System.out.println("\nThe item is out of reach. Move closer to break it.");
            return;
        }

        if (breakableItems.contains(item.getName())) {
            System.out.println("\nBreaking " + item.getName() + "...");
            System.out.println(item.getName() + " has been successfully broken!");
        } else {
            System.out.println("\n" + item.getName() + " cannot be broken with this tool.");
        }
    }

    
    @Override
    public void interact() {
        try {
            while (interact) {
                System.out.println("\nAvailable actions: \n1. Equip tool\n2. Upgrade tool\n3. Break an item\n4. Exit interaction");
                System.out.print("Choose an action (1-4): ");

                int action = sc.nextInt();
                sc.nextLine();

                switch (action) {
                    case 1:
                        equip();
                        break;
                    case 2:
                        upgrade();
                        break;
                    case 3:
                        System.out.println("\nEnter the name of the item you want to break:");
                        sc.nextLine();
                        String itemName = sc.nextLine();

                        //assume a fixed distance for now, could be based on player position
                        System.out.println("\nEnter the distance to the item (within reach of " + this.reach + "):");
                        int distance = sc.nextInt();

                        Item itemToBreak = new Item(itemName, true, true, false, false, false, rarity) {};
                        breakItem(itemToBreak, distance);
                        break;
                    case 4:
                        System.out.println("\nExiting the interaction.");
                        interact = false;
                        break;
                    default:
                        System.out.println("\nInvalid action.");
                }
            }
        } catch (Exception e) {
            System.out.println("\nInvalid input. Please enter a number.");
            sc.nextLine();
        }
    }
}
