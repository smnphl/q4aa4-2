package q2aa2_pho_asuncion.bosi.manuel;

public class Ingredient extends Item {
    private double price;
    private String type;
    
    public Ingredient(String name, double price, String rarity){
        super(name, true, false, false, false, false, rarity);
        this.price = price;
    }
    
    public double getPrice() {
        return price; 
    }
    
    public void setPrice(double price){ 
        this.price = price; 
    }  
    
    public void buy(Currency currency) throws InventoryFull, InsufficientBalance {
        System.out.print("\nEnter the type of ingredient you want to buy: ");
        String type = sc.nextLine();
        System.out.print("\nEnter the quantity of " + type + " you want to buy: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        double totalCost = quantity * price;

        if (currency.getValue() < totalCost) {
            throw new InsufficientBalance("\nInsufficient funds. You need " + (totalCost - currency.getValue()) + " more to buy " + quantity + " of " + type + ".");
        }

        Ingredient ingredientItem = new Ingredient(type, price, rarity);

        if (!inventory.addToInventory(ingredientItem, quantity)) {
            throw new InventoryFull("\nInventory is full. Could not purchase " + type + ".");
        }

        currency.setValue((int) (currency.getValue() - totalCost));
        System.out.println("\nSuccessfully purchased " + quantity + " of " + type + " for " + totalCost + " currency units.");
    }
    
    public void mix() {
        System.out.println("\n" + name + " is being mixed with other ingredients.");
    }
    
    public void use() {
        boolean removed = inventory.removeFromInventory(this, 1);

        if (removed) {
            System.out.println("\nOne unit of " + getName() + " has been used.");
        } else {
            System.out.println("\n" + getName() + " is not available in the inventory.");
        }
    }

    @Override
    public void interact() {
        try {
            while (interact) {
                System.out.println("\nAvailable actions: \n1. Buy\n2. Use\n3. Mix ingredients\n4. Exit interaction");
                System.out.print("Choose an action (1-3): ");

                int action = sc.nextInt();
                sc.nextLine();

                Currency currency = new Currency(name, price, rarity);

                switch (action) {
                    case 1:
                        try {
                            buy(currency);
                        } catch (InventoryFull | InsufficientBalance e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        use();
                        break;
                    case 3:
                        mix();
                        break;
                    case 4:
                        System.out.println("\nExiting the interaction");
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
