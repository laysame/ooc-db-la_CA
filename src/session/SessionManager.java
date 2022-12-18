package session;

import authentication.AuthenticationManager;
import authentication.InvalidUsernameOrPasswordException;
import authentication.UsernameAlreadyUsedException;
import db.DatabaseManager;
import models.Operation;
import models.User;
import models.UserDoesNotExistException;
import system_solver.DataInput;
import system_solver.NoSolutionException;
import system_solver.SystemOfLinearEquationsSystemSolver;
import system_solver.SystemSolverInterface;

import java.util.Date;
import java.util.List;

public class SessionManager {
    private DatabaseManager databaseManager;

    private AuthenticationManager authenticationManager;

    private SystemSolverInterface systemSolver;

    private DataInput dataInput;

    private User currentUser = null;

    public SessionManager(DatabaseManager databaseManager, AuthenticationManager authenticationManager) {
        this.databaseManager = databaseManager;
        this.authenticationManager = authenticationManager;
        this.systemSolver = new SystemOfLinearEquationsSystemSolver();
        this.dataInput = new DataInput();
    }

    public void start() {
        showMainMenu();
    }

    private void showMainMenu() {
        System.out.println("==== Main Menu ====");
        System.out.println("Select an option:");
        System.out.println("1. Login to the system");
        System.out.println("2. Register a new user");
        System.out.println("3. Exit");

        int choice = dataInput.askInteger("Enter your choice:", 1, 3);

        switch (choice) {
            case 1: {
                showLoginPage();
                break;
            }

            case 2: {
                showRegistrationPage();
                break;
            }

            case 3: {
                System.exit(0);
            }
        }
    }

    private void showAdminActionMenu() {
        System.out.println("==== Action Menu ====");
        System.out.println("Select an option:");
        System.out.println("1. Update profile");
        System.out.println("2. Delete user");
        System.out.println("3. Review operations");
        System.out.println("4. Exit");

        int choice = dataInput.askInteger("Enter your choice:", 1, 4);

        switch (choice) {
            case 1: {
                showUpdateProfilePage();
                break;
            }

            case 2: {
                showDeleteUserPage();
                break;
            }

            case 3: {
                // TODO
                break;
            }

            case 4: {
                System.exit(0);
            }
        }
    }

    private void showRegularUserActionMenu() {
        System.out.println("==== Action Menu ====");
        System.out.println("Select an option:");
        System.out.println("1. Update profile");
        System.out.println("2. Use calculator");
        System.out.println("3. Review operations");
        System.out.println("4. Exit");

        int choice = dataInput.askInteger("Enter your choice:", 1, 4);

        switch (choice) {
            case 1: {
                showUpdateProfilePage();
                break;
            }

            case 2: {
                showCalculatorPage();
                break;
            }

            case 3: {
                showUserOperationsPage();
                break;
            }

            case 4: {
                System.exit(0);
            }
        }
    }

    private void showActionMenuByAccountType() {
        if (currentUser.getAccountType().getAccountId() == 1) {
            showAdminActionMenu();
        } else {
            showRegularUserActionMenu();
        }
    }

    private void showLoginPage() {
        System.out.println("==== Login Page ====");

        do {
            String username = dataInput.askString("Username:");
            String password = dataInput.askString("Password:");

            try {
                currentUser = authenticationManager.authenticate(username, password);
            } catch (InvalidUsernameOrPasswordException e) {
                System.err.println("The credentials you've entered are not valid!");
            }
        } while (currentUser == null);

        System.out.println("Welcome back, " + currentUser.getFirstName() + "!");

        showActionMenuByAccountType();
    }

    private void showRegistrationPage() {
        System.out.println("==== Registration Page ====");
        System.out.println("Please, enter your personal information.");

        do {
            String username = dataInput.askString("Username:");
            String password = dataInput.askString("Password:");
            String firstName = dataInput.askString("First name:");
            String lastName = dataInput.askString("Last name:");

            try {
                currentUser = authenticationManager.register(username, password, firstName, lastName);
                ;
            } catch (UsernameAlreadyUsedException e) {
                System.err.println("The username already exist, sorry!");
            }
        } while (currentUser == null);

        System.out.println("Welcome aboard, " + currentUser.getFirstName() + "!");

        showActionMenuByAccountType();
    }

    private void showUpdateProfilePage() {
        System.out.println("==== Update Profile Page ====");
        System.out.println("Please, enter your personal information.");

        String firstName = dataInput.askString("First name:");
        String lastName = dataInput.askString("Last name:");

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        databaseManager.updateUser(currentUser);

        System.out.println("Your personal information has been updated!");

        showActionMenuByAccountType();
    }

    private void showDeleteUserPage() {
        System.out.println("==== Delete User Page ====");
        System.out.println("This is the list of the users on the system:");

        List<User> users = databaseManager.getAllUsers();
        users.forEach((user) -> {
            System.out.println("id=" + user.getUserId() + ") " + user.getFirstName() + " " + user.getLastName());
        });

        System.out.println();

        User user = null;

        do {
            try {
                int userId = dataInput.askInteger("Enter the id of the user you want to delete (or -1 to exit):", -1, 100000);

                if (userId == -1) {
                    break;
                }

                user = databaseManager.getUserById(userId);
                databaseManager.deleteUser(user);
                System.out.println("The user has been deleted!");

            } catch (UserDoesNotExistException e) {
                System.err.println("The user does not exist!");
            }
        } while (user == null);

        showActionMenuByAccountType();
    }

    private void showUserOperationsPage() {
        System.out.println("==== User Operations Page ====");
        System.out.println("This is the operations performed by the user:");

        List<Operation> operations = databaseManager.getOperationsByUser(currentUser);
        operations.forEach((operation) -> {
            System.out.println("id=" + operation.getOperationId() + ") " + operation.getFirstEquation() + ", " + operation.getSecondEquation() + ", " + operation.getThirdEquation() + " => " + operation.getXValue() + ", " + operation.getYValue() + ", " + operation.getZValue());
        });

        System.out.println();

        // TODO: REVIEW THIS
    }

    private void showCalculatorPage() {
        Operation operation = new Operation();
        operation.setUser(currentUser);
        operation.setCreatedOn(new Date());

        int numOfVariables = systemSolver.getNumOfVariables();

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

                operation.setFirstEquation(String.format("%.1fx %+.1fy = %.1f\n", coefficients1.get(0), coefficients1.get(1), coefficients1.get(2)));
                operation.setSecondEquation(String.format("%.1fx %+.1fy = %.1f\n", coefficients2.get(0), coefficients2.get(1), coefficients2.get(2)));

                operation.setXValue(solution.get(0));
                operation.setYValue(solution.get(1));

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

                operation.setFirstEquation(String.format("%.1fx %+.1fy %+.1fy = %.1f\n", coefficients1.get(0), coefficients1.get(1), coefficients1.get(2), coefficients1.get(3)));
                operation.setSecondEquation(String.format("%.1fx %+.1fy %+.1fy = %.1f\n", coefficients2.get(0), coefficients2.get(1), coefficients2.get(2), coefficients2.get(3)));
                operation.setThirdEquation(String.format("%.1fx %+.1fy %+.1fy = %.1f\n", coefficients3.get(0), coefficients3.get(1), coefficients3.get(2), coefficients3.get(3)));

                operation.setXValue(solution.get(0));
                operation.setYValue(solution.get(1));
                operation.setZValue(solution.get(2));

            } catch (NoSolutionException exception) {
                System.out.println("Unable to solve the system: " + exception.getMessage());
            }
        }

        databaseManager.addOperation(operation);

        showActionMenuByAccountType();
    }
}