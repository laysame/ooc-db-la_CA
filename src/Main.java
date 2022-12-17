import authentication.AuthenticationManager;
import authentication.InvalidUsernameOrPasswordException;
import db.DatabaseManager;
import models.Operation;
import models.User;
import system_solver.NoSolutionException;
import system_solver.SystemSolverInterface;

import java.util.ArrayList;
import java.util.Date;
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

        authenticationManager.register("pinuccio123", "password1234", "Pino", "Cammino");

        try {
            //User user = authenticationManager.authenticate("CCT", "Dublin");
            User user = authenticationManager.authenticate("pinuccio123", "password1234");

            System.out.println(user.getFirstName());
            System.out.println(user.getAccountType().getDescription());

        } catch (InvalidUsernameOrPasswordException e) {

        }

        List<User> users = databaseManager.getAllUsers();
        users.forEach((n) -> System.out.println(n.getFirstName()));


//        User john = databaseManager.getUserByUsername("John101");
//        john.setFirstName("Giacomo");
//
//        databaseManager.updateUser(john);
//        databaseManager.deleteUser(john);

        User john = databaseManager.getUserByUsername("John101");

        List<Double> results = new ArrayList<>();
        results.add(4.0);
        results.add(7.0);

        Operation operation = new Operation();
        operation.setUser(john);
        operation.setFirstEquation("2x + 3y = 2");
        operation.setSecondEquation("6x + 2y = 0");
        operation.setThirdEquation("");
        operation.setXValue(5.0);
        operation.setYValue(11.0);
        operation.setZValue(9.0);
        operation.setCreatedOn(new Date());

        databaseManager.addOperation(operation);

        List<Operation> operations = databaseManager.getOperationsByUser(john);
        operations.forEach((n) -> System.out.println(n.getOperationId()));

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