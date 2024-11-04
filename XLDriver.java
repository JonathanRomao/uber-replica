/*
 * Name:        Jonathan Romao
*/

/*
 * This extends the Driver class for the abilty for users to request a ride with more than one passenger 
 */

public class XLDriver extends Driver {

   private int carSize;

   public XLDriver(String id, String name, String carModel, String licensePlate, int carSize, String address) {
      super(id, name, carModel, licensePlate, address);
      this.setType("XL");
      this.carSize = carSize;
   }

   // prints the info for an XL driver
   public void printInfo() {
      super.printInfo();
      System.out.printf("Car Size: %-5d", carSize);
   }

   // getters and setters
   public int getCarSize() {
      return carSize;
   }

   public void setCarSize(int carSize) {
      this.carSize = carSize;
   }

}
