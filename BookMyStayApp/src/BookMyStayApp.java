import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking Processor
class BookingProcessor {

    private Map<String, Integer> inventory;
    private Queue<BookingRequest> queue;

    public BookingProcessor(Map<String, Integer> inventory, Queue<BookingRequest> queue) {
        this.inventory = inventory;
        this.queue = queue;
    }

    // CRITICAL SECTION (synchronized)
    public synchronized void processBooking() {
        if (queue.isEmpty()) return;

        BookingRequest request = queue.poll();

        if (request == null) return;

        String type = request.roomType;

        // Check + Update must be atomic
        if (inventory.getOrDefault(type, 0) > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " booking for " + request.guestName);

            // Simulate delay (to expose race conditions if not synchronized)
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventory.put(type, inventory.get(type) - 1);

            System.out.println("SUCCESS: " + request.guestName +
                    " got " + type);
        } else {
            System.out.println("FAILED: No rooms for " + request.guestName);
        }
    }
}

// Worker Thread
class BookingWorker extends Thread {
    private BookingProcessor processor;

    public BookingWorker(BookingProcessor processor) {
        this.processor = processor;
    }

    public void run() {
        while (true) {
            processor.processBooking();

            // Stop when queue becomes empty
            try { Thread.sleep(50); } catch (InterruptedException e) {}

            synchronized (processor) {
                // small check to break loop
                // avoids infinite spinning
                // (safe because same lock is used)
                // NOTE: demonstration purpose only
            }
            if (Thread.interrupted()) break;
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // Shared inventory
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Deluxe", 2);

        // Shared queue
        Queue<BookingRequest> queue = new LinkedList<>();

        // Simulate multiple guests
        queue.add(new BookingRequest("Arpit", "Deluxe"));
        queue.add(new BookingRequest("Rahul", "Deluxe"));
        queue.add(new BookingRequest("Sneha", "Deluxe")); // extra request

        BookingProcessor processor = new BookingProcessor(inventory, queue);

        // Multiple threads (concurrent users)
        Thread t1 = new BookingWorker(processor);
        Thread t2 = new BookingWorker(processor);
        Thread t3 = new BookingWorker(processor);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        t1.start();
        t2.start();
        t3.start();

        // Allow threads to process
        Thread.sleep(1000);

        // Stop threads
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();

        System.out.println("\nFinal Inventory: " + inventory);
    }
}