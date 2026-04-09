import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double basePrice;

    public Reservation(String reservationId, String guestName, String roomType, double basePrice) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.basePrice = basePrice;
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

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType + " | ₹" + basePrice;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Get all reservations (read-only copy)
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}

// Booking Report Service
class BookingReportService {

    // Display all bookings
    public void showAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        System.out.println("\n--- Booking Summary ---");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            totalRevenue += r.getBasePrice();

            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);

        System.out.println("Room Type Distribution:");
        for (String type : roomTypeCount.keySet()) {
            System.out.println("- " + type + ": " + roomTypeCount.get(type));
        }
    }
}

// Main class (single compile)
public class Main {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("RES101", "Arpit", "Deluxe", 3000);
        Reservation r2 = new Reservation("RES102", "Rahul", "Suite", 5000);
        Reservation r3 = new Reservation("RES103", "Sneha", "Deluxe", 3000);

        // Add to history (after confirmation)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views booking history
        List<Reservation> storedReservations = history.getAllReservations();
        reportService.showAllBookings(storedReservations);

        // Admin generates report
        reportService.generateSummary(storedReservations);
    }
}