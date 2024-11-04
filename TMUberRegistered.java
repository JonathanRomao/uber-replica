import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * Name:        Jonathan Romao
*/

public class TMUberRegistered {
   // These variables are used to generate user account and driver ids
   private static int firstUserAccountID = 900;
   private static int firstDriverId = 700;

   // Generate a new user account id
   public static String generateUserAccountId(ArrayList<User> current) {
      return "" + firstUserAccountID + current.size();
   }

   // Generate a new driver id
   public static String generateDriverId(ArrayList<Driver> current) {
      return "" + firstDriverId + current.size();
   }

   // imports a database of registered users
   public static ArrayList<User> loadPreregisteredUsers(String filename) throws FileNotFoundException {
      ArrayList<User> users = new ArrayList<>();
      Scanner sc = new Scanner(new File(filename));

      while (sc.hasNextLine()) {
         String name = sc.nextLine();
         String address = sc.nextLine();
         int wallet = Integer.valueOf(sc.nextLine());

         users.add(new User(generateUserAccountId(users), name, address, wallet));
      }

      sc.close();
      return users;
   }

   // Database of Preregistered users
   // In Assignment 2 these will be loaded from a file
   public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws FileNotFoundException {
      ArrayList<Driver> drivers = new ArrayList<>();
      Scanner sc = new Scanner(new File(filename));

      while (sc.hasNextLine()) {
         String name = sc.nextLine();
         String carModel = sc.nextLine();
         String licensePlate = sc.nextLine();
         String address = sc.nextLine();

         drivers.add(new Driver(generateDriverId(drivers), name, carModel, licensePlate, address));
      }

      sc.close();
      return drivers;
   }
}
