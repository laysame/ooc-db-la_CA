package system_solver;

import java.util.ArrayList;
import java.util.List;

public class SplitEquationString {
    List<String> result;
    List<String> coefficients;

    public List<String> split(String string) throws NullPointerException {
        if (string == null)
            throw new NullPointerException("the given string is null!");
        result = new ArrayList<>();

        // operators to split upon
        String[] operators = new String[]{"x", "y", "="};

        int index = 0;
        while (index < string.length()) {
            // find the index of the nearest operator
            int minimum = string.length();
            for (String operator : operators) {
                int i = string.indexOf(operator, index);
                if (i > -1)
                    minimum = Math.min(minimum, i);
            }

            // if an operator is found, split the string
            if (minimum < string.length()) {
                result.add(string.substring(index, minimum));
                result.add("" + string.charAt(minimum));
                index = minimum + 1;
            } else {
                result.add(string.substring(index));
                break;
            }
        }

        return result;
    }

    public List<String> getCoefficientsAB() {
        coefficients = new ArrayList<>();
        coefficients.add(result.get(0));
        coefficients.add(result.get(2));
        // checks if the variable x has a number before, if not, 1 will be added
        String coeffA = (result.get(0));
        if (coeffA.compareToIgnoreCase("") == 0) {
            coefficients.remove(coeffA);
            coeffA = Integer.toString(1);
            coefficients.add(0, coeffA);
        }

        // change sign for the numbers
        int coeffC = Integer.parseInt(result.get(4));

        if (coeffC < 0) {
            coeffC = coeffC * -1;
        } else if (coeffC > 0) {
            coeffC = coeffC * -1;
        }
        //System.out.println(coeffC);
        return coefficients;
    }
}
