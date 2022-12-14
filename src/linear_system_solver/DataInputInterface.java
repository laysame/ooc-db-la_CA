package linear_system_solver;

public interface DataInputInterface {
    /**
     * @param prompt   Prompt as a String
     * @param minValue Minimum value expected from the user as an integer
     * @param maxValue Maximum value expected from the user as an integer
     * @return a valid number as an integer
     */
    public int askInteger(String prompt, int minValue, int maxValue);

    /**
     * Asks the user to input a number within a range and return a double value
     * If is not valid, keep asking
     *
     * @param prompt   -- Prompt as a String
     * @return -- valid number as an integer as double
     */

    public double askDouble(String prompt);
}
