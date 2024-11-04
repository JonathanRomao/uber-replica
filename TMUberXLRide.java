/*
 * Name:        Jonathan Romao
 * 
 * Student ID:  501249734
*/

/*
 * This extends the TMUberRide class for the abilty to have more than 1 person on a ride 
 */

public class TMUberXLRide extends TMUberRide {

    public static final String TYPENAME = "XLRIDE";

    public TMUberXLRide(String from, String to, User user, int distance, double cost,
            int numPassengers) {
        super(from, to, user, distance, cost);
        this.setRequestedXL(true);
        this.setNumPassengers(numPassengers);
    }

    public String getServiceType() {
        return TYPENAME;
    }

    // Overiding to properly display the type of ride when calling
    // listAllServiceRequests() in TMUberSystemManager
    public void printInfo() {
        System.out.printf("\nType: %-9s From: %-15s To: %-15s", getServiceType(), getFrom(), getTo());
        System.out.print("\nUser: ");
        getUser().printInfo();
    }
}
