package q2aa2_pho_asuncion.bosi.manuel;

public class NPC extends Character {
    private int talkCount = 0;
    
    public NPC(String name, String species, int relationship, String dialogue, double[] location, boolean inARelationship, InventoryManager inventory) {
        super(name, species, relationship, dialogue, inARelationship, inventory);
        this.relationship = relationship;
        this.inventory = new InventoryManager();
    }
    
    public InventoryManager getInventory() {
        return inventory;
    }
    
    private void checkRS(Player player) {
        if (relationship >= 15) {
            player.addLoveInterest(this);
        } else if (relationship >= 5) {
            System.out.println("\n" + name + " is now your friend!");
        }
    }
    
    public void talk(Player player) {
        talkCount++;
        
        if (talkCount % 3 == 0) {
            relationship += 1;
        }
        
        System.out.println("\n" + player.getName() + ": " + player.getDialogue());
        System.out.println(name + ": " + dialogue);
        System.out.println("Your relationship with " + name + " is now: " + relationship);
        checkRS(player);
    }
    
    public void gift(Player player) {
        relationship += 3;
        System.out.println("\n" + name + ": Thank you for the gift! Relationship +3");
        System.out.println("Your relationship with " + name + " is now: " + relationship);
        checkRS(player);
    }
}
