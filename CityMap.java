import java.util.Arrays;
/*
 * Name:        Jonathan Romao
 * 
 * Student ID:  501249734
*/

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap {
   // Checks for string consisting of all digits
   // An easier solution would use String method matches()
   private static boolean allDigits(String s) {
      for (int i = 0; i < s.length(); i++)
         if (!Character.isDigit(s.charAt(i)))
            return false;
      return true;
   }

   // Get all parts of address string
   // An easier solution would use String method split()
   // Other solutions are possible - you may replace this code if you wish
   private static String[] getParts(String address) {
      String parts[] = new String[3];

      if (address == null || address.length() == 0) {
         parts = new String[0];
         return parts;
      }

      // I used the split method to split the address into it's parts
      parts = address.split(" ");

      // resizes the array to match the size of parts
      if (parts.length == 1)
         parts = Arrays.copyOf(parts, 1);
      else if (parts.length == 2)
         parts = Arrays.copyOf(parts, 2);
      return parts;
   }

   // Checks for a valid address
   public static boolean validAddress(String address) {
      // Fill in the code
      // Make use of the helper methods above if you wish
      // There are quite a few error conditions to check for
      // e.g. number of parts != 3
      String[] parts = getParts(address);

      // length check
      if (parts.length != 3)
         return false;

      // first part checks (all digits and length)
      if (!allDigits(parts[0]) || parts[0].length() != 2)
         return false;

      // second part check (length)
      if (parts[1].length() != 3)
         return false;

      // splitting the second part (nth) into 2
      String num = parts[1].substring(0, 1);
      String suffix = parts[1].substring(1);
      // second part checks (ending and digit)
      if (!allDigits(num)
            && !(suffix.equals("st") || suffix.equals("nd") || suffix.equals("rd") || suffix.equals("th")))
         return false;

      // third part check (avenue or street)
      if (!(parts[2].equalsIgnoreCase("AVENUE") || parts[2].equalsIgnoreCase("STREET")))
         return false;

      return true;
   }

   // gets the city zone that the given address would be located in
   public static int getCityZone(String address) {
      if (!validAddress(address)) {
         return -1;
      }
      int[] block = getCityBlock(address);
      // i = 0 (avenue), i = 1 (street)
      if ((block[0] >= 1 && block[0] <= 5) && (block[1] >= 6 && block[1] <= 9)) {
         return 0;
      }
      if ((block[0] >= 6 && block[0] <= 9) && (block[1] >= 6 && block[1] <= 9)) {
         return 1;
      }
      if ((block[0] >= 6 && block[0] <= 9) && (block[1] >= 1 && block[1] <= 5)) {
         return 2;
      }
      if ((block[0] >= 1 && block[0] <= 5) && (block[1] >= 1 && block[1] <= 5)) {
         return 3;
      }
      return -1;
   }

   // Computes the city block coordinates from an address string
   // returns an int array of size 2. e.g. [3, 4]
   // where 3 is the avenue and 4 the street
   // See comments at the top for a more detailed explanation
   public static int[] getCityBlock(String address) {
      int[] block = { -1, -1 };
      String[] parts = getParts(address);

      // street ending ([m, n] "mx nth street")
      if (parts[2].equalsIgnoreCase("STREET")) {
         block[0] = Integer.valueOf(parts[0].substring(0, 1));
         block[1] = Integer.valueOf(parts[1].substring(0, 1));
      }

      // avenue ending ([n, m] "mx nth avenue")
      else if (parts[2].equalsIgnoreCase("AVENUE")) {
         block[1] = Integer.valueOf(parts[0].substring(0, 1));
         block[0] = Integer.valueOf(parts[1].substring(0, 1));
      }
      return block;
   }

   // Calculates the distance in city blocks between the 'from' address and 'to'
   // address
   // Hint: be careful not to generate negative distances

   // This skeleton version generates a random distance
   // If you do not want to attempt this method, you may use this default code
   public static int getDistance(String from, String to) {

      // retrieves the blocks
      int[] from_block = getCityBlock(from);
      int[] to_block = getCityBlock(to);

      // calculates the distances for avenue, street and return the sum
      int avenue_dist = Math.abs(from_block[0] - to_block[0]);
      int street_dist = Math.abs(from_block[1] - to_block[1]);
      int dist = avenue_dist + street_dist;

      return dist;
   }
}
