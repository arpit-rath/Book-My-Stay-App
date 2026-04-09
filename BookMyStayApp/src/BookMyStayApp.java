import java.util.*;

// Custom Exception for invalid booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Validator class (Fail-Fast)
class InvalidBookingValidator {

    private Set<String> validRoomTypes;
    private Map<String, Integer> inventory;

    public InvalidBookingValidator(Map<String, Integer> inventory) {
        this.inventory = inventory;
        validRoomTypes = new HashSet<>(inventory.keySet());
    }

    public void validate(String reservationId, String guestName, String roomType)
            throws InvalidBookingException {

        // Validate empty inputs
        if (reservationId == null || reservationId.isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty");
        }

        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Validate room type
        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // Validate availability
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }
}

// Booking Service (uses validation)
class BookingService {

    private Map<String, Integer> inventory;

    public BookingService(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public Reservation createBooking(String id, String name, String type)
            throws InvalidBookingException {

        // Validate first (Fail-Fast)
        InvalidBookingValidator validator = new InvalidBookingValidator(inventory);
        validator.validate(id, name, type);

        // Update inventory safely
        inventory.put(type, inventory.get(type) - 1);

        return new Reservation(id, name, type);
    }
}

// Main class
public class Main {
    public static void main(String[] args) {

        // Initial inventory
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        BookingService bookingService = new BookingService(inventory);

        // Test cases
        String[][] testInputs = {
                {"RES201", "Arpit", "Deluxe"},
                {"RES202", "Rahul", "Suite"},
                {"RES203", "Sneha", "Suite"},   // Will fail (no rooms left)
                {"", "Test", "Deluxe"},         // Invalid ID
                {"RES204", "", "Deluxe"},       // Invalid name
                {"RES205", "Amit", "Premium"}   // Invalid type
        };

        for (String[] input : testInputs) {
            try {
                Reservation r = bookingService.createBooking(
                        input[0], input[1], input[2]);

                System.out.println("Booking Successful: " + r);

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        // System continues running safely
        System.out.println("\nFinal Inventory: " + inventory);
    }
}