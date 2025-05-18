package q2aa2_pho_asuncion.bosi.manuel;
import java.util.ArrayList;

public class House extends Structure {
    public House(String name, ArrayList<Character> inhabitants, int numberOfInhabitants, String purpose, double x, double y, double plotValue, ArrayList<Item> materials, int numberOfMaterials, ArrayList<String> rooms) {
        super(name, inhabitants, numberOfInhabitants, purpose, x, y, plotValue, materials, numberOfMaterials, rooms);
        this.materials = materials;
        this.numberOfMaterials = numberOfMaterials;
    }

    public void repair() {
        System.out.println("\nRepairing house at (" + getX() + ", " + getY() + ") using materials.");
    }

    @Override
    public void interact() {
        try {
            while (interact) {
                System.out.println("\nAvailable actions for the house: \n1. Build\n2. Upgrade\n3. Add Room\n4. Repair\n5. Exit interaction");
                System.out.print("Choose an action (1-5): ");

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
                        repair();
                        break;
                    case 5:
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