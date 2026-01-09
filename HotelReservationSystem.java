import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    int reservationId;
    String customerName;
    int roomNumber;
    String category;
    double amountPaid;

    Reservation(int reservationId, String customerName, int roomNumber, String category, double amountPaid) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.amountPaid = amountPaid;
    }
}

public class HotelReservationSystem {

    static List<Room> rooms = new ArrayList<>();
    static List<Reservation> reservations = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "reservations.dat";

    public static void main(String[] args) {
        initializeRooms();
        loadReservations();

        while (true) {
            System.out.println("\n====== HOTEL RESERVATION SYSTEM ======");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelReservation();
                case 4 -> viewBookingDetails();
                case 5 -> {
                    saveReservations();
                    System.out.println("Thank you for using Hotel Reservation System!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 3000));
        rooms.add(new Room(202, "Deluxe", 3000));
        rooms.add(new Room(301, "Suite", 5000));
    }

    static void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (r.available) {
                System.out.println("Room No: " + r.roomNumber +
                        " | Category: " + r.category +
                        " | Price: ₹" + r.price);
            }
        }
    }

    static void bookRoom() {
        viewAvailableRooms();
        System.out.print("\nEnter Room Number to Book: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && r.available) {
                System.out.print("Enter Customer Name: ");
                String name = sc.nextLine();

                System.out.println("Processing Payment of ₹" + r.price + "...");
                System.out.println("Payment Successful!");

                int reservationId = new Random().nextInt(10000);
                reservations.add(new Reservation(reservationId, name, roomNo, r.category, r.price));
                r.available = false;

                saveReservations();

                System.out.println("Room Booked Successfully!");
                System.out.println("Reservation ID: " + reservationId);
                return;
            }
        }
        System.out.println("Room not available!");
    }

    static void cancelReservation() {
        System.out.print("Enter Reservation ID: ");
        int id = sc.nextInt();

        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation res = iterator.next();
            if (res.reservationId == id) {
                iterator.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == res.roomNumber) {
                        r.available = true;
                    }
                }
                saveReservations();
                System.out.println("Reservation Cancelled Successfully!");
                return;
            }
        }
        System.out.println("Reservation not found!");
    }

    static void viewBookingDetails() {
        System.out.print("Enter Reservation ID: ");
        int id = sc.nextInt();

        for (Reservation r : reservations) {
            if (r.reservationId == id) {
                System.out.println("\n--- Booking Details ---");
                System.out.println("Customer Name: " + r.customerName);
                System.out.println("Room Number: " + r.roomNumber);
                System.out.println("Category: " + r.category);
                System.out.println("Amount Paid: ₹" + r.amountPaid);
                return;
            }
        }
        System.out.println("Reservation not found!");
    }

    static void saveReservations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving reservations.");
        }
    }

    static void loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            reservations = (List<Reservation>) ois.readObject();
            for (Reservation r : reservations) {
                for (Room room : rooms) {
                    if (room.roomNumber == r.roomNumber) {
                        room.available = false;
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
