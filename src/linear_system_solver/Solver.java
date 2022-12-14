package linear_system_solver;

import java.util.HashMap;
import java.util.Map;

public class Solver implements SolverInterface {
    public double a, b, c, d;
    DataInput dataInput = new DataInput();

    @Override
    public int getVariablesQuantity() {
        int variableQtt = dataInput.askInteger("***** Hello :) *****\n" +
                "This is a System Equation Solver for equations of two or three variables only!\n" +
                "You can start using it by typing the quantity of variables for both equations:\n", 2, 3);
        return variableQtt;
    }

    public Map<String, Number> getCoefficients(int variableQtt) {
        Map<String, Number> coefficients;
        coefficients = new HashMap<>();
        a = dataInput.askDouble("a=");
        b = dataInput.askDouble("b=");
        c = dataInput.askDouble("c=");
        // c2 = dataInput.askDouble("c (Equation 2)=");
        coefficients.put("a", a);
        coefficients.put("b", b);
        coefficients.put("c", c);
        if (variableQtt == 3) {
            d = dataInput.askDouble("d=");
            coefficients.put("d", d);
        }
        System.out.println(coefficients);

        return coefficients;
    }

    public void getMatrix() {

    }


}
