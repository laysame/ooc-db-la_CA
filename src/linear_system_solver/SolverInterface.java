package linear_system_solver;
import java.util.List;


public interface SolverInterface {
    int getNumOfVariables();
    List<Double> getCoefficients(int variableQtt);
    List<Double> solveTwoEquationSystem(List<Double> coefficients1, List<Double> coefficients2);
    double getDeterminant(double a, double b, double c, double d);
    double getDeterminantMatrixThreeByThree(double a1, double a2, double a3, double c1, double c2, double c3);
    void printEquation(List<Double> equation);
    void printSolution(List<Double> solution);
}
