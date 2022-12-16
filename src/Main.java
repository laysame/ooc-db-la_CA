import linear_system_solver.Solver;

import java.util.List;

public class Main {
    static List<Double> data1;
    static List<Double> data2;

    public static void main(String[] args) {

        Solver calcSolver = new Solver();
        int variableQtt = calcSolver.getVariablesQuantity();

        System.out.println("\n" + "***** Equation 1 *****");
        System.out.println("Insert within the format: ax + by + c = 0");
        data1 = calcSolver.getCoefficients(variableQtt);
        System.out.println("Equation 1: ");
        calcSolver.createEquationTwoVariables();


        System.out.println("\n" + "***** Equation 2 *****");
        data2 = calcSolver.getCoefficients(variableQtt);
        System.out.println("Equation 2: ");
        calcSolver.createEquationTwoVariables();

        getXYCoordinates();
    }

    public static void getXYCoordinates() {

        // coefficients a,b,c and d from Matrix A
        double a = Double.parseDouble(String.format("%.3f", data1.get(0)));
        double b = Double.parseDouble(String.format("%.3f", data1.get(1)));
        double c = Double.parseDouble(String.format("%.3f", data2.get(0)));
        double d = Double.parseDouble(String.format("%.2f", data2.get(1)));
        // coefficients a and b from Matrix B
        double a2 = Double.parseDouble(String.format("%.3f", data1.get(2)));
        double b2 = Double.parseDouble(String.format("%.3f", data2.get(2)));

        // gets the determinant of a matrix 2x2
        double determinant = (a * d) - (b * c);
        float oneOverDeterminant = (float) ((float) 1 / determinant);

        // checks if the determinant is different from 0
        // if yes, calculate the inverse of Matrix A
        // and multiply the result by Matrix B to get the coordinates x and y
        if (determinant != 0) {
            b = -b;
            c = -c;

            // structure to get the inverse matrix of A
            float d3 = (float) Double.parseDouble(String.format("%.3f", d * oneOverDeterminant));
            float b3 = (float) Double.parseDouble(String.format("%.3f", b * oneOverDeterminant));
            float c3 = (float) Double.parseDouble(String.format("%.3f", c * oneOverDeterminant));
            float a3 = (float) Double.parseDouble(String.format("%.3f", a * oneOverDeterminant));

            System.out.println("*****Coordinates (x,y) *********");
            float x = (float) Double.parseDouble(String.format("%.1f", (d3 * a2) + (b3 * b2)));
            float y = (float) Double.parseDouble(String.format("%.1f", (c3 * a2) + (a3 * b2)));
            System.out.println("x: " + x);
            System.out.println("y: " + y);
        }
    }
}