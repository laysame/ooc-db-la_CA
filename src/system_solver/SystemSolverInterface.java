package system_solver;

import java.util.List;


public interface SystemSolverInterface {
    int getNumOfVariables();

    List<Double> getCoefficients(int variableQtt);

    List<Double> solveTwoEquationSystem(List<Double> coefficients1, List<Double> coefficients2);

    List<Double> solveThreeEquationSystem(List<Double> coefficients1, List<Double> coefficients2, List<Double> coefficients3);

    double getTwoByTwoMatrixDeterminant(double a, double b, double c, double d);

    double getThreeByThreeMatrixDeterminant(double a1, double a2, double a3, double c1, double c2, double c3);

    void printEquation(List<Double> equation);

    void printSolution(List<Double> solution);
}
