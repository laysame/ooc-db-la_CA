package system_solver;

public interface DataInputInterface {
    /**
     * @param prompt   Prompt as a String
     * @param minValue Minimum value expected from the user as an integer
     * @param maxValue Maximum value expected from the user as an integer
     * @return the number of variables for the system equations either 2 or 3
     */
    public int askNumberOfVariables(String prompt, int minValue, int maxValue);

    /**
     * Asks the user to input a number within a range and return a integer value
     * If is not valid, keep asking
     *
     * @param prompt   -- Prompt as a String
     * @return -- valid number as an integer
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

    /**
     * Asks the user to input a text
     *
     * @param prompt   -- Prompt as a String
     * @return -- input text from the user
     */
    public String askString(String prompt);
}
