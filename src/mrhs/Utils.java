package mrhs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Daniel Jahodka
 */
public class Utils {

    protected static boolean checkBlocksBounds(MrhsSystem system, String sender, int... indices) {
        for (int i : indices) {
            if (i < 0 || i >= system.getnBlocks()) {
                System.err.println(sender + " in System: Input indices are out of bounds.");
                System.err.println("System has " + system.getnBlocks() + " blocks.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkRowsBounds(MrhsSystem system, String sender, int... indices) {
        for (int i : indices) {
            if (i < 0 || i >= system.getnRows()) {
                System.err.println(sender + " in System: Input indices are out of bounds.");
                System.err.println("System has " + system.getnRows() + " rows.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkColumnsBounds(MrhsEquation equation, String sender, int... indices) {
        for (int i : indices) {
            if (i < 0 || i >= equation.getnCols()) {
                System.err.println(sender + " in Equation: Input indices are out of bounds.");
                System.err.println("This equation has " + equation.getnCols() + " columns.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkPermutation(MrhsSystem system, List<Integer> permutation, String sender) {
        if (system.getnBlocks() != permutation.size()) {
            System.err.println(sender + ": Permutation's length does not match number of blocks.");
            return false;
        }
        List<Integer> sortedPerm = new ArrayList<>();
        for (Integer i : permutation) {
            sortedPerm.add(i);
        }
        Collections.sort(sortedPerm);
        for (int i = 0; i < sortedPerm.size(); i++) {
            if (sortedPerm.get(i) != i) {
                System.err.println(sender + ": Wrong permutation.");
                return false;
            }
        }
        return true;
    }

    protected static ArrayList<MrhsEquation> copyEquationsValues(ArrayList<MrhsEquation> equations) {
        ArrayList<MrhsEquation> result = new ArrayList<>();
        for (MrhsEquation eq : equations) {
            MrhsEquation copyEq = copyEquationValue(eq);
            result.add(copyEq);
        }
        return result;
    }

    protected static MrhsEquation copyEquationValue(MrhsEquation equation) {
        MrhsEquation copyEq = new MrhsEquation();

        copyEq.setnRows(equation.getnRows());
        copyEq.setnCols(equation.getnCols());
        copyEq.setnRHS(equation.getnRHS());

        ArrayList<ArrayList<Integer>> copyLhs = copyHandValues(equation.getLeftSide());
        copyEq.setLeftSide(copyLhs);
        ArrayList<ArrayList<Integer>> copyRhs = copyHandValues(equation.getRightSide());
        copyEq.setRightSide(copyRhs);

        return copyEq;
    }

    protected static ArrayList<ArrayList<Integer>> copyHandValues(ArrayList<ArrayList<Integer>> hand) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>(hand.size());
        for (ArrayList<Integer> row : hand) {
            ArrayList<Integer> copyRow = new ArrayList<>();
            for (Integer val : row) {
                copyRow.add(val);
            }
            result.add(copyRow);
        }
        return result;
    }

    protected static MrhsSystem copySystemValues(MrhsSystem system){
        MrhsSystem result = new MrhsSystem();
        
        result.setnBlocks(system.getnBlocks());
        result.setnRows(system.getnRows());
        result.setIsSystemLoaded(system.isSystemLoaded());
        
        ArrayList<MrhsEquation> equations = copyEquationsValues(system.getSystem());
        result.setSystem(equations);
        
        return result;
    }
    
    protected static Integer factorial(int n){
        Integer result = 1;
        for(int i = 1; i <= n; i++){
            result = result * i;
        }
        return result;
    }
    
}
