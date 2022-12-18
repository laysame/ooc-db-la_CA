package system_solver;


import java.util.ArrayList;
import java.util.List;


public class SystemOfLinearEquationsSystemSolver implements SystemSolverInterface {
    DataInput dataInput = new DataInput();

    @Override

    public int getNumOfVariables() {
        int numOfVariables = dataInput.askNumberOfVariables("***** Hello :) *****\n" + "This is a Systems of Linear Equations of two and three variables Solver!\n" + "You can start using it by typing in the quantity of variables for both equations:\n", 2, 3);
        return numOfVariables;
    }

    public List<Double> getCoefficients(int numOfVariables) {
        List<Double> coefficients = new ArrayList<>();
        double a, b, c, d;

        a = dataInput.askDouble("a:");
        b = dataInput.askDouble("b:");
        c = dataInput.askDouble("c:");

        coefficients.add(a);
        coefficients.add(b);

        if (numOfVariables == 2) {
            // Converts the "c" coefficient to its opposite value within the format >> ax+by=-c
            c = -c;
        }

        coefficients.add(c);

        if (numOfVariables == 3) {
            d = dataInput.askDouble("d:");
            // Converts the "d" coefficient to its opposite value within the format >> ax+by+cz=-d
            d = -d;
            coefficients.add(d);
        }

        return coefficients;
    }

    public List<Double> solveTwoEquationSystem(List<Double> coefficients1, List<Double> coefficients2) {
        double row1col1A, row1col2A;
        double row2col1A, row2col2A;
        double row1col1B, row2col1B;

        // Matrix A 2x2
        row1col1A = coefficients1.get(0);
        row1col2A = coefficients1.get(1);
        row2col1A = coefficients2.get(0);
        row2col2A = coefficients2.get(1);

        // Gets the determinant of a matrix 2x2
        double determinant = getTwoByTwoMatrixDeterminant(row1col1A, row1col2A, row2col1A, row2col2A);
        double oneOverDeterminant = 1.0 / determinant;

        // If determinant != 0, calculate the inverse of Matrix A and multiply the result by Matrix B
        // and get the coordinates x and y
        if (determinant == 0) {
            throw new NoSolutionException("Determinant is zero, therefore inverse matrix doesn't exist!");
        }

        // Coefficients a and b from Matrix B 2x2
        row1col1B = coefficients1.get(2);
        row2col1B = coefficients2.get(2);

        // Inverting values to apply formula >> -ba -bc
        row1col2A = -row1col2A; // b
        row2col1A = -row2col1A; // c

        // Values of each position of Inverse Matrix A
        double row2col2Inverse = row2col2A * oneOverDeterminant;
        double row1col2Inverse = row1col2A * oneOverDeterminant;
        double row2col1Inverse = row2col1A * oneOverDeterminant;
        double row1col1Inverse = row1col1A * oneOverDeterminant;

        // Multiplies the inverse of Matrix A by Matrix B
        double x = (row2col2Inverse * row1col1B) + (row1col2Inverse * row2col1B);
        double y = (row2col1Inverse * row1col1B) + (row1col1Inverse * row2col1B);

        List<Double> solution = new ArrayList<>();
        solution.add(x);
        solution.add(y);

        return solution;
    }

    public List<Double> solveThreeEquationSystem(List<Double> coefficients1, List<Double> coefficients2, List<Double> coefficients3) {

        // Matrix A 3x3 - First Row
        double row1col1A = coefficients1.get(0);
        double row1col2A = coefficients1.get(1);
        double row1col3A = coefficients1.get(2);
        // Matrix A 3x3 - Second Row
        double row2col1A = coefficients2.get(0);
        double row2col2A = coefficients2.get(1);
        double row2col3A = coefficients2.get(2);
        // Matrix A 3x3 - Third Row
        double row3col1A = coefficients3.get(0);
        double row3col2A = coefficients3.get(1);
        double row3col3A = coefficients3.get(2);

        //Find Minors of each element
        // Values Matrix M - First Row
        double minorRow1Col1 = getTwoByTwoMatrixDeterminant(row2col2A, row2col3A, row3col2A, row3col3A);
        double minorRow1Col2 = getTwoByTwoMatrixDeterminant(row2col1A, row2col3A, row3col1A, row3col3A);
        double minorRow1Col3 = getTwoByTwoMatrixDeterminant(row2col1A, row2col2A, row3col1A, row3col2A);
        // Values Matrix M - Second Row
        double minorRow2Col1 = getTwoByTwoMatrixDeterminant(row1col2A, row1col3A, row3col2A, row3col3A);
        double minorRow2Col2 = getTwoByTwoMatrixDeterminant(row1col1A, row1col3A, row3col1A, row3col3A);
        double minorRow2Col3 = getTwoByTwoMatrixDeterminant(row1col1A, row1col2A, row3col1A, row3col2A);
        // Values Matrix M - Third Row
        double minorRow3Col1 = getTwoByTwoMatrixDeterminant(row1col2A, row1col3A, row2col2A, row2col3A);
        double minorRow3Col2 = getTwoByTwoMatrixDeterminant(row1col1A, row1col3A, row2col1A, row2col3A);
        double minorRow3Col3 = getTwoByTwoMatrixDeterminant(row1col1A, row1col2A, row2col1A, row2col2A);

        // Find Cofactor
        // Values Matrix C - First Row
        double cofactorRow1Col1 = minorRow1Col1;
        double cofactorRow1Col2 = -minorRow1Col2;
        double cofactorRow1Col3 = minorRow1Col3;
        // Values Matrix C - Second Row
        double cofactorRow2Col1 = -minorRow2Col1;
        double cofactorRow2Col2 = minorRow2Col2;
        double cofactorRow2Col3 = -minorRow2Col3;
        // Values Matrix C - Third Row
        double cofactorRow3Col1 = minorRow3Col1;
        double cofactorRow3Col2 = -minorRow3Col2;
        double cofactorRow3Col3 = minorRow3Col3;

        // Get determinant Matrix 3x3 = Row 1 Matrix A by Row 1 Matrix C
        double determinant = getThreeByThreeMatrixDeterminant(row1col1A, row1col2A, row1col3A, cofactorRow1Col1, cofactorRow1Col2, cofactorRow1Col3);

        if (determinant == 0) {
            throw new NoSolutionException("Determinant is zero, therefore inverse matrix doesn't exist!");
        }

        double oneOverDeterminant = 1 / determinant;

        // Find Adjoin of Matrix A
        // Values Matrix ADJ - First Row
        double adjoinRow1Col1 = cofactorRow1Col1;
        double adjoinRow1Col2 = cofactorRow2Col1;
        double adjoinRow1Col3 = cofactorRow3Col1;

        // Values Matrix ADJ - Second Row
        double adjoinRow2Col1 = cofactorRow1Col2;
        double adjoinRow2Col2 = cofactorRow2Col2;
        double adjoinRow2Col3 = cofactorRow3Col2;

        // Values Matrix ADJ - Third Row
        double adjoinRow3Col1 = cofactorRow1Col3;
        double adjoinRow3Col2 = cofactorRow2Col3;
        double adjoinRow3Col3 = cofactorRow3Col3;

        // Find Inverse Matrix A
        // Values Inverse Matrix A - First Row
        double row1col1Inverse = oneOverDeterminant * adjoinRow1Col1;
        double row1col2Inverse = oneOverDeterminant * adjoinRow1Col2;
        double row1col3Inverse = oneOverDeterminant * adjoinRow1Col3;
        // Values Inverse Matrix A - Second Row
        double row2col1Inverse = oneOverDeterminant * adjoinRow2Col1;
        double row2col2Inverse = oneOverDeterminant * adjoinRow2Col2;
        double row2col3Inverse = oneOverDeterminant * adjoinRow2Col3;
        // Values Inverse Matrix A - Third Row
        double row3col1Inverse = oneOverDeterminant * adjoinRow3Col1;
        double row3col2Inverse = oneOverDeterminant * adjoinRow3Col2;
        double row3col3Inverse = oneOverDeterminant * adjoinRow3Col3;


        // Matrix B
        double row1col1B = coefficients1.get(3);
        double row2col1B = coefficients2.get(3);
        double row3col1B = coefficients3.get(3);

        double x = (row1col1Inverse * row1col1B) + (row1col2Inverse * row2col1B) + (row1col3Inverse * row3col1B);
        double y = (row2col1Inverse * row1col1B) + (row2col2Inverse * row2col1B) + (row2col3Inverse * row3col1B);
        double z = (row3col1Inverse * row1col1B) + (row3col2Inverse * row3col1B) + (row3col3Inverse * row3col1B);

        List<Double> solution = new ArrayList<>();
        solution.add(x);
        solution.add(y);
        solution.add(z);

        return solution;
    }

    public double getTwoByTwoMatrixDeterminant(double a, double b, double c, double d) {
        return (a * d) - (b * c);
    }

    public double getThreeByThreeMatrixDeterminant(double a1, double a2, double a3, double c1, double c2, double c3) {
        return (a1 * c1) + (a2 * c2) + (a3 * c3);
    }

    @Override
    public void printEquation(List<Double> coefficients) {
        double a, b, c, d;

        a = coefficients.get(0);
        b = coefficients.get(1);
        c = coefficients.get(2);

        if (coefficients.size() == 3) {
            System.out.format("%.1fx %+.1fy = %.1f\n", a, b, c);

        } else if (coefficients.size() == 4) {
            d = coefficients.get(3);

            System.out.format("%.1fx %+.1fy %+.1fz = %.1f\n", a, b, c, d);
        }
    }

    @Override
    public void printSolution(List<Double> solution) {
        double x, y, z;

        x = solution.get(0);
        y = solution.get(1);

        if (solution.size() == 2) {
            System.out.format("x: %.2f, y: %.2f\n", x, y);

        } else if (solution.size() == 3) {
            z = solution.get(2);
            System.out.format("x: %.2f, y: %.2f, z: %.2f\n", x, y, z);
        }
    }
}
