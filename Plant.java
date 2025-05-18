package q2aa2_pho_asuncion.bosi.manuel;

public class Plant extends Item {
    private int growthStage;
    private int growTime;
    private boolean isMature;
    private boolean isWatered;
    
    public Plant(String name, String rarity, int growthStage, int growTime, boolean isMature, boolean isWatered) {
        super(name, true, false, false, false, true, rarity);
        this.isPlant = true;
        this.growthStage = growthStage;
        this.growTime = growTime;
        this.isMature = isMature;
        this.isWatered = isWatered;
    }
    
    public int growthStage() {
        return growthStage;
    }

    public void setGrowthStage(int growthStage) {
        this.growthStage = growthStage;
    }
    
    public int growTime() {
        return growTime;
    }

    public void setGrowTime(int growTime) {
        this.growTime = growTime;
    }
    
    public boolean isMature() { 
        return isMature; 
    }
    
    public void setMature(boolean mature) { 
        isMature = mature;
    }
    
    public boolean isWatered() { 
        return isWatered; 
    }
    
    public void setWatered(boolean watered) { 
        this.isWatered = watered; 
    }
    
    public boolean checkMature() {
        if (isMature = true) {
            System.out.println("\n" + getName() + " is fully mature.");
        } else {
            System.out.println("\n" + getName() +  "is not yet mature. Growth stage: " + growthStage());
        }
        
        return false;
    }

    public boolean checkWatered() {
        if (isWatered = true) {
            System.out.println("\n" + getName() + " has been watered.");
        } else {
            System.out.println("\n" + getName() + " has not been watered");
        }
        
        return false;
    }
    
    @Override
    public void interact() {
        while (interact) {
            System.out.println("\nYou are now interacting with " + getName() + "!");
            System.out.println("\nAvailable actions: \n1. Grow\n2. Water\n3. Harvest\n4. Check maturity\n5. Check watered\n6. Exit interaction");
            System.out.print("Choose an action (1-6): ");
            
            int action = sc.nextInt();
            sc.nextLine();

            switch (action) {
                case 1:
                    grow();
                    break;
                case 2:
                    water();
                    break;
                case 3:
                    try {
                        harvest();
                    } catch (InventoryFull e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    checkMature();
                    break;
                case 5:
                    checkWatered();
                    break;
                case 6:
                    System.out.println("\nExiting the interaction.");
                    interact = false;
                    break;
                default:
                    System.out.println("\nInvalid action.");
            }
        }
    }
        
    public void grow() {
        if (growthStage < 10) {
            growthStage++;
            System.out.println("\n" + getName() + "'s growth stage is now " + growthStage() + "/10.");
        } else {
            System.out.println("\n" + getName() + " is fully grown.");
            isMature = true;
        }
    }
    
    public void harvest() throws InventoryFull {
        if (growthStage == 10) {
            if (inventory.addToInventory(this, 1)) {
                System.out.println("\n" + getName() + " has been harvested and added to the inventory.");
            } else {
                throw new InventoryFull("\nInventory is full. Cannot harvest " + getName() + ".");
            }
        } else if (growthStage < 10) {
            System.out.println("\n" + name + " is not ready for harvest.");
        } else {
            System.out.println("\nPlant has died. Sorry.");
            die();
        }
    }

    public void water() {
        isWatered = true;
        System.out.println("\n" + getName() + " has been watered.");
    }
    
    public void die() {
        growthStage = 0;
        isMature = false;
        isWatered = false;
    }
}

