package linear_system_solver;


import java.util.Map;

public interface SolverInterface {


    public int getVariablesQuantity();
    Map<String, Number> getCoefficients(int variableQtt);
    public void getMatrix();
}
