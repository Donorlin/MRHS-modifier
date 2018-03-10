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

    protected static List<MrhsEquation> copyEquationsValues(List<MrhsEquation> equations) {
        List<MrhsEquation> result = new ArrayList<>();
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

        List<List<Integer>> copyLhs = copyHandValues(equation.getLeftSide());
        copyEq.setLeftSide(copyLhs);
        List<List<Integer>> copyRhs = copyHandValues(equation.getRightSides());
        copyEq.setRightSides(copyRhs);

        return copyEq;
    }

    protected static List<List<Integer>> copyHandValues(List<List<Integer>> hand) {
        List<List<Integer>> result = new ArrayList<>(hand.size());
        for (List<Integer> row : hand) {
            List<Integer> copyRow = new ArrayList<>();
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
        
        List<MrhsEquation> equations = copyEquationsValues(system.getSystem());
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
