import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.util.*;

//uC:1
class HotelBookingApp {

    public static void main(String[] args) {

        // Print welcome message and application details
        System.out.println("=================================");
        System.out.println(" Welcome to the Hotel Booking System ");
        System.out.println(" Application Name: Hotel Booking System");
        System.out.println(" Version: 1.0");
        System.out.println("=================================");

        // Program terminates after printing the message
    }
}

//UC:2
abstract class Room {
    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 3000);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 5000);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 9000);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

class BookMyStayApp {

    public static void main(String[] args) {

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display room details
        System.out.println("Hotel Room Availability\n");

        System.out.println(single.getRoomType());
        System.out.println("Beds: " + single.getBeds());
        System.out.println("Size: " + single.getSize() + " sq ft");
        System.out.println("Price: ₹" + single.getPrice());
        System.out.println("Available: " + singleAvailable + "\n");

        System.out.println(doubleRoom.getRoomType());
        System.out.println("Beds: " + doubleRoom.getBeds());
        System.out.println("Size: " + doubleRoom.getSize() + " sq ft");
        System.out.println("Price: ₹" + doubleRoom.getPrice());
        System.out.println("Available: " + doubleAvailable + "\n");

        System.out.println(suite.getRoomType());
        System.out.println("Beds: " + suite.getBeds());
        System.out.println("Size: " + suite.getSize() + " sq ft");
        System.out.println("Price: ₹" + suite.getPrice());
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Application Terminated.");
    }
}

//UC:3
class RoomInventoryUC3 {

    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventoryUC3() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Retrieve availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability in a controlled way
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + change);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* Application Entry Point */
class HotelInventoryApp {

    public static void main(String[] args) {

        // Initialize inventory system
        RoomInventoryUC3 inventory = new RoomInventoryUC3();

        // Display current inventory
        inventory.displayInventory();

        // Example update (booking a room)
        inventory.updateAvailability("Single Room", -1);

        System.out.println("\nAfter Booking One Single Room:");
        inventory.displayInventory();
    }
}

//UC:4
abstract class RoomUC4 {

    protected String type;
    protected double price;
    protected String amenities;

    public RoomUC4(String type, double price, String amenities) {
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

class SingleRoomUC4 extends RoomUC4 {

    public SingleRoomUC4() {
        super("Single Room", 3000, "1 Bed, Free WiFi");
    }
}

class DoubleRoomUC4 extends RoomUC4 {

    public DoubleRoomUC4() {
        super("Double Room", 5000, "2 Beds, Free WiFi, TV");
    }
}

class SuiteRoomUC4 extends RoomUC4 {

    public SuiteRoomUC4() {
        super("Suite Room", 9000, "King Bed, Living Area, Premium WiFi");
    }
}

class RoomInventoryUC4 {

    private HashMap<String, Integer> inventory;

    public RoomInventoryUC4() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class SearchServiceUC4 {

    private RoomInventoryUC4 inventory;

    public SearchServiceUC4(RoomInventoryUC4 inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(RoomUC4[] rooms) {

        System.out.println("Available Rooms:\n");

        for (RoomUC4 room : rooms) {

            int available = inventory.getAvailability(room.getType());

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

class HotelSearchApp {

    public static void main(String[] args) {

        RoomInventoryUC4 inventory = new RoomInventoryUC4();

        RoomUC4[] rooms = {
                new SingleRoomUC4(),
                new DoubleRoomUC4(),
                new SuiteRoomUC4()
        };

        SearchServiceUC4 searchService = new SearchServiceUC4(inventory);

        searchService.searchAvailableRooms(rooms);
    }
}

//UC:5
/**
 * Reservation class represents a guest booking request.
 */
class ReservationUC5 {

    private String guestName;
    private String roomType;

    public ReservationUC5(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Requested Room: " + roomType;
    }
}

/**
 * BookingRequestQueue manages incoming booking requests.
 * Requests are stored in FIFO order.
 */
class BookingRequestQueue {

    private Queue<ReservationUC5> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a new booking request
    public void addRequest(ReservationUC5 reservation) {
        requestQueue.add(reservation);
        System.out.println("Request added: " + reservation);
    }

    // Display all pending requests
    public void displayRequests() {

        System.out.println("\nCurrent Booking Queue:");

        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (ReservationUC5 r : requestQueue) {
            System.out.println(r);
        }
    }

    // Peek at the next request without removing it
    public ReservationUC5 peekNextRequest() {
        return requestQueue.peek();
    }
}

/**
 * Application Entry Point
 */
class BookingQueueApp {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new ReservationUC5("Arpit", "Single Room"));
        bookingQueue.addRequest(new ReservationUC5("Rahul", "Double Room"));
        bookingQueue.addRequest(new ReservationUC5("Priya", "Suite Room"));

        bookingQueue.displayRequests();

        System.out.println("\nNext request to process: "
                + bookingQueue.peekNextRequest());

        System.out.println("\nRequests are waiting for allocation processing.");
    }
}

//UC:6
/**
 * Reservation represents a booking request from a guest.
 */
class ReservationUC6 {

    private String guestName;
    private String roomType;

    public ReservationUC6(String guestName, String roomType) {
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
class InventoryServiceUC6 {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryServiceUC6() {

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
class BookingServiceUC6 {

    private Queue<ReservationUC6> requestQueue;
    private InventoryServiceUC6 inventoryService;

    // Track allocated room IDs
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Track room IDs per room type
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    private int roomCounter = 1;

    public BookingServiceUC6(
            Queue<ReservationUC6> requestQueue,
            InventoryServiceUC6 inventoryService) {

        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            ReservationUC6 reservation = requestQueue.poll();

            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for "
                    + reservation.getGuestName());

            int available = inventoryService.getAvailability(roomType);

            if (available > 0) {

                String roomId =
                        roomType.replace(" ", "") + "-" + roomCounter++;

                allocatedRoomIds.add(roomId);

                roomAllocations
                        .computeIfAbsent(roomType,
                                k -> new HashSet<>())
                        .add(roomId);

                inventoryService.decrementRoom(roomType);

                System.out.println("Booking Confirmed!");
                System.out.println("Guest: "
                        + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: "
                        + roomId);

            } else {

                System.out.println(
                        "Booking Failed - No rooms available for "
                                + roomType);
            }
        }
    }

    public void displayAllocations() {

        System.out.println("\nRoom Allocations:");

        for (Map.Entry<String, Set<String>> entry
                : roomAllocations.entrySet()) {

            System.out.println(entry.getKey()
                    + " -> "
                    + entry.getValue());
        }
    }
}

/**
 * Application Entry Point
 */
class BookingAllocationApp {

    public static void main(String[] args) {

        Queue<ReservationUC6> bookingQueue =
                new LinkedList<>();

        bookingQueue.add(
                new ReservationUC6("Arpit", "Single Room"));

        bookingQueue.add(
                new ReservationUC6("Rahul", "Double Room"));

        bookingQueue.add(
                new ReservationUC6("Priya", "Suite Room"));

        bookingQueue.add(
                new ReservationUC6("Amit", "Single Room"));

        InventoryServiceUC6 inventoryService =
                new InventoryServiceUC6();

        BookingServiceUC6 bookingService =
                new BookingServiceUC6(
                        bookingQueue,
                        inventoryService);

        bookingService.processBookings();

        bookingService.displayAllocations();

        inventoryService.displayInventory();
    }
}

//UC:7
// Add-On Service class
class AddOnService {

    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {

        return serviceMap.getOrDefault(
                reservationId,
                new ArrayList<>()
        );
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        for (AddOnService service : getServices(reservationId)) {
            total += service.getPrice();
        }

        return total;
    }
}

// Application Entry Point
class AddOnServiceApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES123";

        AddOnService breakfast =
                new AddOnService("Breakfast", 500);

        AddOnService wifi =
                new AddOnService("WiFi", 200);

        AddOnService spa =
                new AddOnService("Spa", 1000);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, wifi);
        manager.addService(reservationId, spa);

        System.out.println("Services for Reservation: " + reservationId);

        for (AddOnService service :
                manager.getServices(reservationId)) {

            System.out.println(
                    "- "
                            + service.getName()
                            + " : ₹"
                            + service.getPrice()
            );
        }

        System.out.println(
                "Total Add-On Cost: ₹"
                        + manager.calculateTotalCost(reservationId)
        );
    }
}

//UC:8
// Reservation class
class ReservationUC8 {

    private String reservationId;
    private String guestName;
    private String roomType;
    private double basePrice;

    public ReservationUC8(String reservationId, String guestName,
                          String roomType, double basePrice) {

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
        return reservationId + " | " + guestName + " | "
                + roomType + " | ₹" + basePrice;
    }
}

// Booking History
class BookingHistoryUC8 {

    private List<ReservationUC8> reservations;

    public BookingHistoryUC8() {
        reservations = new ArrayList<>();
    }

    public void addReservation(ReservationUC8 reservation) {
        reservations.add(reservation);
    }

    public List<ReservationUC8> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}

// Report Service
class BookingReportService {

    public void showAllBookings(List<ReservationUC8> reservations) {

        System.out.println("\n--- Booking History ---");

        for (ReservationUC8 r : reservations) {
            System.out.println(r);
        }
    }

    public void generateSummary(List<ReservationUC8> reservations) {

        System.out.println("\n--- Booking Summary ---");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (ReservationUC8 r : reservations) {

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
            System.out.println(type + " : " + roomTypeCount.get(type));
        }
    }
}

// Main Class
class BookingReportApp {

    public static void main(String[] args) {

        BookingHistoryUC8 history = new BookingHistoryUC8();
        BookingReportService report = new BookingReportService();

        history.addReservation(
                new ReservationUC8("RES101", "Arpit", "Deluxe", 3000));

        history.addReservation(
                new ReservationUC8("RES102", "Rahul", "Suite", 5000));

        history.addReservation(
                new ReservationUC8("RES103", "Sneha", "Deluxe", 3000));

        List<ReservationUC8> list = history.getAllReservations();

        report.showAllBookings(list);
        report.generateSummary(list);
    }
}

//UC:9
// Custom Exception
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
class ReservationUC9 {

    private String reservationId;
    private String guestName;
    private String roomType;

    public ReservationUC9(String reservationId,
                          String guestName,
                          String roomType) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | "
                + guestName + " | "
                + roomType;
    }
}

// Validator
class InvalidBookingValidator {

    private Set<String> validRoomTypes;
    private Map<String, Integer> inventory;

    public InvalidBookingValidator(Map<String, Integer> inventory) {

        this.inventory = inventory;
        validRoomTypes = new HashSet<>(inventory.keySet());
    }

    public void validate(String id,
                         String guest,
                         String room)
            throws InvalidBookingException {

        if (id == null || id.isEmpty()) {
            throw new InvalidBookingException(
                    "Reservation ID cannot be empty");
        }

        if (guest == null || guest.isEmpty()) {
            throw new InvalidBookingException(
                    "Guest name cannot be empty");
        }

        if (!validRoomTypes.contains(room)) {
            throw new InvalidBookingException(
                    "Invalid room type: " + room);
        }

        if (inventory.get(room) <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for: " + room);
        }
    }
}

// Booking Service
class BookingServiceUC9 {

    private Map<String, Integer> inventory;

    public BookingServiceUC9(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    public ReservationUC9 createBooking(
            String id,
            String guest,
            String room)
            throws InvalidBookingException {

        InvalidBookingValidator validator =
                new InvalidBookingValidator(inventory);

        validator.validate(id, guest, room);

        inventory.put(room, inventory.get(room) - 1);

        return new ReservationUC9(id, guest, room);
    }
}

// Main
class BookingValidationApp {

    public static void main(String[] args) {

        Map<String, Integer> inventory = new HashMap<>();

        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        BookingServiceUC9 bookingService =
                new BookingServiceUC9(inventory);

        String[][] testInputs = {

                {"RES201", "Arpit", "Deluxe"},
                {"RES202", "Rahul", "Suite"},
                {"RES203", "Sneha", "Suite"},
                {"", "Test", "Deluxe"},
                {"RES204", "", "Deluxe"},
                {"RES205", "Amit", "Premium"}
        };

        for (String[] input : testInputs) {

            try {

                ReservationUC9 reservation =
                        bookingService.createBooking(
                                input[0],
                                input[1],
                                input[2]);

                System.out.println(
                        "Booking Successful: "
                                + reservation);

            } catch (InvalidBookingException e) {

                System.out.println(
                        "Booking Failed: "
                                + e.getMessage());
            }
        }

        System.out.println("\nFinal Inventory: " + inventory);
    }
}

//UC:10
// Custom Exception
class BookingExceptionUC10 extends Exception {

    public BookingExceptionUC10(String message) {
        super(message);
    }
}

// Reservation Class
class ReservationUC10 {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public ReservationUC10(String reservationId,
                           String guestName,
                           String roomType,
                           String roomId) {

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    @Override
    public String toString() {

        return reservationId
                + " | "
                + guestName
                + " | "
                + roomType
                + " | Room: "
                + roomId
                + " | "
                + (active ? "ACTIVE" : "CANCELLED");
    }
}

// Booking History
class BookingHistoryUC10 {

    private List<ReservationUC10> reservations =
            new ArrayList<>();

    public void add(ReservationUC10 reservation) {
        reservations.add(reservation);
    }

    public ReservationUC10 findById(String id) {

        for (ReservationUC10 reservation : reservations) {

            if (reservation.getReservationId().equals(id)) {
                return reservation;
            }
        }

        return null;
    }

    public List<ReservationUC10> getAll() {
        return reservations;
    }
}

// Booking Service
class BookingServiceUC10 {

    private Map<String, Integer> inventory;
    private Map<String, Stack<String>> availableRooms;
    private BookingHistoryUC10 history;

    public BookingServiceUC10(
            Map<String, Integer> inventory,
            BookingHistoryUC10 history) {

        this.inventory = inventory;
        this.history = history;

        availableRooms = new HashMap<>();

        for (String type : inventory.keySet()) {

            Stack<String> rooms = new Stack<>();

            for (int i = inventory.get(type); i >= 1; i--) {
                rooms.push(type + "-R" + i);
            }

            availableRooms.put(type, rooms);
        }
    }

    public ReservationUC10 book(
            String id,
            String guest,
            String type)
            throws BookingExceptionUC10 {

        if (!inventory.containsKey(type))
            throw new BookingExceptionUC10("Invalid room type");

        if (inventory.get(type) <= 0)
            throw new BookingExceptionUC10("No rooms available");

        String roomId = availableRooms.get(type).pop();

        inventory.put(type, inventory.get(type) - 1);

        ReservationUC10 reservation =
                new ReservationUC10(
                        id,
                        guest,
                        type,
                        roomId);

        history.add(reservation);

        return reservation;
    }

    // Getter instead of Reflection
    public Map<String, Stack<String>> getAvailableRooms() {
        return availableRooms;
    }
}

// Cancellation Service
class CancellationServiceUC10 {

    private Map<String, Integer> inventory;
    private Map<String, Stack<String>> availableRooms;
    private BookingHistoryUC10 history;

    public CancellationServiceUC10(
            Map<String, Integer> inventory,
            Map<String, Stack<String>> availableRooms,
            BookingHistoryUC10 history) {

        this.inventory = inventory;
        this.availableRooms = availableRooms;
        this.history = history;
    }

    public void cancel(String reservationId)
            throws BookingExceptionUC10 {

        ReservationUC10 reservation =
                history.findById(reservationId);

        if (reservation == null)
            throw new BookingExceptionUC10(
                    "Reservation not found");

        if (!reservation.isActive())
            throw new BookingExceptionUC10(
                    "Reservation already cancelled");

        availableRooms.get(
                reservation.getRoomType())
                .push(reservation.getRoomId());

        inventory.put(
                reservation.getRoomType(),
                inventory.get(
                        reservation.getRoomType()) + 1);

        reservation.cancel();
    }
}

// Main Class
class BookingCancellationApp {

    public static void main(String[] args) {

        Map<String, Integer> inventory =
                new HashMap<>();

        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        BookingHistoryUC10 history =
                new BookingHistoryUC10();

        BookingServiceUC10 bookingService =
                new BookingServiceUC10(
                        inventory,
                        history);

        try {

            ReservationUC10 r1 =
                    bookingService.book(
                            "RES301",
                            "Arpit",
                            "Deluxe");

            ReservationUC10 r2 =
                    bookingService.book(
                            "RES302",
                            "Rahul",
                            "Suite");

            System.out.println("Booked: " + r1);
            System.out.println("Booked: " + r2);

            CancellationServiceUC10 cancellation =
                    new CancellationServiceUC10(
                            inventory,
                            bookingService.getAvailableRooms(),
                            history);

            cancellation.cancel("RES301");

            System.out.println(
                    "\nReservation RES301 Cancelled");

            cancellation.cancel("RES301");

        } catch (BookingExceptionUC10 e) {

            System.out.println(
                    "Error: " + e.getMessage());
        }

        System.out.println("\nFinal Inventory");

        System.out.println(inventory);

        System.out.println("\nBooking History");

        for (ReservationUC10 reservation :
                history.getAll()) {

            System.out.println(reservation);
        }
    }
}

//UC:11
// Booking Request
class BookingRequestUC11 {

    private String guestName;
    private String roomType;

    public BookingRequestUC11(String guestName, String roomType) {
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

// Shared Booking Processor
class BookingProcessorUC11 {

    private Map<String, Integer> inventory;
    private Queue<BookingRequestUC11> queue;

    public BookingProcessorUC11(
            Map<String, Integer> inventory,
            Queue<BookingRequestUC11> queue) {

        this.inventory = inventory;
        this.queue = queue;
    }

    // Critical Section
    public synchronized boolean processBooking() {

        if (queue.isEmpty()) {
            return false;
        }

        BookingRequestUC11 request = queue.poll();

        if (request == null) {
            return false;
        }

        String type = request.getRoomType();

        if (inventory.getOrDefault(type, 0) > 0) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " processing booking for "
                            + request.getGuestName());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            inventory.put(type,
                    inventory.get(type) - 1);

            System.out.println(
                    "SUCCESS : "
                            + request.getGuestName()
                            + " got "
                            + type);

        } else {

            System.out.println(
                    "FAILED : No rooms available for "
                            + request.getGuestName());
        }

        return true;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

// Worker Thread
class BookingWorkerUC11 extends Thread {

    private BookingProcessorUC11 processor;

    public BookingWorkerUC11(
            BookingProcessorUC11 processor) {

        this.processor = processor;
    }

    @Override
    public void run() {

        while (processor.processBooking()) {

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}

// Main Class
class ConcurrentBookingApp {

    public static void main(String[] args)
            throws InterruptedException {

        Map<String, Integer> inventory =
                new HashMap<>();

        inventory.put("Deluxe", 2);

        Queue<BookingRequestUC11> queue =
                new LinkedList<>();

        queue.add(
                new BookingRequestUC11(
                        "Arpit",
                        "Deluxe"));

        queue.add(
                new BookingRequestUC11(
                        "Rahul",
                        "Deluxe"));

        queue.add(
                new BookingRequestUC11(
                        "Sneha",
                        "Deluxe"));

        BookingProcessorUC11 processor =
                new BookingProcessorUC11(
                        inventory,
                        queue);

        Thread t1 =
                new BookingWorkerUC11(processor);

        Thread t2 =
                new BookingWorkerUC11(processor);

        Thread t3 =
                new BookingWorkerUC11(processor);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("\nFinal Inventory");

        for (Map.Entry<String, Integer> entry
                : processor.getInventory().entrySet()) {

            System.out.println(
                    entry.getKey()
                            + " : "
                            + entry.getValue());
        }

        System.out.println("\nProcessing Completed.");
    }
}

//UC:12
// Reservation (Serializable)
class ReservationUC12 implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public ReservationUC12(String reservationId,
                           String guestName,
                           String roomType) {

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

        return reservationId
                + " | "
                + guestName
                + " | "
                + roomType;
    }
}

// Wrapper Class
class SystemStateUC12 implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Integer> inventory;
    private List<ReservationUC12> reservations;

    public SystemStateUC12(
            Map<String, Integer> inventory,
            List<ReservationUC12> reservations) {

        this.inventory = inventory;
        this.reservations = reservations;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public List<ReservationUC12> getReservations() {
        return reservations;
    }
}

// Persistence Service
class PersistenceServiceUC12 {

    private static final String FILE_NAME =
            "system_state.ser";

    // Save State
    public static void save(SystemStateUC12 state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println(
                    "State saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error saving state: "
                            + e.getMessage());
        }
    }

    // Load State
    public static SystemStateUC12 load() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {

            System.out.println(
                    "No saved state found. Starting fresh.");

            return null;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new FileInputStream(FILE_NAME))) {

            SystemStateUC12 state =
                    (SystemStateUC12) in.readObject();

            System.out.println(
                    "State loaded successfully.");

            return state;

        } catch (IOException |
                 ClassNotFoundException e) {

            System.out.println(
                    "Error loading state. Starting fresh.");

            return null;
        }
    }
}

// Main Class
class PersistenceApp {

    public static void main(String[] args) {

        Map<String, Integer> inventory;
        List<ReservationUC12> reservations;

        // Load previous state
        SystemStateUC12 loadedState =
                PersistenceServiceUC12.load();

        if (loadedState != null) {

            inventory =
                    loadedState.getInventory();

            reservations =
                    loadedState.getReservations();

        } else {

            inventory = new HashMap<>();

            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);

            reservations =
                    new ArrayList<>();
        }

        System.out.println(
                "\nCurrent Inventory:");

        System.out.println(inventory);

        System.out.println(
                "\nBooking History:");

        for (ReservationUC12 reservation
                : reservations) {

            System.out.println(reservation);
        }

        // Simulate New Booking

        if (inventory.get("Deluxe") > 0) {

            ReservationUC12 booking =
                    new ReservationUC12(
                            "RES401",
                            "Arpit",
                            "Deluxe");

            reservations.add(booking);

            inventory.put(
                    "Deluxe",
                    inventory.get("Deluxe") - 1);

            System.out.println(
                    "\nNew Booking Added:");

            System.out.println(booking);
        }

        // Save Updated State

        SystemStateUC12 newState =
                new SystemStateUC12(
                        inventory,
                        reservations);

        PersistenceServiceUC12.save(newState);

        System.out.println(
                "\nFinal Inventory:");

        System.out.println(inventory);
    }
}
