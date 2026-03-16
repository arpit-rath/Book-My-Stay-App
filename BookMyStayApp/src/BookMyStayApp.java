import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Room class representing common room properties.
 */
abstract class Room {
    protected String type;
    protected double price;
    protected String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

/* Concrete Room Types */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 3000, "1 Bed, Free WiFi");
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 5000, "2 Beds, Free WiFi, TV");
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 9000, "King Bed, Living Area, Premium WiFi");
    }
}

/**
 * Centralized Room Inventory using HashMap
 */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // Example unavailable room
    }

    // Read-only access to inventory
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

/**
 * Search Service – provides read-only room search functionality
 */
class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            // Defensive check: only show rooms with availability > 0
            if (available > 0) {
                System.out.println("Room Type: " + room.getType());
                System.out.println("Price: ₹" + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }
}

/**
 * Application Entry Point
 */
public class HotelSearchApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room domain objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Guest searches for available rooms
        searchService.searchAvailableRooms(rooms);
    }
}