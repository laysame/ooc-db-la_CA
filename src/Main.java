import system_solver.NoSolutionException;
import system_solver.SystemOfLinearEquationsSystemSolver;
import system_solver.SystemSolverInterface;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SystemSolverInterface systemSolver = new SystemOfLinearEquationsSystemSolver();
        int numOfVariables = systemSolver.getNumOfVariables();
        printIntro(systemSolver, numOfVariables);
    }

    public static void printIntro(SystemSolverInterface systemSolver, int numOfVariables) {
        if (numOfVariables == 2) {
            System.out.println("Insert equations within the format: ax + by + c = 0");
            System.out.println("\n" + "***** Equation A *****");
            List<Double> coefficients1 = systemSolver.getCoefficients(numOfVariables);

            System.out.println("\n" + "***** Equation B *****");
            List<Double> coefficients2 = systemSolver.getCoefficients(numOfVariables);

            System.out.println("Equations");
            systemSolver.printEquation(coefficients1);
            systemSolver.printEquation(coefficients2);
            System.out.println("----------");

            try {
                List<Double> solution = systemSolver.solveTwoEquationSystem(coefficients1, coefficients2);
                systemSolver.printSolution(solution);
            } catch (NoSolutionException exception) {
                System.out.println("Unable to solve the system: " + exception.getMessage());
            }

        } else {
            System.out.println("Insert Equations within the format: ax + by + cz + d = 0");

            System.out.println("\n" + "***** Equation A *****");
            List<Double> coefficients1 = systemSolver.getCoefficients(numOfVariables);

            System.out.println("\n" + "***** Equation B *****");
            List<Double> coefficients2 = systemSolver.getCoefficients(numOfVariables);

            System.out.println("\n" + "***** Equation C *****");
            List<Double> coefficients3 = systemSolver.getCoefficients(numOfVariables);

            System.out.println("Equations");
            systemSolver.printEquation(coefficients1);
            systemSolver.printEquation(coefficients2);
            systemSolver.printEquation(coefficients3);
            System.out.println("----------");

            try {
                List<Double> solution = systemSolver.solveThreeEquationSystem(coefficients1, coefficients2, coefficients3);
                systemSolver.printSolution(solution);
            } catch (NoSolutionException exception) {
                System.out.println("Unable to solve the system: " + exception.getMessage());
            }
        }
    }
}