package mrhs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Daniel Jahodka
 */
public class MrhsSystem {

    private boolean isSystemLoaded;

    private int nRows;
    private int nBlocks;
    private ArrayList<MrhsEquation> system;

    public MrhsSystem() {
        isSystemLoaded = false;
    }

    protected void setnRows(int nRows) {
        this.nRows = nRows;
    }

    protected void setnBlocks(int nBlocks) {
        this.nBlocks = nBlocks;
    }

    protected void setSystem(ArrayList<MrhsEquation> system) {
        this.system = system;
    }

    public void setIsSystemLoaded(boolean isSystemLoaded) {
        this.isSystemLoaded = isSystemLoaded;
    }

    protected int getnRows() {
        return nRows;
    }

    protected int getnBlocks() {
        return nBlocks;
    }

    public ArrayList<MrhsEquation> getSystem() {
        return system;
    }

    public boolean isSystemLoaded() {
        return isSystemLoaded;
    }

    private boolean checkSizes() {
        checkNumberOfBlocks();
        if (!checkEquationSizes()) {
            return false;
        }
        if (!checkNumberOfRows()) {
            return false;
        }
        return true;
    }

    private boolean checkNumberOfRows() {
        int rowFirst = system.get(0).getnRows();
        for (MrhsEquation e : system) {
            if (rowFirst != e.getnRows()) {
                return false;
            }
        }
        if (nRows != system.get(0).getnRows()) {
            nRows = system.get(0).getnRows();
        }
        return true;
    }

    private boolean checkEquationSizes() {
        for (MrhsEquation e : system) {
            if (!e.checkSizes()) {
                return false;
            }
        }
        return true;
    }

    private void checkNumberOfBlocks() {
        if (nBlocks != system.size()) {
            nBlocks = system.size();
        }
    }

    /**
     * Swaps i-th block with j-th block.
     *
     * @param i i-th block to swap
     * @param j j-th block to swap
     */
    public void swapBlocks(int i, int j) {
        i = Math.floorMod(i, system.size());
        j = Math.floorMod(j, system.size());
        Collections.swap(system, i, j);
    }

    public void deleteBlock(int i) {
        if (Utils.checkBlocksBounds(this, "deleteblock", i)) {
            system.remove(i);
            nBlocks--;
        }
    }

    public void permutateBlocks() {
        Collections.shuffle(system);
    }

    public void permutateBlocks(String permutationFileName) {
        MrhsReader read = new MrhsReader();
        List<Integer> permutation = read.readPermutationFromFile(permutationFileName);
        if (permutation != null && Utils.checkPermutation(this, permutation, "permutateblocks")) {
            Map<MrhsEquation, Integer> ordering = new HashMap<>();
            for (int i = 0; i < permutation.size(); i++) {
                MrhsEquation eq = system.get(permutation.get(i));
                ordering.put(eq, i);
            }
            Collections.sort(system, new MrhsEquationComparator(ordering));
        }
    }

    /**
     * XOR row to with row toAdd.
     *
     * @param to
     * @param toAdd
     */
    public void addRow(int to, int toAdd) {
        if (Utils.checkRowsBounds(this, "addrow", to, toAdd)) {
            for (int i = 0; i < system.size(); i++) {
                for (int j = 0; j < system.get(i).getLeftSide().get(0).size(); j++) {
                    int valueTo = system.get(i).getLeftSide().get(to).get(j);
                    int valueToAdd = system.get(i).getLeftSide().get(toAdd).get(j);
                    system.get(i).getLeftSide().get(to).set(j, valueTo ^ valueToAdd);
                }
            }
        }
    }

    /**
     * Swap i-th row with j-th row.
     *
     * @param i i-th row
     * @param j j-th row
     */
    public void swapRows(int i, int j) {
        if (Utils.checkRowsBounds(this, "swaprow", i, j)) {
            for (int k = 0; k < nBlocks; k++) {
                Collections.swap(system.get(k).getLeftSide(), i, j);
            }
        }
    }

    public void deleteRow(int i) {
        if (Utils.checkRowsBounds(this, "deleterow", i)) {
            for (MrhsEquation e : system) {
                e.getLeftSide().remove(i);
                e.setnRows(e.getnRows() - 1);
            }
            nRows--;
        }
    }

    public void swapCols(int iB, int i, int j) {
        if (Utils.checkBlocksBounds(this, "swapcols", iB)) {
            system.get(iB).swapCols(i, j);
        }
    }

    public void addCol(int iB, int i, int j) {
        if (Utils.checkBlocksBounds(this, "addcol", iB)) {
            system.get(iB).addCol(i, j);
        }
    }

    public void deleteCol(int iB, int i) {
        if (Utils.checkBlocksBounds(this, "deletecol", iB)) {
            system.get(iB).deleteCol(i);
        }
    }

    public void glue(int iB, int jB, int keepOld) {        
        iB = Math.floorMod(iB, system.size());        
        jB = Math.floorMod(jB, system.size());
        if(iB == jB){
            return;
        }
        MrhsEquation glued = createGluedBlock(iB, jB);        
        if (keepOld > 0) {
            system.add(glued);
            nBlocks++;
        } else if (keepOld == 0) {
            if (iB < jB) {
                system.remove(jB);
                system.remove(iB);
            } else if (jB < iB) {
                system.remove(iB);
                system.remove(jB);
            }
            if (iB > system.size()) {
                system.add(glued);
            } else {
                system.add(iB, glued);
            }
            nBlocks--;
        }
    }

    private MrhsEquation createGluedBlock(int iB, int jB) {
        MrhsEquation glued = new MrhsEquation();

        glued.setnRows(nRows);
        glued.setnCols(system.get(iB).getnCols() + system.get(jB).getnCols());
        glued.setLeftSide(createGluedLeftHandSide(iB, jB));
        glued.setRightSide(createGluedRightHandSides(iB, jB));
        glued.setnRHS(glued.getRightSide().size());
        glued.normalize();
        
        return glued;
    }

    private ArrayList<ArrayList<Integer>> createGluedLeftHandSide(int iB, int jB) {
        ArrayList<ArrayList<Integer>> newLeftSide = cloneHandValues(system.get(iB).getLeftSide());
        ArrayList<ArrayList<Integer>> leftSideOfJ = cloneHandValues(system.get(jB).getLeftSide());
        
        for (int i = 0; i < leftSideOfJ.size(); i++) {
            ArrayList<Integer> rowFromLeftSideOfJ = leftSideOfJ.get(i);
            newLeftSide.get(i).addAll(rowFromLeftSideOfJ);
        }
        return newLeftSide;
    }

    private ArrayList<ArrayList<Integer>> createGluedRightHandSides(int iB, int jB) {
        ArrayList<ArrayList<Integer>> newRightSides = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rightSidesOfI = cloneHandValues(system.get(iB).getRightSide());
        ArrayList<ArrayList<Integer>> rightSidesOfJ = cloneHandValues(system.get(jB).getRightSide());

        for (ArrayList<Integer> rhsI : rightSidesOfI) {
            for (ArrayList<Integer> rhsJ : rightSidesOfJ) {
                ArrayList<Integer> newRhs = new ArrayList<>();
                newRhs.addAll(rhsI);
                newRhs.addAll(rhsJ);
                newRightSides.add(newRhs);
            }
        }
        return newRightSides;
    }

    private ArrayList<ArrayList<Integer>> cloneHandValues(ArrayList<ArrayList<Integer>> lhs) {
        ArrayList<ArrayList<Integer>> clone = new ArrayList<>(lhs.size());
        for (ArrayList<Integer> row : lhs) {
            ArrayList<Integer> newRow = new ArrayList<>();
            for (Integer i : row) {
                newRow.add(i);
            }
            clone.add(newRow);
        }
        return clone;
    }

    private int findPivot(int fromRow, int inCol) {
        int iBlock = colToBlockNumber(inCol);
        int realCol = colToRealCol(inCol);

        int result = -1;
        for (int i = fromRow; i < system.get(iBlock).getLeftSide().size(); i++) {
            if (system.get(iBlock).getLeftSide().get(i).get(realCol) == 1) {
                return i;
            }
        }
        return result;
    }

    public void normalize(int i) {
        if (Utils.checkBlocksBounds(this, "normalize", i)) {
            system.get(i).normalize();
        }
    }

    private int nCols() {
        int result = 0;
        for (MrhsEquation e : system) {
            result += e.getnCols();
        }
        return result;
    }

    private int colToBlockNumber(int col) {
        int iBlock = 0;
        for (MrhsEquation e : system) {
            if ((col - e.getnCols()) >= 0) {
                col -= e.getnCols();
                iBlock++;
            } else {
                break;
            }
        }
        return iBlock;
    }

    private int colToRealCol(int col) {
        for (MrhsEquation e : system) {
            if ((col - e.getnCols()) >= 0) {
                col -= e.getnCols();
            } else {
                break;
            }
        }
        return col;
    }

    public int gauss() {
        int nPivots = 0;
        int lead = 0;
        int i;

        for (int r = 0; r < nRows; r++) {
            if (nCols() <= lead) {
                return nPivots;
            } else {
                i = r;
            }
            int pivot = -1;
            while ((pivot = findPivot(i, lead)) == -1) {
                lead++;
                if (nCols() == lead) {
                    return nPivots;
                }
            }
            nPivots++;
            swapRows(r, pivot);
            for (int j = 0; j < nRows; j++) {
                if (j != r && (system.get(colToBlockNumber(lead)).getLeftSide().get(j).get(colToRealCol(lead)) == 1)) {
                    addRow(j, r);
                }
            }
            lead++;
        }
        return nPivots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!checkSizes()) {
            System.err.println("Something is wrong with this system, probably some equation has different number of rows than the other, or some row has different number of columns than the other rows.");
            return null;
        }
        sb.append("\n");
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nBlocks; j++) {
                sb.append(system.get(j).getLeftSide().get(i) + " ");
                sb.append("  ");
            }
            sb.append("\n");
        }
        for (int i = 0; i < (2 * nCols() + ((nBlocks - 1) * 3) - nBlocks); i++) {
            sb.append("-");
        }
        sb.append("\n");

        int max = system.get(0).getnRHS();
        for (MrhsEquation eq : system) {
            if (max < eq.getnRHS()) {
                max = eq.getnRHS();
            }
        }

        for (int i = 0; i < max; i++) {
            for (int j = 0; j < nBlocks; j++) {
                ArrayList<ArrayList<Integer>> list = new ArrayList<>(system.get(j).getRightSide());
                if (i >= list.size()) {
                    for (int k = 0; k < 2 * list.get(0).size() - 1; k++) {
                        sb.append(" ");
                    }
                    sb.append("   ");
                    continue;
                }
                sb.append(list.get(i) + " ");
                sb.append("  ");
            }
            sb.append("\n");
        }

        return sb.toString().replaceAll("[^0-9- \\n]", "");
    }

    public String toFileString() {
        String newLineChar = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        if (!checkSizes()) {
            System.err.println("Something is wrong with this system, probably some equation has different number of rows than the other, or some row has different number of columns than the other rows.");
            return null;
        }
        sb.append(nRows + newLineChar);
        sb.append(nBlocks + newLineChar);
        for (MrhsEquation e : system) {
            sb.append(e.getnCols() + " " + e.getnRHS() + newLineChar);
        }
        for (int i = 0; i < nRows; i++) {
            sb.append("[");
            for (int j = 0; j < nBlocks; j++) {
                for (int k = 0; k < system.get(j).getLeftSide().get(i).size(); k++) {
                    sb.append(system.get(j).getLeftSide().get(i).get(k));
                    if (k != (system.get(j).getLeftSide().get(i).size() - 1)) {
                        sb.append(" ");
                    }
                }
                if (j != (nBlocks - 1)) {
                    sb.append("  ");
                }
            }
            sb.append("]" + newLineChar);
        }

        for (MrhsEquation e : system) {
            for (ArrayList<Integer> rs : e.getRightSide()) {
                sb.append(rs + newLineChar);
            }
        }
        return sb.toString().replaceAll("[^0-9-\\[\\] \\n]", "");
    }

    public void infoSystem() {  
        if(!checkSizes()){
            System.err.println("Something is wrong with this system, probably some equation has different number of rows than the other, or some row has different number of columns than the other rows.");
            return;
        }
        for(int i = 0; i < system.size(); i++){
            System.err.println("Equation " + i + " rows " + system.get(i).getnRows() + " cols " + system.get(i).getnCols() + " rhs " + system.get(i).getnRHS());
        }
    }

    public void generateRandomSystem(int nRows, int nBlocks, int nCols, int nRHS, int randomRange) {
        Random rand = new Random(System.currentTimeMillis());
        this.nRows = nRows;
        this.nBlocks = nBlocks;
        system = new ArrayList<>();
        for (int i = 0; i < nBlocks; i++) {
            MrhsEquation eq = new MrhsEquation();
            eq.setnRows(nRows);

            if (randomRange >= 1) {
                nCols = rand.nextInt(nCols) + 1;
                nRHS = rand.nextInt(nRHS) + 1;
            }
            eq.setnCols(nCols);
            eq.generateRandomLeftSide(rand, nRows, nCols);

            eq.setnRHS(nRHS);
            
            eq.generateRandomRightSides(rand, nRHS, nCols);
                
            system.add(eq);
        }
        isSystemLoaded = true;
    }

}
