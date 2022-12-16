package linear_system_solver;

import java.util.ArrayList;
import java.util.List;


public class Solver implements SolverInterface {
    public double a, b, c, d;
    DataInput dataInput = new DataInput();
    List<Double> coefficients;

    @Override
    public int getVariablesQuantity() {
        int variableQtt = dataInput.askInteger("***** Hello :) *****\n" + "This is a Systems of Linear Equations of two and three variables Solver!\n" + "You can start using it by typing in the quantity of variables for both equations:\n", 2, 3);
        return variableQtt;
    }

    public List<Double> getCoefficients(int variableQtt) {
        coefficients = new ArrayList<>();
        a = dataInput.askDouble("a:");
        b = dataInput.askDouble("b:");
        c = dataInput.askDouble("c:");
        coefficients.add(a);
        coefficients.add(b);
        // Converts the "c" coefficient to its opposite value within the format >> ax+by=-c
            // The format to get the form for a matrix calculation
        c = -c;
        coefficients.add(c);
        if (variableQtt == 3) {
            d = dataInput.askDouble("d:");
            coefficients.add(d);
        }
        return coefficients;
    }

    @Override
    public void createEquationTwoVariables() {
        double a = coefficients.get(0);
        double b = coefficients.get(1);
        double c = coefficients.get(2);

        // Prints the Equation
        String coefficientA = a + "x";
        String coefficientB = b + "y";
        if (b > 0) {
            System.out.println(coefficientA + "+" + coefficientB + "=" + c);
        } else {
            System.out.println(coefficientA + "" + coefficientB + "=" + c);
        }
    }


    @Override
    public void createEquationThreeVariables() {
    }

    @Override
    public void printMatrix() {

    }
}
