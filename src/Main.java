import linear_system_solver.Solver;

import java.util.List;

public class Main {
    static List<Double> coefficients1;
    static List<Double> coefficients2;
    static List<Double> coefficients3;

    public static void main(String[] args) {
        Solver calcSolver = new Solver();
        int numOfVariables = calcSolver.getNumOfVariables();
        printIntro(calcSolver, numOfVariables);
    }

    public static void printIntro(Solver calcSolver, int numOfVariables) {
        if (numOfVariables == 2) {
            System.out.println("Insert equations within the format: ax + by + c = 0");
            System.out.println("\n" + "***** Equation A *****");
            List<Double> coefficients1 = calcSolver.getCoefficients(numOfVariables);

            System.out.println("\n" + "***** Equation B *****");
            List<Double> coefficients2 = calcSolver.getCoefficients(numOfVariables);

            System.out.println("Equations");
            calcSolver.printEquation(coefficients1);
            calcSolver.printEquation(coefficients2);
            System.out.println("----------");

            List<Double> solution = calcSolver.solveTwoEquationSystem(coefficients1, coefficients2);
            calcSolver.printSolution(solution);

        } else {
            System.out.println("Insert Equations within the format: ax + by + cz + d = 0");

            System.out.println("\n" + "***** Equation A *****");
            List<Double> coefficients1 = calcSolver.getCoefficients(numOfVariables);

            System.out.println("\n" + "***** Equation B *****");
            List<Double> coefficients2 = calcSolver.getCoefficients(numOfVariables);

            System.out.println("\n" + "***** Equation C *****");
            List<Double> coefficients3 = calcSolver.getCoefficients(numOfVariables);

            System.out.println("Equations");
            calcSolver.printEquation(coefficients1);
            calcSolver.printEquation(coefficients2);
            calcSolver.printEquation(coefficients3);
            System.out.println("----------");

            List<Double> solution = calcSolver.solveThreeEquationSystem(coefficients1, coefficients2, coefficients3);
            calcSolver.printSolution(solution);

        }
    }
}