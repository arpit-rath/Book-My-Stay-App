import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Wrapper class for full system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> reservations;

    public SystemState(Map<String, Integer> inventory, List<Reservation> reservations) {
        this.inventory = inventory;
        this.reservations = reservations;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("State saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("State loaded successfully.");
            return state;

        } catch (Exception e) {
            System.out.println("Error loading state. Starting fresh.");
            return null;
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {

        Map<String, Integer> inventory;
        List<Reservation> reservations;

        // STEP 1: Load previous state (Recovery)
        SystemState loadedState = PersistenceService.load();

        if (loadedState != null) {
            inventory = loadedState.inventory;
            reservations = loadedState.reservations;
        } else {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);

            reservations = new ArrayList<>();
        }

        // Display current state
        System.out.println("\nCurrent Inventory: " + inventory);
        System.out.println("Booking History:");
        for (Reservation r : reservations) {
            System.out.println(r);
        }

        // STEP 2: Simulate new booking
        if (inventory.get("Deluxe") > 0) {
            Reservation newBooking =
                    new Reservation("RES401", "Arpit", "Deluxe");

            reservations.add(newBooking);
            inventory.put("Deluxe", inventory.get("Deluxe") - 1);

            System.out.println("\nNew Booking Added: " + newBooking);
        }

        // STEP 3: Save state before shutdown
        SystemState newState = new SystemState(inventory, reservations);
        PersistenceService.save(newState);

        System.out.println("\nFinal Inventory: " + inventory);
    }
}