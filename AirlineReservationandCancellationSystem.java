 /*
 
   3. Airline Reservation and Cancellation System :

      Create an airline reservation system that allows users
      to book, cancel, or modify tickets. System Perform following operation:
 To initialize flight and passenger data
 To manage invalid booking or cancellation requests

class : 
1.--> Book a flight
bookTicket()

Ask passenger ID

Check if passenger exists

If already booked → show warning

Show available flights

Ask for flight number

Check if flight exists and has seats

If valid → reduce seat count and assign flight to passenger

2.--> Cancel a ticket
 cancelTicket()
Ask passenger ID

Check if passenger exists

If passenger has a booking → cancel it

Increase flight seat by 1

Set bookedFlightNumber = -1

3.--- >Modify details 
modifyBooking()

Change passenger name

Change booked flight

Cancel old booking

Book new flight if seats available

4.View available flights or passengers

 

 */
 


import java.util.*;

class Flight {
    int flightnumber;
    String source;
    String destination;
    int availableseats;

    Flight(int flightnumber, String source, String destination, int availableseats) {
        this.flightnumber = flightnumber;
        this.source = source;
        this.destination = destination;
        this.availableseats = availableseats;
    }

    public void display() {
        System.out.println("Flight " + flightnumber + " | " + source + " -> " + destination + " | Seats: " + availableseats);
    }
}

class Passenger {
    int id;
    String name;
    int bookedflightnum;

    Passenger(int id, String name) {
        this.id = id;
        this.name = name;
        this.bookedflightnum = -1; // means not booked
    }

    public void display() {
        if (bookedflightnum == -1)
            System.out.println("Passenger ID: " + id + " | Name: " + name + " | No booking");
        else
            System.out.println("Passenger ID: " + id + " | Name: " + name + " | Booked Flight: " + bookedflightnum);
    }
}

public class AirlineReservationandCancellationSystem {
    static ArrayList<Flight> flights = new ArrayList<>();
    static ArrayList<Passenger> passengers = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
        initializeData();
        int choice;

        do {
            System.out.println("\n==== Airline Reservation and Cancellation System ====");
            System.out.println("1. Show Flights");
            System.out.println("2. Show Passengers");
            System.out.println("3. Book Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. Modify Booking");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> showFlights();
                case 2 -> showPassengers();
                case 3 -> bookTicket();
                case 4 -> cancelTicket();
                case 5 -> modifyBooking();
                case 6 -> System.out.println("Thank you for using the system!");
                default -> System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 6);
    }

    static void initializeData() {
        flights.add(new Flight(101, "Ahmedabad", "Mumbai", 4));
        flights.add(new Flight(102, "Delhi", "Punjab", 5));
        flights.add(new Flight(103, "Bangalore", "Hyderabad", 8));
        flights.add(new Flight(104, "Rajkot", "Patna", 2));
        flights.add(new Flight(105, "Pune", "Chennai", 6));

        passengers.add(new Passenger(1, "Navshad"));
        passengers.add(new Passenger(2, "Mahira"));
        passengers.add(new Passenger(3, "Karan"));
        passengers.add(new Passenger(4, "Moin"));
        passengers.add(new Passenger(5, "Sonal"));
        passengers.add(new Passenger(6, "Husen"));
    }

    static void showFlights() {
        System.out.println("\nAvailable Flights:");
        for (Flight f : flights)
            f.display();
    }

    static void showPassengers() {
        System.out.println("\nPassenger List:");
        for (Passenger p : passengers)
            p.display();
    }

    static void bookTicket() {
        System.out.print("Enter Passenger ID: ");
        int pid = sc.nextInt();
        Passenger p = findPassenger(pid);

        if (p == null) {
            System.out.println("Passenger not found!");
            return;
        }

        if (p.bookedflightnum != -1) {
            System.out.println("Passenger already has a booking! Cancel first to book another.");
            return;
        }

        showFlights();
        System.out.print("Enter Flight Number to book: ");
        int fno = sc.nextInt();
        Flight f = findFlight(fno);

        if (f == null) {
            System.out.println("Invalid flight number!");
            return;
        }

        if (f.availableseats > 0) {
            f.availableseats--;
            p.bookedflightnum = f.flightnumber;
            System.out.println("Booking successful for " + p.name + " on Flight " + f.flightnumber);
        } else {
            System.out.println("No seats available on this flight!");
        }
    }

    static void cancelTicket() {
        System.out.print("Enter Passenger ID: ");
        int pid = sc.nextInt();
        Passenger p = findPassenger(pid);

        if (p == null) {
            System.out.println("Passenger not found!");
            return;
        }

        if (p.bookedflightnum == -1) {
            System.out.println("No booking found for this passenger!");
            return;
        }

        Flight f = findFlight(p.bookedflightnum);
        if (f != null) {
            f.availableseats++;
            p.bookedflightnum = -1;
            System.out.println("Booking canceled successfully!");
        }
    }

    static void modifyBooking() {
        System.out.print("Enter Passenger ID: ");
        int pid = sc.nextInt();
        Passenger p = findPassenger(pid);

        if (p == null) {
            System.out.println("Passenger not found!");
            return;
        }

        System.out.println("1. Change Passenger Name");
        System.out.println("2. Change Flight");
        System.out.print("Enter your choice: ");
        int ch = sc.nextInt();
        sc.nextLine();

        switch (ch) {
            case 1 -> {
                System.out.print("Enter new name: ");
                String newname = sc.nextLine();
                p.name = newname;
                System.out.println("Name updated successfully!");
            }

            case 2 -> {
                if (p.bookedflightnum == -1) {
                    System.out.println("No booking found! Please book first.");
                    return;
                }

                Flight oldFlight = findFlight(p.bookedflightnum);
                if (oldFlight != null)
                    oldFlight.availableseats++;

                showFlights();
                System.out.print("Enter new Flight Number: ");
                int newFno = sc.nextInt();
                Flight newFlight = findFlight(newFno);

                if (newFlight == null || newFlight.availableseats == 0) {
                    System.out.println("Invalid or full flight. Modification failed!");
                    if (oldFlight != null)
                        oldFlight.availableseats--; // revert seat change
                    return;
                }

                newFlight.availableseats--;
                p.bookedflightnum = newFno;
                System.out.println("Flight changed successfully to " + newFno);
            }

            default -> System.out.println("Invalid choice!");
        }
    }

    static Flight findFlight(int flightnumber) {
        for (Flight f : flights)
            if (f.flightnumber == flightnumber)
                return f;
        return null;
    }

    static Passenger findPassenger(int id) {
        for (Passenger p : passengers)
            if (p.id == id)
                return p;
        return null;
    }
}
