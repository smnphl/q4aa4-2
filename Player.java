package q2aa2_pho_asuncion.bosi.manuel;
import java.util.ArrayList;

public class Player extends Character {
    private int money;
    private int energy;
    private final int maxEnergy = 100;
    private int hunger; //basically how full player is
    private final int maxHunger = 100; //if ur REALLY full
    public ArrayList<NPC> friends;
    public ArrayList<NPC> loveInterests;
    public ArrayList<Structure> places;
    public double destinationX, destinationY;
    
    public Player(String name, String species, int relationship, String dialogue, double[] location, boolean inARelationship, int money, InventoryManager inventory, int hunger, ArrayList<NPC> friends, ArrayList<NPC> loveInterests, ArrayList<Structure> places) {
        super(name, species, relationship, dialogue, inARelationship, inventory);
        this.money = money;
        this.energy = maxEnergy;
        this.hunger = maxHunger;
        this.friends = friends;
        this.loveInterests = loveInterests;
        this.places = places;
        this.inventory = new InventoryManager();
    }
    
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getMaxHunger() {
        return maxHunger;
    }
    
    public void increaseHunger(int increase) {
        this.hunger = Math.min(hunger + increase, maxHunger);
    }
    
    public Item chooseItemToTrade(NPC npc) {
        System.out.println("\n-----------\nChoose an item to trade from " + npc.name + "'s inventory:");

        npc.inventory.printInventory();

        ArrayList<Item> items = npc.inventory.getItemNames();
        ArrayList<Integer> counts = npc.inventory.getItemCounts();

        if (items.isEmpty()) {
            System.out.println("\n" + npc.name + "'s inventory is empty.");
            return null;
        }

        int itemIndex = -1;
        while (itemIndex < 1 || itemIndex > items.size()) {
            System.out.print("\nEnter item number: ");
            itemIndex = sc.nextInt();
            if (itemIndex < 1 || itemIndex > items.size()) {
                System.out.println("\nInvalid choice. Please enter a number between 1 and " + items.size());
            }
        }

        int quantityToTrade = -1;
        while (quantityToTrade < 1 || quantityToTrade > counts.get(itemIndex - 1)) {
            System.out.print("\nHow much of it?: ");
            quantityToTrade = sc.nextInt();

            if (quantityToTrade < 1 || quantityToTrade > counts.get(itemIndex - 1)) {
                System.out.println("\nInvalid quantity. You only have " + counts.get(itemIndex - 1) + " of " + items.get(itemIndex - 1).getName());
            }
        }

        int remaining = counts.get(itemIndex - 1) - quantityToTrade;
        npc.inventory.getItemCounts().set(itemIndex - 1, remaining);

        if (remaining == 0) {
            npc.inventory.getItemNames().remove(itemIndex - 1);
            npc.inventory.getItemCounts().remove(itemIndex - 1);
        }

        return items.get(itemIndex - 1);
    }

    public Item chooseItemToOffer() {
        System.out.println("\n-----------\nChoose an item to offer from your inventory:");

        inventory.printInventory();

        ArrayList<Item> items = inventory.getItemNames();
        ArrayList<Integer> counts = inventory.getItemCounts();

        if (items.isEmpty()) {
            System.out.println("\nYour inventory is empty.");
            return null;
        }

        int itemIndex = -1;
        while (itemIndex < 1 || itemIndex > items.size()) {
            System.out.print("\nEnter item number: ");
            itemIndex = sc.nextInt();
            if (itemIndex < 1 || itemIndex > items.size()) {
                System.out.println("\nInvalid choice. Please enter a number between 1 and " + items.size());
            }
        }

        int quantityToOffer = -1;
        while (quantityToOffer < 1 || quantityToOffer > counts.get(itemIndex - 1)) {
            System.out.print("\nHow much of it?: ");
            quantityToOffer = sc.nextInt();

            if (quantityToOffer < 1 || quantityToOffer > counts.get(itemIndex - 1)) {
                System.out.println("\nInvalid quantity. You only have " + counts.get(itemIndex - 1) + " of " + items.get(itemIndex - 1).getName());
            }
        }

        int remaining = counts.get(itemIndex - 1) - quantityToOffer;
        inventory.getItemCounts().set(itemIndex - 1, remaining);

        if (remaining == 0) {
            inventory.getItemNames().remove(itemIndex - 1);
            inventory.getItemCounts().remove(itemIndex - 1);
        }

        return items.get(itemIndex - 1);
    }

    @Override
    public void interact() {
        try {
            while (interact) {
                System.out.println("Available actions: \n1. Walk\n2. Sleep\n3. Exit interaction");
                System.out.print("Choose an action (1-3): ");

                int action = sc.nextInt();
                sc.nextLine();

                switch (action) {
                    case 1:
                        walk();
                        break;
                    case 2:
                        sleep();
                        break;
                    case 3:
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

    public void interactWithNPC(NPC npc) {
        while (interact) {
            System.out.println("\nYou are now interacting with " + npc.name + "!");
            System.out.println("\nAvailable actions: \n1. Talk\n2. Gift\n3. Trade\n4. Exit interaction");
            System.out.print("Choose an action (1-4): ");

            int npcAction = sc.nextInt();
            sc.nextLine();

            switch (npcAction) {
                case 1:
                    npc.talk(this);
                    break;
                case 2:
                    npc.gift(this);
                    break;
                case 3:
                    try {
                        handleTrade(npc);
                    } catch (InventoryFull | InvalidTrade e) {
                        System.out.println("\n" + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Exiting the interaction");
                    interact = false;
                    break;
                default:
                    System.out.println("Invalid action.");
                    break;
            }
        }
    }

    public void handleTrade(NPC npc) throws InventoryFull, InvalidTrade {
        ArrayList<Item> npcItems = npc.inventory.getItemNames();
        ArrayList<Integer> npcCounts = npc.inventory.getItemCounts();

        if (npcItems.isEmpty()) {
            throw new InvalidTrade("\n" + npc.name + " has no items to trade.");
        }

        try {
            System.out.println("\n-----------");
            System.out.println("Choose an item to trade from " + npc.name + "'s inventory:");
            npc.inventory.printInventory();

            System.out.print("\nEnter the item number: ");
            int npcItemIndex = sc.nextInt() - 1;
            sc.nextLine();

            if (npcItemIndex < 0 || npcItemIndex >= npcItems.size() || npcItemIndex >= npcCounts.size()) {
                throw new InvalidTrade("\nInvalid item selection. Please choose a valid item.");
            }

            Item npcItem = npcItems.get(npcItemIndex);
            System.out.print("How much of it?: ");
            int npcQuantity = sc.nextInt();
            sc.nextLine();

            if (npcQuantity <= 0 || npcQuantity > npcCounts.get(npcItemIndex)) {
                throw new InvalidTrade("\nInvalid quantity. Please select a valid amount.");
            }

            int updatedNpcCount = npcCounts.get(npcItemIndex) - npcQuantity;
            npc.inventory.updateItemCount(npcItem, updatedNpcCount);

            System.out.println("\n-----------");
            System.out.println("Choose an item to offer from your inventory:");
            inventory.printInventory();

            ArrayList<Item> playerItems = inventory.getItemNames();
            ArrayList<Integer> playerCounts = inventory.getItemCounts();

            if (playerItems.isEmpty() || playerCounts.isEmpty()) {
                npc.inventory.addToInventory(npcItem, npcQuantity);
                throw new InvalidTrade("\nYour inventory is empty. Trade is not possible.");
            }

            System.out.print("\nEnter the item number: ");
            int playerItemIndex = sc.nextInt() - 1;
            sc.nextLine();

            if (playerItemIndex < 0 || playerItemIndex >= playerItems.size() || playerItemIndex >= playerCounts.size()) {
                npc.inventory.addToInventory(npcItem, npcQuantity); 
                throw new InvalidTrade("\nInvalid item selection. Please check the trade requirements.");
            }

            Item playerItem = playerItems.get(playerItemIndex);
            System.out.print("How much of it?: ");
            int playerQuantity = sc.nextInt();
            sc.nextLine();

            if (playerQuantity <= 0 || playerQuantity > playerCounts.get(playerItemIndex)) {
                npc.inventory.addToInventory(npcItem, npcQuantity);
                throw new InvalidTrade("\nInvalid quantity. Please check the trade requirements.");
            }

            int updatedPlayerCount = playerCounts.get(playerItemIndex) - playerQuantity;
            inventory.updateItemCount(playerItem, updatedPlayerCount);

            String npcRarity = npcItem.getRarity();
            String playerRarity = playerItem.getRarity();

            if (npcRarity.equalsIgnoreCase(playerRarity)) {
                if (npcQuantity != playerQuantity) {
                    npc.inventory.updateItemCount(npcItem, npcCounts.get(npcItemIndex));
                    inventory.updateItemCount(playerItem, playerCounts.get(playerItemIndex));
                    throw new InvalidTrade("\nTrade failed. Quantities must match when trading items of the same rarity.");
                }
            }

            npc.inventory.updateItemCount(playerItem, playerQuantity);
            inventory.updateItemCount(npcItem, npcQuantity);

            System.out.println("\nTrade successful!");
            System.out.println("You traded " + playerQuantity + " " + playerItem.getName() + " for " + npcQuantity + " " + npcItem.getName() + " with " + npc.name + ".");
            npc.relationship += 1;
            System.out.println(npc.name + "'s relationship increased to " + npc.getRelationship());

        } catch (InvalidTrade e) {
            System.out.println(e.getMessage());
            System.out.println("\nPlease type 'OK' to exit the trade.");
            String input = sc.nextLine();
            while (!input.equalsIgnoreCase("OK")) {
                System.out.println("\nInvalid input. Please type 'OK' to exit the trade.");
                input = sc.nextLine();
            }
        } catch (InventoryFull e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void addLoveInterest(NPC npc) {
        if (!loveInterests.contains(npc)) {
            loveInterests.add(npc);
            System.out.println("\n" + npc.name + " is now a romantic partner option.");
        }
    }

    public void chooseRomanticPartner() {
        if (loveInterests.isEmpty()) {
            System.out.println("\nYou don't have any love interests yet!");
            return;
        }

        System.out.println("\nChoose your romantic partner from your love interests:");
        for (int i = 0; i < loveInterests.size(); i++) {
            System.out.println((i + 1) + ". " + loveInterests.get(i).name);
        }

        System.out.print("> ");
        try {
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice < 1 || choice > loveInterests.size()) {
                System.out.println("\nInvalid choice. Please select a number between 1 and " + loveInterests.size() + ".");
            } else {
                String chosenPartner = loveInterests.get(choice - 1).name;
                System.out.println("\nYou have chosen " + chosenPartner + " as your romantic partner.");
            }
        } catch (Exception e) {
            System.out.println("\nInvalid input. Please enter a number.");
            sc.nextLine();
        }
    }
 
    public void walk() {
        System.out.println("\nAvailable places to walk to:");
        for (Structure place : places) {
            System.out.println("- " + place.getName());
        }

        System.out.print("\nEnter the name of the place to walk to: ");
        String chosenPlace = sc.nextLine();
        
        Structure destination = null;
        for (Structure place : places) {
            if (place.getName().equalsIgnoreCase(chosenPlace)) {
                destination = place;
                break;
            }
        }

        if (destination != null) {
            destinationX = destination.getX();
            destinationY = destination.getY();
            System.out.println("\n" + name + " is walking to " + chosenPlace + " at coordinates (" + destinationX + "," + destinationY + ").");
        } else {
            System.out.println("\nPlace not found!");
        }
    }

    public void sleep() {
        System.out.print("\nEnter number of hours to sleep: ");
        int sleepTime = sc.nextInt();

        System.out.println("\n" + name + " is sleeping for " + sleepTime + " hours.");

        if (sleepTime >= 8) { //assuming 8 hours is enough for full recovery
            energy = maxEnergy;
        } 
        
        else {
            //restore energy proportional to sleep time (e.g., 10% per hour)
            energy += (maxEnergy * sleepTime / 8);

            if (energy > maxEnergy) {
                energy = maxEnergy;
            }
        }
    
        System.out.println("\n" + name + "'s energy level is now: " + energy);
    }
}
