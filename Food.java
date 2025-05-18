package q2aa2_pho_asuncion.bosi.manuel;

public class Food extends Item {
    private double price;
    private int quantity;
    private int hungerIncrease;
    private Player player;
    
    public Food(String name, double price, String rarity){
        super(name, true, false, false, false, false, rarity);
        this.price = price;
    }
    
    public double getPrice() {
        return price; 
    }
    
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("\nPrice cannot be negative.");
        }
        this.price = price;
    }   
    
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("\nQuantity cannot be negative.");
        }
        this.quantity = quantity;
    }
    
    public void eat() {
        if (quantity > 0) {
            quantity--;
            player.increaseHunger(hungerIncrease);
            
            System.out.println("\nYou ate one unit of " + name + ". Your hunger is now " + player.getHunger() + "/" + player.getMaxHunger());
            System.out.println("Remaining quantity of " + name + ": " + quantity);

        } else {
            System.out.println("No " + name + " left to eat!");
        }
    }
    
    public void buy(Currency currency) throws InventoryFull, InsufficientBalance {
        System.out.print("\nEnter the food you want to buy: ");
        String type = sc.nextLine();
        System.out.print("\nEnter the quantity of " + type + " you want to buy: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        double totalCost = quantity * price;

        if (currency.getValue() < totalCost) {
            throw new InsufficientBalance("\nInsufficient funds. You need " + (totalCost - currency.getValue()) + " more to buy " + quantity + " of " + type + ".");
        }

        Food food = new Food(type, price, rarity);

        if (!inventory.addToInventory(food, quantity)) {
            throw new InventoryFull("\nInventory is full. Could not purchase " + type + ".");
        }

        currency.setValue((int) (currency.getValue() - totalCost)); 
        System.out.println("\nSuccessfully purchased " + quantity + " of " + type + " for " + totalCost + " currency units.");
    }
    
    public void storeInInventory() throws InventoryFull {
        if (inventory.addToInventory(this, quantity)) {
            System.out.println(name + " (" + quantity + " items) has been added to your inventory.");
        } else {
            System.out.println("\nInventory is full! Unable to add " + name + ".");
        }
    }
    
    @Override
    public void interact() {
        while (interact) {
            try {
                System.out.println("\nAvailable actions: \n1. Eat\n2. Buy\n3. Store in inventory\n4. Exit interaction");
                System.out.print("Choose an action (1-3): ");

                int action = sc.nextInt();
                sc.nextLine();

                Currency currency = new Currency(name, price, rarity);

                switch (action) {
                    case 1:
                        eat();
                        break;
                    case 2:
                        try {
                            buy(currency);
                        } catch (InventoryFull | InsufficientBalance e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            storeInInventory();
                        } catch (InventoryFull e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("\nExiting the interaction");
                        interact = false;
                        break;
                    default:
                        System.out.println("\nInvalid action.");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid input. Please enter a number.");
                sc.nextLine();
            }
        }
    }
}
