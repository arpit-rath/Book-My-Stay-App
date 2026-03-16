import java.util.LinkedList;
import java.util.Queue;

/**
 * Reservation class represents a guest booking request.
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

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add a new booking request
    public void addRequest(Reservation reservation) {
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

        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }

    // Peek at the next request without removing it
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }
}

/**
 * Application Entry Point
 */
public class BookingQueueApp {

    public static void main(String[] args) {

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        bookingQueue.addRequest(new Reservation("Arpit", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite Room"));

        // Display queued requests
        bookingQueue.displayRequests();

        // Show next request to be processed (FIFO)
        System.out.println("\nNext request to process: " + bookingQueue.peekNextRequest());

        System.out.println("\nRequests are waiting for allocation processing.");
    }
}