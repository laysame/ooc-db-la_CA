import authentication.AuthenticationManager;
import authentication.InvalidUsernameOrPasswordException;
import db.DatabaseManager;
import models.User;
import system_solver.NoSolutionException;
import system_solver.SystemOfLinearEquationsSystemSolver;
import system_solver.SystemSolverInterface;

import java.util.List;

public class Main {
    static final String DATABASE_HOST = "localhost";
    static final String DATABASE_USER = "ooc2022";
    static final String DATABASE_PASSWORD = "ooc2022";
    static final String DATABASE_NAME = "system_solver";

    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();

        if (!databaseManager.connect(DATABASE_HOST, DATABASE_USER, DATABASE_PASSWORD, DATABASE_NAME)) {
            throw new RuntimeException("Unable to connect to the database: check your connection!");
        }

        AuthenticationManager authenticationManager = new AuthenticationManager(databaseManager);

        try {
            User user = authenticationManager.authenticate("CCT", "Dublin");

            System.out.println(user.getFirstName());
            System.out.println(user.getAccountType().getDescription());

        } catch (InvalidUsernameOrPasswordException e) {

        }

        // SystemSolverInterface systemSolver = new SystemOfLinearEquationsSystemSolver();
        // int numOfVariables = systemSolver.getNumOfVariables();
        // printIntro(systemSolver, numOfVariables);
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