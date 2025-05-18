package q2aa2_pho_asuncion.bosi.manuel;
import java.util.ArrayList;

public class Currency extends Item {
    private String material;
    private double value;

    public Currency(String name, double value, String rarity) {
        super(name, false, false, false, true, false, rarity);
        this.value = value;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("\nValue cannot be negative.");
        }
        this.value = value;
    }

    public void exchange(Item item, InventoryManager inventory) {
        int exchangedValue = 0;
        String rarity = item.getRarity().toLowerCase();

        switch (rarity) {
            case "common":
                exchangedValue = 50;
                break;
            case "uncommon":
                exchangedValue = 100;
                break;
            case "rare":
                exchangedValue = 300;
                break;
            case "epic":
                exchangedValue = 750;
                break;
            case "legendary":
                exchangedValue = 1000;
                break;
            default:
                System.out.println("\nInvalid rarity. Exchange failed.");
                return;
        }

        if (inventory.removeFromInventory(item, 1)) {
            value += exchangedValue;
            System.out.println("\nExchanged " + item.getName() + " for " + exchangedValue + " " + material + ". Current balance: " + value);
        } else {
            System.out.println("\nItem not found in inventory. Exchange failed.");
        }
    }

    public void pay(int amount) throws InsufficientBalance {
        if (amount <= 0) {
            System.out.println("\nPayment amount must be positive.");
            return;
        }

        if (this.value >= amount) {
            this.value -= amount;
            System.out.println("\nPaid " + amount + " from " + material + ". Remaining balance: " + value);
        } else {
            throw new InsufficientBalance("\nInsufficient balance. Payment failed.");
        }
    }

    @Override
    public void interact() {
        while (interact) {
            try {
                System.out.println("\nAvailable actions: \n1. Exchange\n2. View Inventory\n3. Exit interaction");
                System.out.print("Choose an action (1-3): ");

                int action = sc.nextInt();
                sc.nextLine();

                switch (action) {
                    case 1:
                        ArrayList<Item> items = inventory.getItemNames();
                        for (Item item : items) {
                            System.out.println(item.getName());
                        }

                        System.out.print("\nEnter the name of the item you want to exchange: ");
                        String itemName = sc.nextLine();

                        Item itemToExchange = null;
                        for (Item item : items) {
                            if (item.getName().equals(itemName)) {
                                itemToExchange = item;
                                break;
                            }
                        }

                        if (itemToExchange != null) {
                            exchange(itemToExchange, inventory);
                        } else {
                            System.out.println("\nItem not found in your inventory.");
                        }
                        break;
                    case 2:
                        inventory.printInventory();
                        break;
                    case 3:
                        System.out.println("\nExiting interaction.");
                        interact = false;
                        break;
                    default:
                        System.out.println("\nInvalid action. Please choose between 1-3.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }
}