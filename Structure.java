package q2aa2_pho_asuncion.bosi.manuel;
import java.util.ArrayList;
import java.util.Scanner;

public class Structure implements Interactable {
    protected String name;
    protected ArrayList<Character> inhabitants;
    protected int numberOfInhabitants;
    protected String purpose;
    protected double x, y;
    protected double plotValue;
    protected ArrayList<Item> materials;
    protected int numberOfMaterials;
    protected ArrayList<String> rooms;
    protected static final Scanner sc = new Scanner(System.in);
    
    boolean interact = true;
    
    public Structure(String name, ArrayList<Character> inhabitants, int numberOfInhabitants, String purpose, double x, double y, double plotValue, ArrayList<Item> materials, int numberOfMaterials, ArrayList<String> rooms) {
        this.name = name;
        this.inhabitants = inhabitants;
        this.numberOfInhabitants = numberOfInhabitants;
        this.purpose = purpose;
        this.x = x;
        this.y = y;
        this.plotValue = plotValue;
        this.materials = materials;
        this.numberOfMaterials = numberOfMaterials;
        this.rooms = rooms;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Character> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(ArrayList<Character> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public int getNumberOfInhabitants() {
        return numberOfInhabitants;
    }

    public void setNumberOfInhabitants(int numberOfInhabitants) {
        this.numberOfInhabitants = numberOfInhabitants;
    }
    
    public void addInhabitant(Character inhabitant) {
        inhabitants.add(inhabitant);
        numberOfInhabitants++;
    }

    public void removeInhabitant(Character inhabitant) {
        inhabitants.remove(inhabitant);
        numberOfInhabitants--;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public double getPlotValue() {
        return plotValue;
    }
    
    public void setPlotValue(double plotValue) {
        this.plotValue = plotValue;
    }
    
    public ArrayList<Item> getMaterials() {
        return materials;
    }
    
    public void setMaterials(ArrayList<Item> materials) {
        this.materials = materials;
    }
    
    public void addMaterial(Item material) {
        numberOfMaterials++;
        materials.add(material);
    }
    
    public int getNumberOfMaterials() {
        return numberOfMaterials;
    }
    
    public void setNumberOfMaterials(int materials) {
        this.numberOfMaterials = materials;
    }
    
    public ArrayList<String> getRooms() {
        return rooms;
    }
    
    public void setRooms(ArrayList<String> rooms) {
        this.rooms = rooms;
    }
    
    public boolean checkIfOwned() {
        return false;
    }
    
    public void build() {
        Player player = new Player("", "", 0, "", new double[]{0, 0}, false, 0, new InventoryManager(), 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        System.out.println("\nYou have encountered an empty plot of land.");
        System.out.println("Would you like to build? (yes/no): ");
        String decision = sc.nextLine();

        if (!decision.equalsIgnoreCase("yes")) {
            System.out.println("\nExiting build mode.");
            return;
        }

        while (true) {
            System.out.println("\nAvailable actions: \n1. Add room\n2. Exit build mode");
            System.out.print("Choose an action: ");
            int action = sc.nextInt();
            sc.nextLine();

            if (action == 2) {
                System.out.println("\nExiting build mode.");
                break;
            } else if (action == 1) {
                System.out.print("Enter the name of the room: ");
                String roomName = sc.nextLine();
                
                System.out.print("Enter the size of the room (square units): ");
                int roomSize = sc.nextInt();
                sc.nextLine();

                int requiredMaterials = roomSize;
                if (numberOfMaterials < requiredMaterials) {
                    System.out.println("\nYou do not have enough materials to build this room. Required: " + requiredMaterials + ", Available: " + numberOfMaterials);
                    continue;
                } else {
                    numberOfMaterials =- requiredMaterials;
                
                    setNumberOfMaterials(numberOfMaterials);
                
                    rooms.add(roomName);
                    System.out.println("\n" + roomName + " added with size " + roomSize + " square units.");
                    System.out.println("Remaining materials: " + numberOfMaterials);
                }
            } else {
                System.out.println("\nInvalid action.");
            }
        }
    }
    
   public void upgrade() {
        System.out.println("\nUpgrading " + name + " at coordinates (" + x + ", " + y + ")...");
    }
    
    public void addRoom(String roomName) {
        rooms.add(roomName);
        System.out.println("\nRoom '" + roomName + "' has been added.");
    }
    
    @Override
    public void interact() {
        try {
            while (interact) {
                System.out.println("\nAvailable actions for the house: \n1. Build\n2. Upgrade\n3. Add Room\n4. Exit interaction");
                System.out.print("Choose an action (1-4): ");

                int action = sc.nextInt();
                sc.nextLine();

                switch (action) {
                    case 1:
                        build();
                        break;
                    case 2:
                        upgrade();
                        break;
                    case 3:
                        System.out.print("\nEnter the name of the room to add: ");
                        String roomName = sc.nextLine();
                        addRoom(roomName);
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
