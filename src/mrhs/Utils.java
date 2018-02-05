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
                System.out.println(sender + " in System: Input indices are out of bounds.");
                System.out.println("System has " + system.getnBlocks() + " blocks.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkRowsBounds(MrhsSystem system, String sender, int... indices) {
        for (int i : indices) {
            if (i < 0 || i >= system.getnRows()) {
                System.out.println(sender + " in System: Input indices are out of bounds.");
                System.out.println("System has " + system.getnRows() + " rows.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkColumnsBounds(MrhsEquation equation, String sender, int... indices) {
        for (int i : indices) {
            if (i < 0 || i >= equation.getnCols()) {
                System.out.println(sender + " in Equation: Input indices are out of bounds.");
                System.out.println("This equation has " + equation.getnCols() + " columns.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkPermutation(MrhsSystem system, List<Integer> permutation, String sender) {
        if (system.getnBlocks() != permutation.size()) {
            System.out.println(sender + ": Permutation's length does not match number of blocks.");
            return false;
        }
        List<Integer> sortedPerm = new ArrayList<>();
        for (Integer i : permutation) {
            sortedPerm.add(i);
        }
        Collections.sort(sortedPerm);
        for (int i = 0; i < sortedPerm.size(); i++) {
            if (sortedPerm.get(i) != i) {
                System.out.println(sender + ": Wrong permutation.");
                return false;
            }
        }
        return true;
    }

}
