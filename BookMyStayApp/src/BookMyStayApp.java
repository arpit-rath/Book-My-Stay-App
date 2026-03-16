/* Abstract Room Class */
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

/* Single Room Class */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 3000);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

/* Double Room Class */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 350, 5000);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

/* Suite Room Class */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 600, 9000);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

/* Application Entry Point */
public class BookMyStayApp {

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