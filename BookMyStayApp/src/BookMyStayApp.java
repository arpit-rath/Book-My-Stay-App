import java.util.*;

// Custom Exception
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean isActive;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isActive = true;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
    public boolean isActive() { return isActive; }

    public void cancel() { this.isActive = false; }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType +
                " | Room: " + roomId + " | " + (isActive ? "ACTIVE" : "CANCELLED");
    }
}

// Booking History
class BookingHistory {
    private List<Reservation> reservations = new ArrayList<>();

    public void add(Reservation r) {
        reservations.add(r);
    }

    public Reservation findById(String id) {
        for (Reservation r : reservations) {
            if (r.getReservationId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public List<Reservation> getAll() {
        return reservations;
    }
}

// Booking Service (with allocation)
class BookingService {
    private Map<String, Integer> inventory;
    private Map<String, Stack<String>> availableRooms;
    private BookingHistory history;

    public BookingService(Map<String, Integer> inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        this.availableRooms = new HashMap<>();

        // Initialize room IDs
        for (String type : inventory.keySet()) {
            Stack<String> stack = new Stack<>();
            for (int i = inventory.get(type); i >= 1; i--) {
                stack.push(type + "-R" + i);
            }
            availableRooms.put(type, stack);
        }
    }

    public Reservation book(String id, String name, String type) throws BookingException {
        if (!inventory.containsKey(type)) {
            throw new BookingException("Invalid room type");
        }

        if (inventory.get(type) <= 0) {
            throw new BookingException("No rooms available");
        }

        // Allocate room (LIFO)
        String roomId = availableRooms.get(type).pop();
        inventory.put(type, inventory.get(type) - 1);

        Reservation r = new Reservation(id, name, type, roomId);
        history.add(r);

        return r;
    }
}

// Cancellation Service
class CancellationService {
    private Map<String, Integer> inventory;
    private Map<String, Stack<String>> availableRooms;
    private BookingHistory history;

    public CancellationService(Map<String, Integer> inventory,
                               Map<String, Stack<String>> availableRooms,
                               BookingHistory history) {
        this.inventory = inventory;
        this.availableRooms = availableRooms;
        this.history = history;
    }

    public void cancel(String reservationId) throws BookingException {

        // Validate existence
        Reservation r = history.findById(reservationId);
        if (r == null) {
            throw new BookingException("Reservation not found");
        }

        // Validate already cancelled
        if (!r.isActive()) {
            throw new BookingException("Reservation already cancelled");
        }

        // --- Controlled rollback ---

        // 1. Release room back (Stack LIFO)
        availableRooms.get(r.getRoomType()).push(r.getRoomId());

        // 2. Restore inventory
        inventory.put(r.getRoomType(), inventory.get(r.getRoomType()) + 1);

        // 3. Mark as cancelled
        r.cancel();
    }
}

// Main class
public class Main {
    public static void main(String[] args) {

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        BookingHistory history = new BookingHistory();
        BookingService bookingService = new BookingService(inventory, history);

        try {
            // Book rooms
            Reservation r1 = bookingService.book("RES301", "Arpit", "Deluxe");
            Reservation r2 = bookingService.book("RES302", "Rahul", "Suite");

            System.out.println("Booked: " + r1);
            System.out.println("Booked: " + r2);

            // Cancellation service
            CancellationService cancelService = new CancellationService(
                    inventory,
                    getAvailableRooms(bookingService),
                    history
            );

            // Cancel booking
            cancelService.cancel("RES301");
            System.out.println("\nCancelled RES301");

            // Try invalid cancellation
            cancelService.cancel("RES301"); // already cancelled

        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Final state
        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("\nBooking History:");
        for (Reservation r : history.getAll()) {
            System.out.println(r);
        }
    }

    // Helper to access private field (for simplicity in single file)
    public static Map<String, Stack<String>> getAvailableRooms(BookingService bs) {
        try {
            java.lang.reflect.Field field = BookingService.class.getDeclaredField("availableRooms");
            field.setAccessible(true);
            return (Map<String, Stack<String>>) field.get(bs);
        } catch (Exception e) {
            return null;
        }
    }
}