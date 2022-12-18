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

import java.util.ArrayList;
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
        // Displays Main Menu to the application
        // After select an option as integer, displays the appropriate page
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
        // Displays Menu Action to admin user only
        // After select an option as integer, displays the appropriate page
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
                showAdminOperationsPage();
                break;
            }

            case 4: {
                System.exit(0);
            }
        }
    }

    private void showRegularUserActionMenu() {
        // Displays Menu Action to regular users only
        // After select an option as integer, displays the appropriate page
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
        // Displays Login Page
        System.out.println("==== Login Page ====");
        // Gets user's username and password and authenticate them
        // If user or password is wrong, an exception will be thrown
        // If user pass authentication, displays welcome back message
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
        // Displays Registration Page
        System.out.println("==== Registration Page ====");
        System.out.println("Please, enter your personal information.");

        // Ask user details to get the registration done
        // If user already exits, throw appropriate exception
        // If first time registration, displays welcome message
        do {
            String username = dataInput.askString("Username:");
            String password = dataInput.askString("Password:");
            String firstName = dataInput.askString("First name:");
            String lastName = dataInput.askString("Last name:");

            try {
                currentUser = authenticationManager.register(username, password, firstName, lastName);

            } catch (UsernameAlreadyUsedException e) {
                System.err.println("The username already exist, sorry!");
            }
        } while (currentUser == null);

        System.out.println("Welcome aboard, " + currentUser.getFirstName() + "!");

        showActionMenuByAccountType();
    }

    private void showUpdateProfilePage() {
        // Displays Update Profile Page
        System.out.println("==== Update Profile Page ====");
        System.out.println("Please, enter your personal information.");

        String firstName = dataInput.askString("First name:");
        String lastName = dataInput.askString("Last name:");

        // Sets and saves new data to current user
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        databaseManager.updateUser(currentUser);

        System.out.println("Your personal information has been updated!");

        showActionMenuByAccountType();
    }

    private void showDeleteUserPage() {
        // Displays the Delete User Page to Admin user
        System.out.println("==== Delete User Page ====");
        System.out.println("This is the list of the users on the system:");

        // Displays all users and their IDs
        List<User> users = databaseManager.getAllUsers();
        users.forEach((user) -> {
            System.out.println("id=" + user.getUserId() + ") " + user.getFirstName() + " " + user.getLastName());
        });

        System.out.println();

        User user = null;
        // Admin user can select a id and delete its user
        // in case of mistake, -1 can be selected to exit the program and no user will be deleted
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
        // Displays the list of operations made by a regular user
        System.out.println("==== User Operations Page ====");
        System.out.println("This is the operations performed by the user:");

        // Print the list of operations ever made by current user
        List<Operation> operations = databaseManager.getOperationsByUser(currentUser);
        operations.forEach((operation) -> {
            System.out.println("id=" + operation.getOperationId() + ") Equations:");
            System.out.println(operation.getFirstEquation());
            System.out.println(operation.getSecondEquation());

            if (operation.getThirdEquation() != null) {
                System.out.println(operation.getThirdEquation());
            }

            System.out.println("----------");

            List<Double> solution = new ArrayList<>();
            solution.add(operation.getXValue());
            solution.add(operation.getYValue());

            if (operation.getThirdEquation() != null) {
                solution.add(operation.getZValue());
            }

            systemSolver.printSolution(solution);
            System.out.println();
        });

        showActionMenuByAccountType();
    }

    private void showAdminOperationsPage() {
        // Displays the list of operations made by a regular user
        System.out.println("==== User Operations Page ====");
        System.out.println("Please, select the user you want to review:");

        // Displays all users and their IDs
        List<User> users = databaseManager.getAllUsers();
        users.forEach((user) -> {
            System.out.println("id=" + user.getUserId() + ") " + user.getFirstName() + " " + user.getLastName());
        });

        System.out.println();

        User user = null;
        // Admin user can select a id and delete its user
        // in case of mistake, -1 can be selected to exit the program and no user will be deleted
        do {
            try {
                int userId = dataInput.askInteger("Enter the id of the user you want to delete (or -1 to exit):", -1, 100000);

                if (userId == -1) {
                    break;
                }

                user = databaseManager.getUserById(userId);

            } catch (UserDoesNotExistException e) {
                System.err.println("The user does not exist!");
            }
        } while (user == null);

        if (user != null) {
            // Print the list of operations ever made by the selected user
            List<Operation> operations = databaseManager.getOperationsByUser(user);
            operations.forEach((operation) -> {
                System.out.println("id=" + operation.getOperationId() + ") Equations:");
                System.out.println(operation.getFirstEquation());
                System.out.println(operation.getSecondEquation());

                if (operation.getThirdEquation() != null) {
                    System.out.println(operation.getThirdEquation());
                }

                System.out.println("----------");

                List<Double> solution = new ArrayList<>();
                solution.add(operation.getXValue());
                solution.add(operation.getYValue());

                if (operation.getThirdEquation() != null) {
                    solution.add(operation.getZValue());
                }

                systemSolver.printSolution(solution);
                System.out.println();
            });
        }

        showActionMenuByAccountType();
    }

    private void showCalculatorPage() {
        // Displays the Calculator Page for a regular user
        Operation operation = new Operation();
        operation.setUser(currentUser);
        operation.setCreatedOn(new Date());

        int numOfVariables = systemSolver.getNumOfVariables();
        // Get the number of variables either 2 or 3 in order to display the appropriate calculator
        // Case 2 the inout format expected is ax + by + c = 0
        // Case 3 the inout format expected is ax + by + cz = 0
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

                operation.setFirstEquation(String.format("%.1fx %+.1fy = %.1f", coefficients1.get(0), coefficients1.get(1), coefficients1.get(2)));
                operation.setSecondEquation(String.format("%.1fx %+.1fy = %.1f", coefficients2.get(0), coefficients2.get(1), coefficients2.get(2)));
                operation.setThirdEquation(null);
                operation.setXValue(solution.get(0));
                operation.setYValue(solution.get(1));

                databaseManager.addOperation(operation);

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

                operation.setFirstEquation(String.format("%.1fx %+.1fy %+.1fy = %.1f", coefficients1.get(0), coefficients1.get(1), coefficients1.get(2), coefficients1.get(3)));
                operation.setSecondEquation(String.format("%.1fx %+.1fy %+.1fy = %.1f", coefficients2.get(0), coefficients2.get(1), coefficients2.get(2), coefficients2.get(3)));
                operation.setThirdEquation(String.format("%.1fx %+.1fy %+.1fy = %.1f", coefficients3.get(0), coefficients3.get(1), coefficients3.get(2), coefficients3.get(3)));

                operation.setXValue(solution.get(0));
                operation.setYValue(solution.get(1));
                operation.setZValue(solution.get(2));

                databaseManager.addOperation(operation);

            } catch (NoSolutionException exception) {
                System.out.println("Unable to solve the system: " + exception.getMessage());
            }
        }

        showActionMenuByAccountType();
    }
}