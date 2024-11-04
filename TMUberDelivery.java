/*
 * Name:        Jonathan Romao
 * 
 * Student ID:  501249734
*/

/*
 * 
 * This class simulates a food delivery service for a simple Uber app
 * 
 * A TMUberDelivery is-a TMUberService with some extra functionality
 */
public class TMUberDelivery extends TMUberService {
    public static final String TYPENAME = "DELIVERY";

    private String restaurant;
    private String foodOrderId;

    // Constructor to initialize all inherited and new instance variables
    public TMUberDelivery(String from, String to, User user, int distance, double cost,
            String restaurant, String order) {
        super(from, to, user, distance, cost, TYPENAME);
        this.restaurant = restaurant;
        this.foodOrderId = order;
    }

    public String getServiceType() {
        return TYPENAME;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getFoodOrderId() {
        return foodOrderId;
    }

    public void setFoodOrderId(String foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    /*
     * Two Delivery Requests are equal if they are equal in terms of
     * TMUberServiceRequest
     * and the restaurant and food order id are the same
     */
    public boolean equals(Object other) {
        TMUberDelivery other_del = (TMUberDelivery) other;

        return (super.equals(other_del) &&
                this.getRestaurant().equalsIgnoreCase(other_del.getRestaurant()) &&
                this.getFoodOrderId().equalsIgnoreCase(other_del.getFoodOrderId()));
    }

    /*
     * Print Information about a Delivery Request
     */
    public void printInfo() {
        // Fill in the code
        // Use inheritance to first print info about a basic service request
        super.printInfo();
        // Then print specific subclass info
        System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId);
    }

}
