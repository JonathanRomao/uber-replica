import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Name:        Jonathan Romao
 * 
 * Student ID:  501249734
*/

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI {
   public static void main(String[] args) {
      // Create the System Manager - the main system code is in here

      TMUberSystemManager tmuber = new TMUberSystemManager();

      Scanner scanner = new Scanner(System.in);
      System.out.print("> ");

      // Process keyboard actions
      while (scanner.hasNextLine()) {
         String action = scanner.nextLine().trim();

         if (action == null || action.equals("")) {
            System.out.print("\n>");
            continue;
         }

         // Quit the App
         else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT")) {
            scanner.close();
            return;
         }

         // Loads users from a given file
         else if (action.equalsIgnoreCase("LOADUSERS")) {

            String filename = "";
            System.out.print("Filename: ");
            if (scanner.hasNextLine()) {
               filename = scanner.nextLine();
            }

            try {
               ArrayList<User> users = new ArrayList<User>(TMUberRegistered.loadPreregisteredUsers(filename));
               tmuber.setUsers(users);
               System.out.println("Users Loaded");
            } catch (FileNotFoundException e) {
               System.out.println(filename + " Not Found");
            }
         }

         // Loads drivers from a given file
         else if (action.equalsIgnoreCase("LOADDRIVERS")) {

            String filename = "";
            System.out.print("Filename: ");
            if (scanner.hasNextLine()) {
               filename = scanner.nextLine();
            }

            try {
               ArrayList<Driver> drivers = new ArrayList<Driver>(TMUberRegistered.loadPreregisteredDrivers(filename));
               tmuber.setDrivers(drivers);
               System.out.println("Drivers Loaded");
            } catch (FileNotFoundException e) {
               System.out.println(filename + " Not Found");
            }
         }

         // Print all the registered drivers
         else if (action.equalsIgnoreCase("DRIVERS")) {
            tmuber.listAllDrivers();
         }

         // Print all the registered users
         else if (action.equalsIgnoreCase("USERS")) {
            tmuber.listAllUsers();
         }

         // Print all current ride requests or delivery requests
         else if (action.equalsIgnoreCase("REQUESTS")) {
            tmuber.listAllServiceRequests();
         }

         // Register a new driver
         else if (action.equalsIgnoreCase("REGDRIVER")) {

            String name = "";
            System.out.print("Name: ");

            if (scanner.hasNextLine()) {
               name = scanner.nextLine();
            }

            String carModel = "";
            System.out.print("Car Model: ");
            if (scanner.hasNextLine()) {
               carModel = scanner.nextLine();
            }

            String license = "";
            System.out.print("Car License: ");
            if (scanner.hasNextLine()) {
               license = scanner.nextLine();
            }

            String address = "";
            System.out.print("Driver Address: ");
            if (scanner.hasNextLine()) {
               license = scanner.nextLine();
            }

            try {
               tmuber.registerNewDriver(name, carModel, license, address);
               System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s",
                     name, carModel, license, address);
            } catch (InvaildDriverNameException e) {
               System.out.println(e.getMessage());

            } catch (InvaildCarModelException e) {
               System.out.println(e.getMessage());

            } catch (InvaildCarLicenseException e) {
               System.out.println(e.getMessage());

            } catch (InvaildAddressException e) {
               System.out.println(e.getMessage());
            }

         }

         // Register a new XL driver
         else if (action.equalsIgnoreCase("REGXLDRIVER")) {
            String name = "";
            System.out.print("Name: ");

            if (scanner.hasNextLine()) {
               name = scanner.nextLine();
            }

            String carModel = "";
            System.out.print("Car Model: ");
            if (scanner.hasNextLine()) {
               carModel = scanner.nextLine();
            }

            String license = "";
            System.out.print("Car License: ");
            if (scanner.hasNextLine()) {
               license = scanner.nextLine();
            }

            String carSize = "";
            System.out.print("Car Size: ");
            if (scanner.hasNextLine()) {
               carSize = scanner.nextLine();
            }

            String address = "";
            System.out.print("Driver Address: ");
            if (scanner.hasNextLine()) {
               license = scanner.nextLine();
            }
            try {
               tmuber.registerNewXLDriver(name, carModel, license, Integer.valueOf(carSize), address);
               System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Car Size: %-10s Address: %-15s",
                     name, carModel, license, carSize, address);
            } catch (InvaildDriverNameException e) {
               System.out.println(e.getMessage());

            } catch (InvaildCarModelException e) {
               System.out.println(e.getMessage());

            } catch (InvaildCarLicenseException e) {
               System.out.println(e.getMessage());

            } catch (InvaildAddressException e) {
               System.out.println(e.getMessage());

            } catch (InvaildCarSizeException e) {
               System.out.println(e.getMessage());
            }

         }

         // Register a new user
         else if (action.equalsIgnoreCase("REGUSER")) {
            String name = "";
            System.out.print("Name: ");
            if (scanner.hasNextLine()) {
               name = scanner.nextLine();
            }

            String address = "";
            System.out.print("Address: ");
            if (scanner.hasNextLine()) {
               address = scanner.nextLine();
            }

            double wallet = 0.0;
            System.out.print("Wallet: ");
            if (scanner.hasNextDouble()) {
               wallet = scanner.nextDouble();
               scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
            }
            try {
               tmuber.registerNewUser(name, address, wallet);
               System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
            } catch (InvaildUserNameException e) {
               System.out.println(e.getMessage());

            } catch (InvaildAddressException e) {
               System.out.println(e.getMessage());

            } catch (InvaildFundsException e) {
               System.out.println(e.getMessage());

            }
         }

         // Request a ride
         else if (action.equalsIgnoreCase("REQRIDE")) {
            // Get the following information from the user (on separate lines)
            // Then use the TMUberSystemManager requestRide() method properly to make a ride
            // request
            // "User Account Id: " (string)
            // "From Address: " (string)
            // "To Address: " (string)

            String account_id = "";
            System.out.print("User Account Id: ");
            if (scanner.hasNextLine()) {
               account_id = scanner.nextLine();
            }

            String from_address = "";
            System.out.print("From Address: ");
            if (scanner.hasNextLine()) {
               from_address = scanner.nextLine();
            }

            String to_address = "";
            System.out.print("To Address: ");
            if (scanner.hasNextLine()) {
               to_address = scanner.nextLine();
            }
            try {
               tmuber.requestRide(account_id, from_address, to_address);
               System.out.printf("\nRIDE for: %-15s From: %-15s To: %-15s",
                     tmuber.getUser(account_id).getName(), from_address, to_address);
            } catch (UserNotFoundException e) {
               System.out.println(e.getMessage());

            } catch (InvaildAddressException e) {
               System.out.println(e.getMessage());

            } catch (InvaildTravelDistanceException e) {
               System.out.println(e.getMessage());

            } catch (InvaildFundsException e) {
               System.out.println(e.getMessage());

            } catch (UserHasExistingRideException e) {
               System.out.println(e.getMessage());

            }
         }

         // Request an XL ride
         else if (action.equalsIgnoreCase("REQXL")) {

            String account_id = "";
            System.out.print("User Account Id: ");
            if (scanner.hasNextLine()) {
               account_id = scanner.nextLine();
            }

            String from_address = "";
            System.out.print("From Address: ");
            if (scanner.hasNextLine()) {
               from_address = scanner.nextLine();
            }

            String to_address = "";
            System.out.print("To Address: ");
            if (scanner.hasNextLine()) {
               to_address = scanner.nextLine();
            }

            String num_passengers = "";
            System.out.print("Number of Passengers: ");
            if (scanner.hasNextLine()) {
               num_passengers = scanner.nextLine();
            }

            try {
               tmuber.requestXLRide(account_id, from_address, to_address, num_passengers);
               System.out.printf("\nRIDE for: %-15s From: %-15s To: %-15s Passengers: %-5s",
                     tmuber.getUser(account_id).getName(), from_address, to_address, num_passengers);
            } catch (UserNotFoundException e) {
               System.out.println(e.getMessage());

            } catch (InvaildAddressException e) {
               System.out.println(e.getMessage());

            } catch (InvaildTravelDistanceException e) {
               System.out.println(e.getMessage());

            } catch (InvaildFundsException e) {
               System.out.println(e.getMessage());

            } catch (UserHasExistingRideException e) {
               System.out.println(e.getMessage());

            } catch (InvaildPassengerNumberException e) {
               System.out.println(e.getMessage());
            }
         }

         // Request a food delivery
         else if (action.equalsIgnoreCase("REQDLVY")) {

            String account_id = "";
            System.out.print("User Account Id: ");
            if (scanner.hasNextLine()) {
               account_id = scanner.nextLine();
            }

            String from_address = "";
            System.out.print("From Address: ");
            if (scanner.hasNextLine()) {
               from_address = scanner.nextLine();
            }

            String to_address = "";
            System.out.print("To Address: ");
            if (scanner.hasNextLine()) {
               to_address = scanner.nextLine();
            }

            String restaurant = "";
            System.out.print("Restaurant: ");
            if (scanner.hasNextLine()) {
               restaurant = scanner.nextLine();
            }

            String food_order_num = "";
            System.out.print("Food Order #: ");
            if (scanner.hasNextLine()) {
               food_order_num = scanner.nextLine();
            }

            try {
               tmuber.requestDelivery(account_id, from_address, to_address, restaurant, food_order_num);
               System.out.printf("\nDELIVERY for: %-15s From: %-15s To: %-15s",
                     tmuber.getUser(account_id).getName(), from_address, to_address);
            } catch (UserNotFoundException e) {
               System.out.println(e.getMessage());

            } catch (InvaildAddressException e) {
               System.out.println(e.getMessage());

            } catch (InvaildTravelDistanceException e) {
               System.out.println(e.getMessage());

            } catch (InvaildFundsException e) {
               System.out.println(e.getMessage());

            } catch (InvalidRestaurantException e) {
               System.out.println(e.getMessage());

            } catch (InvalidFoodIdException e) {
               System.out.println(e.getMessage());

            } catch (UserHasExistingDeliveryException e) {
               System.out.println(e.getMessage());
            }

         }

         // Sort users by name
         else if (action.equalsIgnoreCase("SORTBYNAME")) {
            tmuber.sortByUserName();
         }

         // Sort users by number of ride they have had
         else if (action.equalsIgnoreCase("SORTBYWALLET")) {
            tmuber.sortByWallet();
         }

         // Sort current service requests (ride or delivery) by distance
         else if (action.equalsIgnoreCase("SORTBYDIST")) {
            tmuber.sortByDistance();
         }

         // Cancel a current service (ride or delivery) request
         else if (action.equalsIgnoreCase("CANCELREQ")) {
            int zone = -1;
            System.out.print("Zone: ");
            if (scanner.hasNextInt()) {
               zone = scanner.nextInt();
               scanner.nextLine(); // consume nl character
            }

            int request = -1;
            System.out.print("Request #: ");
            if (scanner.hasNextInt()) {
               request = scanner.nextInt();
               scanner.nextLine(); // consume nl character
            }

            try {
               tmuber.cancelServiceRequest(zone, request);
               System.out.printf("Service request #%s in zone %s cancelled\n", request, zone);
            } catch (InvaildRequestNumberException e) {
               System.out.println(e.getMessage());
            }
         }

         // picks up the user/delivery
         else if (action.equalsIgnoreCase("PICKUP")) {

            String driver_id = "";
            System.out.print("Driver Id: ");
            if (scanner.hasNextLine()) {
               driver_id = scanner.nextLine();
            }

            try {
               tmuber.pickup(driver_id);
               System.out.printf("Driver %s Picking Up In Zone #%s\n", driver_id,
                     tmuber.getDriver(driver_id).getZone());
            } catch (DriverNotAvailableException e) {
               System.out.println(e.getMessage());
            } catch (NoServiceAvailableException e) {
               System.out.println(e.getMessage());
            } catch (DriverBusyException e) {
               System.out.println(e.getMessage());
            }
         }

         // moves a driver to a specfic location
         else if (action.equalsIgnoreCase("DRIVETO")) {

            String driver_id = "";
            System.out.print("Driver Id: ");
            if (scanner.hasNextLine()) {
               driver_id = scanner.nextLine();
            }

            String address = "";
            System.out.print("Address: ");
            if (scanner.hasNextLine()) {
               address = scanner.nextLine();
            }

            try {
               tmuber.driveTo(driver_id, address);
               System.out.printf("");
            } catch (DriverNotAvailableException e) {
               System.out.println(e.getMessage());
            } catch (InvaildAddressException e) {
               System.out.println(e.getMessage());
            } catch (DriverBusyException e) {
               System.out.println(e.getMessage());
            }
         }

         // Drop-off the user or the food delivery to the destination address
         else if (action.equalsIgnoreCase("DROPOFF")) {
            String driver_id = "";
            System.out.print("Driver ID: ");
            if (scanner.hasNextLine()) {
               driver_id = scanner.nextLine();
            }

            try {
               tmuber.dropOff(driver_id);
               System.out.printf("Driver %s Dropping Off\n", driver_id);
            } catch (DriverNotAvailableException e) {
               System.out.println(e.getMessage());
            }
         }

         // Get the Current Total Revenues
         else if (action.equalsIgnoreCase("REVENUES")) {
            System.out.printf("Total Revenue: %.2f\n", tmuber.totalRevenue);
         }

         // Unit Test of Valid City Address
         else if (action.equalsIgnoreCase("ADDR")) {
            String address = "";
            System.out.print("Address: ");
            if (scanner.hasNextLine()) {
               address = scanner.nextLine();
            }
            System.out.print(address);
            if (CityMap.validAddress(address))
               System.out.println("\nValid Address");
            else
               System.out.println("\nBad Address");
         }

         // Unit Test of CityMap Distance Method
         else if (action.equalsIgnoreCase("DIST")) {
            String from = "";
            System.out.print("From: ");
            if (scanner.hasNextLine()) {
               from = scanner.nextLine();
            }
            String to = "";
            System.out.print("To: ");
            if (scanner.hasNextLine()) {
               to = scanner.nextLine();
            }
            System.out.print("\nFrom: " + from + " To: " + to);
            System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
         }

         // lists all the available commands to make using the commands easier
         else if (action.equalsIgnoreCase("HELP") || action.equalsIgnoreCase("H")) {
            System.out.println("""
                  \n
                  QUIT/Q\n-> Quit the App.\n
                  LOADUSERS\n-> Loads users from a given file.\n
                  LOADDRIVERS\n-> Loads drivers from a given file.\n
                  DRIVERS\n-> Print all the registered drivers.\n
                  USERS\n-> Print all the registered users.\n
                  REQUESTS\n-> Print all current ride requests or delivery requests.\n
                  REGDRIVER\n-> Register a new driver.\n
                  REGXLDRIVER\n-> Register a new XL driver.\n
                  REGUSER\n-> Register a new user.\n
                  REQRIDE\n-> Request a ride.\n
                  REQXL\n-> Request an XL ride.\n
                  REQDLVY\n-> Request a food delivery.\n
                  SORTBYNAME\n-> Sort users by name.\n
                  SORTBYWALLET\n-> Sort users by number of ride they have had.\n
                  SORTBYDIST\n-> Sort current service requests (ride or delivery) by distance.\n
                  CANCELREQ\n-> Cancel a current service (ride or delivery) request.\n
                  PICKUP\n-> Picks up the user/delivery.\n
                  DRIVETO\n-> Moves a driver to a specified location.\n
                  DROPOFF\n-> Drop-off the user or the food delivery to the destination address.\n
                  REVENUES\n-> Get the Current Total Revenues.\n
                  ADDR\n-> Unit Test of Valid City Address.\n
                  DIST\n-> Unit Test of CityMap Distance Method.\n""");
         }

         // I reset the scanner becuase of a problem that I noticed
         // whenever the user would type in information that would give an error but
         // wasn't the last line
         // after the method to do the action was completeted in TMUberSystemManager
         // the "action" variable was still what the user had input in the field(s) after
         // the one that gave them the error
         // this causes the "\n>" at the end of the while loop to be printed twice
         scanner.reset();
         System.out.print("\n> ");
      }
      scanner.close();
   }
}
