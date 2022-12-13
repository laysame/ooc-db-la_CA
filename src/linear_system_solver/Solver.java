package linear_system_solver;

import java.util.Scanner;

public class Solver {
    /**
     * @param prompt   Prompt as a String
     * @param minValue Minimum value expected from the user as an integer
     * @param maxValue Maximum value expected from the user as an integer
     * @return the number as an integer
     */
    public int askInteger(String prompt, int minValue, int maxValue) {
        Scanner userInput = new Scanner(System.in);
        int number = -1;
        boolean valid = false;

        do {
            System.out.println(prompt);
            try {
                number = Integer.parseInt(userInput.nextLine());
                // check the range
                if ((number < minValue) || (number > maxValue)) {
                    System.out.println("ERROR >> Invalid number");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("ERROR >> Insert a integer value. Please, try again!");
            }
        } while (!valid);

        return number;
    }

    public void linear_calc_solver() {
        char[] var = {'x', 'y', 'z'};
        int input = askInteger("Enter the number of variables in the equations: ", 2, 3);

        switch (input) {

            case 2:
                System.out.println("Enter the coefficients 'a', 'b' and 'c' for each equations");
                System.out.println("ax + by = c");
                break;
            case 3:
                System.out.println("Enter the coefficients 'a', 'b','c' and 'd' for each equations");
                System.out.println("ax + by + cz = d");
                break;
            default:
        }

    }
}
