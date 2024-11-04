import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Queue;
/*
 * Name:        Jonathan Romao
 * 
 * Student ID:  501249734
*/

/*
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 */
public class TMUberSystemManager {
   private TreeMap<String, User> users;
   private ArrayList<User> userList;
   private ArrayList<Driver> drivers;

   private Queue<TMUberService>[] serviceRequests;

   public double totalRevenue; // Total revenues accumulated via rides and deliveries

   // Rates per city block
   private static final double DELIVERYRATE = 1.2;
   private static final double RIDERATE = 1.5;

   // Portion of a ride/delivery cost paid to the driver
   private static final double PAYRATE = 0.1;

   // These variables are used to generate user account and driver ids
   int userAccountId = 900;
   int driverId = 700;

   @SuppressWarnings("unchecked")
   public TMUberSystemManager() {
      users = new TreeMap<>();
      drivers = new ArrayList<>();
      userList = new ArrayList<>();
      serviceRequests = new LinkedList[4];
      serviceRequests[0] = new LinkedList<>();
      serviceRequests[1] = new LinkedList<>();
      serviceRequests[2] = new LinkedList<>();
      serviceRequests[3] = new LinkedList<>();

      totalRevenue = 0;
   }

   // methods to set the data to the list and hashmap
   public void setDrivers(ArrayList<Driver> drivers) {
      this.drivers = drivers;
   }

   public void setUsers(ArrayList<User> users) {
      // this is to reset the hashmap
      this.users = new TreeMap<>();

      for (User user : users) {
         this.users.put(user.getAccountId(), user);
         userList.add(user);
      }
   }

   public void setServices(ArrayList<TMUberService>[] services) {

      for (int zone = 0; zone < 4; zone++) {
         serviceRequests[zone] = new LinkedList<>();
         for (TMUberService service : services[zone]) {
            serviceRequests[zone].add(service);
         }
      }
   }

   // Given user account id, find user in list of users
   // Return null if not found
   public User getUser(String accountId) {
      return users.get(accountId);
   }

   // Given driver id, find driver in list of drivers
   // Return null if not found
   public Driver getDriver(String driverId) {
      for (Driver driver : drivers) {
         if (driver.getId().equals(driverId)) {
            return driver;
         }
      }
      return null;
   }

   // Check for duplicate user
   private boolean userExists(User user) {
      return users.values().contains(user);
   }

   // Check for duplicate driver
   private boolean driverExists(Driver driver) {
      for (Driver d : drivers) {
         if (d.equals(driver)) {
            return true;
         }
      }
      return false;
   }

   // Given a TMUberService, check if user ride/delivery request already exists in
   // service requests
   private boolean existingRequest(TMUberService req) {
      for (int zone = 0; zone < 4; zone++) {
         for (TMUberService tmu_req : serviceRequests[zone]) {
            // checks if requests are the same type
            if (tmu_req.getServiceType().equals(req.getServiceType())) {
               if (tmu_req.equals(req)) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   // Calculate the cost of a ride or of a delivery based on distance
   private double getDeliveryCost(int distance) {
      return distance * DELIVERYRATE;
   }

   private double getRideCost(int distance) {
      return distance * RIDERATE;
   }

   // Go through all drivers and see if one is available
   // Choose the first available driver
   // Return null if no available driver
   private Driver getAvailableDriver() {
      for (Driver driver : drivers) {
         if (driver.getStatus() == Driver.Status.AVAILABLE) {
            return driver;
         }
      }
      return null;
   }

   // same as getAvailableDriver but makes sure the driver that is returned
   // has an XL car
   private XLDriver getAvailableXLDriver() {
      for (Driver driver : drivers) {
         if (driver.getType().equals("XL")) {
            if (driver.getStatus() == Driver.Status.AVAILABLE) {
               return (XLDriver) driver;
            }
         }
      }
      return null;
   }

   // Print Information (printInfo()) about all registered users in the system
   public void listAllUsers() {
      System.out.println();

      for (int i = 0; i < userList.size(); i++) {
         System.out.printf("%-2s. ", i + 1);
         userList.get(i).printInfo();
         System.out.println();
      }
   }

   // Print Information (printInfo()) about all registered drivers in the system
   public void listAllDrivers() {
      System.out.println();

      for (int i = 0; i < drivers.size(); i++) {
         int index = i + 1;
         System.out.printf("%-2s. ", index);
         drivers.get(i).printInfo();
         System.out.println();
      }
   }

   // helper method for listing all requests
   // lists all the requests in the given zone
   private void listAllZoneRequests(int zone) {
      if (serviceRequests[zone] != null) {
         Iterator<TMUberService> iterator = serviceRequests[zone].iterator();
         int count = 1;
         while (iterator.hasNext()) {
            System.out.printf("%-2s. %s", count, "-".repeat(60));
            iterator.next().printInfo();
            System.out.println("\n");
            count++;
         }

      }
   }

   // Print Information (printInfo()) about all current service requests
   public void listAllServiceRequests() {
      for (int zone_i = 0; zone_i < 4; zone_i++) {
         System.out.printf("ZONE %d\n======\n", zone_i);

         System.out.println();
         listAllZoneRequests(zone_i);
         System.out.println();
      }
   }

   // Add a new user to the system
   public void registerNewUser(String name, String address, double wallet) throws RuntimeException {

      if (name.trim().equals("") || name == null) { // username check
         throw new InvaildUserNameException("Invaild User Name");
      } else if (!CityMap.validAddress(address)) { // address check
         throw new InvaildAddressException("Invaild User Address");
      } else if (wallet < 0) { // wallet check
         throw new InvaildFundsException("Invaild Money in Wallet");
      }

      // creating the new id and user
      String new_id = Integer.toString(userAccountId) + Integer.toString(users.size());
      User user = new User(new_id, name, address, wallet);
      if (userExists(user)) { // duplicate user check
         throw new DuplicateUserException("User Already Exists in System");
      } else {
         users.put(user.getAccountId(), user);
         userList.add(user);
      }
   }

   // Add a new driver to the system
   public void registerNewDriver(String name, String carModel, String carLicensePlate, String address)
         throws RuntimeException {
      if (name.trim().equals("") || name == null) { // driver name check
         throw new InvaildDriverNameException("Invaild Driver Name");
      } else if (carModel.trim().equals("") || carModel == null) { // car model check
         throw new InvaildCarModelException("Invaild Car Model");
      } else if (carLicensePlate.trim().equals("") || carLicensePlate == null) { // car license check
         throw new InvaildCarLicenseException("Invaild Car License");
      } else if (!CityMap.validAddress(address)) { // address check
         throw new InvaildAddressException("Invalid Driver Address");
      }

      // creating the new id and driver
      String new_id = TMUberRegistered.generateDriverId(drivers);
      Driver driver = new Driver(new_id, name, carModel, carLicensePlate, address);
      if (driverExists(driver)) { // duplicate driver check
         throw new DuplicateDriverException("Driver Already Exists in System");
      } else {
         drivers.add(driver);
      }
   }

   // Adds a new XL driver to the system
   public void registerNewXLDriver(String name, String carModel, String carLicensePlate, int carSize, String address)
         throws RuntimeException {
      if (name.trim().equals("") || name == null) { // driver name check
         throw new InvaildDriverNameException("Invaild Driver Name");
      } else if (carModel.trim().equals("") || carModel == null) { // car model check
         throw new InvaildCarModelException("Invaild Car Model");
      } else if (carLicensePlate.trim().equals("") || carLicensePlate == null) { // car license check
         throw new InvaildCarLicenseException("Invaild Car License");
      } else if (carSize < 1) { // car size check
         throw new InvaildCarSizeException("Invalid Car Size");
      } else if (!CityMap.validAddress(address)) { // address check
         throw new InvaildAddressException("Invalid Driver Address");
      }

      // creating the new id and XLDriver
      String new_id = Integer.toString(driverId) + Integer.toString(drivers.size());
      XLDriver driver = new XLDriver(new_id, name, carModel, carLicensePlate, carSize, address);
      if (driverExists(driver)) { // duplicate driver check
         throw new DuplicateDriverException("Driver Already Exists in System");
      } else {
         drivers.add(driver);
      }
   }

   // Request a ride. User wallet will be reduced when drop off happens
   public void requestRide(String accountId, String from, String to) throws RuntimeException {

      User user = getUser(accountId);

      if (user == null) { // user DNE
         throw new UserNotFoundException("User Account Not Found");
      } else if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) { // address check
         throw new InvaildAddressException("Invalid Address");
      }

      // getting the distance
      int dist = CityMap.getDistance(from, to);
      int zone = CityMap.getCityZone(from);

      if (dist < 1) { // distance check
         throw new InvaildTravelDistanceException("Insufficient Travel Distance");
      }

      // getting cost pof ride
      double cost = getRideCost(dist);

      if (user.getWallet() < cost) { // checking if user has enough funds in wallet
         throw new InvaildFundsException("Insufficient Funds");
      }

      // creating the ride object
      TMUberRide temp_ride = new TMUberRide(from, to, user, dist, cost);

      if (existingRequest(temp_ride)) { // duplicate request check
         throw new UserHasExistingRideException("User Already Has Ride Request");
      } else {
         serviceRequests[zone].add(temp_ride);
         user.addRide();
      }
   }

   public void requestXLRide(String accountId, String from, String to, String numPassengers) throws RuntimeException {

      User user = getUser(accountId);

      if (user == null) { // user DNE
         throw new UserNotFoundException("User Account Not Found");
      } else if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) { // address check
         throw new InvaildAddressException("Invalid Address");
      }

      // getting the distance and zone of the ride
      int dist = CityMap.getDistance(from, to);
      int zone = CityMap.getCityZone(from);

      if (dist < 1) { // distance check
         throw new InvaildTravelDistanceException("Insufficient Travel Distance");
      }

      // casting the numPassengers pararmeter into an Integer
      int numPassengersInt = (Integer.valueOf(numPassengers));

      if (numPassengersInt <= 1) { // num passenger check
         throw new InvaildPassengerNumberException("Invalid Number of Passengers");

      }

      // getting the cost of the ride
      double cost = getRideCost(dist);

      if (user.getWallet() < cost) { // user funds check
         throw new InvaildFundsException("Insufficient Funds");
      }

      // creating the xl ride object
      TMUberXLRide temp_ride = new TMUberXLRide(from, to, user, dist, cost, numPassengersInt);

      if (existingRequest(temp_ride)) { // duplicate xl ride check
         throw new UserHasExistingRideException("User Already Has Ride Request");
      }
      serviceRequests[zone].add(temp_ride);
      user.addRide();
   }

   // Request a food delivery. User wallet will be reduced when drop off happens
   public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
         throws RuntimeException {
      // See the comments above and use them as a guide
      // For deliveries, an existing delivery has the same user, restaurant and food
      // order id
      // Increment the number of deliveries the user has had
      User user = getUser(accountId);

      if (user == null) { // User DNE
         throw new UserNotFoundException("User Account Not Found");
      } else if (!CityMap.validAddress(from) || !CityMap.validAddress(to)) { // address check
         throw new InvaildAddressException("Invalid Address");
      }

      // getting the distance of the ride
      int dist = CityMap.getDistance(from, to);
      int zone = CityMap.getCityZone(from);

      if (dist < 1) { // distance check
         throw new InvaildTravelDistanceException("Insufficient Travel Distance");
      }

      // getting the cost of the delivery
      double cost = getDeliveryCost(dist);

      if (user.getWallet() < cost) { // user funds check
         throw new InvaildFundsException("Insufficient Funds");
      }

      if (restaurant == null || restaurant.equals("")) {
         throw new InvalidRestaurantException("Invalid Restaurant");
      }
      if (foodOrderId == null || foodOrderId.equals("")) {
         throw new InvalidFoodIdException("Invalid Food Order #");
      }

      // creating the delivery object
      TMUberDelivery temp_del = new TMUberDelivery(from, to, user, dist, cost, restaurant, foodOrderId);

      if (existingRequest(temp_del)) { // duplicate delivery check
         throw new UserHasExistingDeliveryException(
               "User Already Has Delivery Request at Restaurant with this Food Order");
      } else {
         serviceRequests[zone].add(temp_del);
         user.addDelivery();
      }
   }

   // Cancel an existing service request.
   // parameter int request is the index in the serviceRequests array list
   public void cancelServiceRequest(int zone, int request) throws RuntimeException {

      request--;
      if (zone < 0 || zone > 3) {
         throw new InvaildRequestNumberException("Invalid Zone Number");
      }

      if (request >= serviceRequests[zone].size() || request < 0) {
         throw new InvaildRequestNumberException(
               String.format("Request #%s In Zone %s Does Not Exist", request + 1, zone));
      }
      // create new queue and TMUService var
      Queue<TMUberService> new_q = new LinkedList<>();
      Iterator<TMUberService> iterator = serviceRequests[zone].iterator();
      int count = 0;
      TMUberService removed = null;

      // iterate through the queue
      while (iterator.hasNext()) {
         TMUberService next = iterator.next();
         if (count != request) { // if not the request to remove, add to new queue
            new_q.add(next);
         } else { // if it is the request to remove don't add to new queue
            removed = next;
         }
         count++;
      }

      serviceRequests[zone] = new_q;

      // reducing the users rides/deliveries counter
      if (removed.getServiceType().equals("RIDE") || removed.getServiceType().equals("XLRIDE"))
         removed.getUser().subRide();
      else if (removed.getServiceType().equals("DELIVERY"))
         removed.getUser().subDelivery();
   }

   // takes a driver ID and tells that driver to pick up an order from the zone
   // that they are in
   public void pickup(String driver_id) throws RuntimeException {

      if (getDriver(driver_id) == null) { // checks if given driver ID is valid
         throw new DriverNotAvailableException("No Driver Found With ID: " + driver_id);
      }
      Driver driver = getDriver(driver_id);
      int zone = driver.getZone();

      if (serviceRequests[zone].size() == 0) { // checks for available service in the zone of the driver
         throw new NoServiceAvailableException("There Are No Service Requests In The Drivers Current Zone");
      } else if (driver.getStatus() == Driver.Status.DRIVING) {
         throw new DriverBusyException("Driver Is Busy With Another Request");
      }

      driver.setService(serviceRequests[zone].remove());
      driver.setStatus(Driver.Status.DRIVING);
   }

   // Drop off a ride or a delivery. This completes a service.
   // parameter request is the index in the serviceRequests array list
   public void dropOff(String driver_id) throws RuntimeException {

      if (getDriver(driver_id) == null) { // checks if given driver ID is valid
         throw new DriverNotAvailableException("No Driver Found With ID " + driver_id);
      }
      // retrieving all info from the service
      Driver driver = getDriver(driver_id);

      if (driver.getService() == null) {
         throw new DriverNotAvailableException("Driver does not have a pickup");
      }
      TMUberService service = driver.getService();
      User user = service.getUser();
      double cost = service.getCost();

      // money opperations
      user.payForService(cost);
      totalRevenue += cost;
      driver.pay(cost * PAYRATE);
      totalRevenue -= cost * PAYRATE;

      // reset the driver
      driver.setAddress(service.getTo());
      driver.setZone(CityMap.getCityZone(service.getTo()));
      driver.setService(null);
      driver.setStatus(Driver.Status.AVAILABLE);
   }

   public void driveTo(String driver_id, String address) {

      Driver driver = getDriver(driver_id);

      if (driver == null) { // checks if given driver ID is valid
         throw new DriverNotAvailableException("No Driver Found With ID: " + driver_id);

      } else if (!CityMap.validAddress(address)) { // address check
         throw new InvaildAddressException("Invalid Address");

      } else if (driver.getStatus() == Driver.Status.DRIVING) { // checks if driving is already currently driving
         throw new DriverBusyException("Driver Is Already Driving");

      }

      driver.setAddress(address);
      driver.setZone(CityMap.getCityZone(address));
   }

   // Sort users by name
   // Then list all users
   public void sortByUserName() {
      Collections.sort(userList, new NameComparator());
      listAllUsers();
   }

   // Helper class for method sortByUserName
   private class NameComparator implements Comparator<User> {
      public int compare(User u1, User u2) {
         // built in compareTo method for strings
         return u1.getName().compareTo(u2.getName());
      }
   }

   // Sort users by number amount in wallet
   // Then list all users
   public void sortByWallet() {
      Collections.sort(userList, new UserWalletComparator());
      listAllUsers();
   }

   // Helper class for use by sortByWallet
   private class UserWalletComparator implements Comparator<User> {
      public int compare(User u1, User u2) {
         return (int) (u1.getWallet() - u2.getWallet());
      }
   }

   // Sort trips (rides or deliveries) by distance
   // Then list all current service requests
   @SuppressWarnings("unchecked")
   public void sortByDistance() {
      ArrayList<TMUberService>[] sorting_array = new ArrayList[4];

      for (int zone = 0; zone < 4; zone++) {
         sorting_array[zone] = new ArrayList<>();
         for (TMUberService service : serviceRequests[zone]) {
            sorting_array[zone].add(service);
         }
      }

      for (ArrayList<TMUberService> arrayList : sorting_array) {
         Collections.sort(arrayList);
      }

      setServices(sorting_array);
      listAllServiceRequests();
   }

}

// Exeptions
class InvaildUserNameException extends RuntimeException {

   public InvaildUserNameException(String message) {
      super("\nError: " + message);
   }
}

class InvaildDriverNameException extends RuntimeException {

   public InvaildDriverNameException(String message) {
      super("\nError: " + message);
   }
}

class InvaildAddressException extends RuntimeException {

   public InvaildAddressException(String message) {
      super("\nError: " + message);
   }
}

class InvaildFundsException extends RuntimeException {

   public InvaildFundsException(String message) {
      super("\nError: " + message);
   }
}

class InvaildCarModelException extends RuntimeException {

   public InvaildCarModelException(String message) {
      super("\nError: " + message);
   }
}

class InvaildCarLicenseException extends RuntimeException {

   public InvaildCarLicenseException(String message) {
      super("\nError: " + message);
   }
}

class InvaildCarSizeException extends RuntimeException {

   public InvaildCarSizeException(String message) {
      super("\nError: " + message);
   }
}

class InvaildUserException extends RuntimeException {

   public InvaildUserException(String message) {
      super("\nError: " + message);
   }
}

class InvaildTravelDistanceException extends RuntimeException {

   public InvaildTravelDistanceException(String message) {
      super("\nError: " + message);
   }
}

class InvaildPassengerNumberException extends RuntimeException {

   public InvaildPassengerNumberException(String message) {
      super("\nError: " + message);
   }
}

class InvaildRequestNumberException extends RuntimeException {

   public InvaildRequestNumberException(String message) {
      super("\nError: " + message);
   }
}

class InvalidRestaurantException extends RuntimeException {

   public InvalidRestaurantException(String message) {
      super("\nError: " + message);
   }
}

class InvalidFoodIdException extends RuntimeException {

   public InvalidFoodIdException(String message) {
      super("\nError: " + message);
   }
}

class UserNotFoundException extends RuntimeException {

   public UserNotFoundException(String message) {
      super("\nError: " + message);
   }
}

class UserHasExistingRideException extends RuntimeException {

   public UserHasExistingRideException(String message) {
      super("\nError: " + message);
   }
}

class UserHasExistingDeliveryException extends RuntimeException {

   public UserHasExistingDeliveryException(String message) {
      super("\nError: " + message);
   }
}

class DuplicateUserException extends RuntimeException {

   public DuplicateUserException(String message) {
      super("\nError: " + message);
   }
}

class DriverNotAvailableException extends RuntimeException {

   public DriverNotAvailableException(String message) {
      super("\nError: " + message);
   }
}

class DuplicateDriverException extends RuntimeException {

   public DuplicateDriverException(String message) {
      super("\nError: " + message);
   }
}

class NoServiceAvailableException extends RuntimeException {

   public NoServiceAvailableException(String message) {
      super("\nError: " + message);
   }
}

class DriverBusyException extends RuntimeException {

   public DriverBusyException(String message) {
      super("\nError: " + message);
   }
}
