import java.util.*;

/**
 * Reservation represents a booking request from a guest.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * InventoryService manages room availability.
 */
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

/**
 * BookingService processes booking requests and allocates rooms.
 */
class BookingService {

    private Queue<Reservation> requestQueue;
    private InventoryService inventoryService;

    // Track allocated room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track room IDs per room type
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventoryService) {
        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for " + reservation.getGuestName());

            int available = inventoryService.getAvailability(roomType);

            if (available > 0) {

                // Generate unique room ID
                String roomId = roomType.replace(" ", "") + "-" + roomCounter++;

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    roomAllocations
                            .computeIfAbsent(roomType, k -> new HashSet<>())
                            .add(roomId);

                    // Update inventory
                    inventoryService.decrementRoom(roomType);

                    System.out.println("Booking Confirmed!");
                    System.out.println("Guest: " + reservation.getGuestName());
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Assigned Room ID: " + roomId);
                }

            } else {
                System.out.println("Booking Failed - No rooms available for " + roomType);
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/**
 * Application Entry Point
 */
public class BookingAllocationApp {

    public static void main(String[] args) {

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Arpit", "Single Room"));
        bookingQueue.add(new Reservation("Rahul", "Double Room"));
        bookingQueue.add(new Reservation("Priya", "Suite Room"));
        bookingQueue.add(new Reservation("Amit", "Single Room"));

        InventoryService inventoryService = new InventoryService();

        BookingService bookingService = new BookingService(bookingQueue, inventoryService);

        bookingService.processBookings();

        bookingService.displayAllocations();

        inventoryService.displayInventory();
    }
}