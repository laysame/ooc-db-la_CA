package linear_system_solver;

import java.util.Scanner;

public class DataInput implements DataInputInterface {
    Scanner userInput = new Scanner(System.in);
    int numberInt = -1;
    double numberDb = -1;

// >> (ax + by + c = 0) /  >> (ax + by + cz + d = 0)

    @Override
    public int askInteger(String prompt, int minValue, int maxValue) {
        boolean valid = false;
        do {
            System.out.println(prompt);
            System.out.println("Type " + "'" + minValue + "'" + " for equations with " + minValue + " variables (x, y)\n" +
                    "or \n" +
                    "Type " + "'" + maxValue + "'" + " for equations with " + maxValue + " variables (x, y and z) \n" +
                    ">>");
            try {
                numberInt = Integer.parseInt(userInput.nextLine());
                // check the range
                if ((numberInt < minValue) || (numberInt > maxValue)) {
                    System.out.println("ERROR >> Invalid number! You must type " + minValue + " or " + maxValue + "! Try again :)");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                System.out.println("ERROR >> Insert a integer value. Please, try again!");
            }
        } while (!valid);

        return numberInt;
    }

    @Override
    public double askDouble(String prompt) {
        boolean valid = false;
        do {
            System.out.println("Insert the value of the coefficient >>");
            System.out.println(prompt);
            try {
                numberDb = Double.parseDouble(userInput.nextLine());
                valid = true;
            } catch (Exception e) {
                System.out.println("ERROR >> Insert a number. Please, try again!");
            }
        } while (!valid);

        return numberDb;
    }
}
