package system_solver;

import java.util.Scanner;

public class DataInput implements DataInputInterface {
    Scanner userInput = new Scanner(System.in);
    int numberInt = -1;
    double numberDb = -1;

// >> (ax + by + c = 0) /  >> (ax + by + cz + d = 0)

    @Override
    public int askNumberOfVariables(String prompt, int minValue, int maxValue) {
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

    public int askInteger(String prompt, int minValue, int maxValue) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;
        int userInput = -1; //defaulted to -1

        do {
            System.out.println(prompt);

            try {
                userInput = Integer.parseInt(input.nextLine());
                // check the range
                if ((userInput < minValue) || (userInput > maxValue)) {
                    System.out.println("Invalid value entered. Please try a number within the range specified");
                } else {
                    valid = true;
                }

            } catch (Exception e) {
                System.out.println("ERROR -- That's not an integer value. Please, try again!");
            }

        } while (!valid);
        // must be a valid number now

        return userInput;
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

    @Override
    public String askString(String prompt) {
        Scanner input = new Scanner(System.in);
        String userInput = "";

        do {
            System.out.println(prompt);
            userInput = input.nextLine();
        } while (userInput.isEmpty());

        return userInput;
    }
}
