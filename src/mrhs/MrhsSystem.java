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

    private int nRows;
    private int nBlocks;
    private List<MrhsEquation> system;
    private boolean isSystemLoaded;

    public MrhsSystem() {
        isSystemLoaded = false;
        system = new ArrayList<>();
    }

    protected void setnRows(int nRows) {
        this.nRows = nRows;
    }

    protected void setnBlocks(int nBlocks) {
        this.nBlocks = nBlocks;
    }

    protected void setSystem(List<MrhsEquation> system) {
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

    public List<MrhsEquation> getSystem() {
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
    public boolean swapBlocks(int i, int j) {
        i = Math.floorMod(i, system.size());
        j = Math.floorMod(j, system.size());
        Collections.swap(system, i, j);
        return true;
    }

    public boolean deleteBlock(int i) {
        if (Utils.checkBlocksBounds(this, "deleteblock", i)) {
            system.remove(i);
            nBlocks--;
            return true;
        }
        return false;
    }

    public boolean permutateBlocks() {
        Collections.shuffle(system);
        return true;
    }

    public boolean permutateBlocks(String permutationFileName) {
        MrhsReader read = new MrhsReader();
        List<Integer> permutation = read.readPermutationFromFile(permutationFileName);
        if (permutation != null && Utils.checkPermutation(this, permutation, "permutateblocks")) {
            Map<MrhsEquation, Integer> ordering = new HashMap<>();
            for (int i = 0; i < permutation.size(); i++) {
                MrhsEquation eq = system.get(permutation.get(i));
                ordering.put(eq, i);
            }
            Collections.sort(system, new MrhsEquationIndexComparator(ordering));
            return true;
        }
        return false;
    }

    /**
     * XOR row to with row toAdd.
     *
     * @param to
     * @param toAdd
     */
    public boolean addRow(int to, int toAdd) {
        if (Utils.checkRowsBounds(this, "addrow", to, toAdd) && (to != toAdd)) {
            for (int i = 0; i < system.size(); i++) {
                for (int j = 0; j < system.get(i).getLeftSide().get(0).size(); j++) {
                    int valueTo = system.get(i).getLeftSide().get(to).get(j);
                    int valueToAdd = system.get(i).getLeftSide().get(toAdd).get(j);
                    system.get(i).getLeftSide().get(to).set(j, valueTo ^ valueToAdd);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Swap i-th row with j-th row.
     *
     * @param i i-th row
     * @param j j-th row
     */
    public boolean swapRows(int i, int j) {
        if (Utils.checkRowsBounds(this, "swaprow", i, j)) {
            for (int k = 0; k < nBlocks; k++) {
                Collections.swap(system.get(k).getLeftSide(), i, j);
            }
            return true;
        }
        return false;
    }

    public boolean deleteRow(int i) {
        if (Utils.checkRowsBounds(this, "deleterow", i)) {
            for (MrhsEquation e : system) {
                e.getLeftSide().remove(i);
                e.setnRows(e.getnRows() - 1);
            }
            nRows--;
            return true;
        }
        return false;
    }

    public boolean swapCols(int iB, int i, int j) {
        if (Utils.checkBlocksBounds(this, "swapcols", iB)) {
            return system.get(iB).swapCols(i, j);
        }
        return false;
    }

    public boolean addCol(int iB, int to, int toAdd) {
        if (Utils.checkBlocksBounds(this, "addcol", iB) && (to != toAdd)) {
            return system.get(iB).addCol(to, toAdd);
        }
        return false;
    }

    public boolean deleteCol(int iB, int i) {
        if (Utils.checkBlocksBounds(this, "deletecol", iB)) {
            return system.get(iB).deleteCol(i);
        }
        return false;
    }
    
    // TODO COMMAND
    public boolean deleteZeroColumns(){
        for(MrhsEquation eq : system){
            eq.deleteZeroColumns();
        }       
        
        return true;
    }

    public boolean glue(int iB, int jB, int keepOld) {
        iB = Math.floorMod(iB, system.size());
        jB = Math.floorMod(jB, system.size());
        
        if(!(keepOld == 0 || keepOld == 1)){
            System.err.println("glue: keepOld should be 0 (false) or 1 (true)");
            return false;
        }
        if (iB == jB) {
            return false;
        }
        MrhsEquation glued = createGluedBlock(iB, jB);
        if(glued.getnRHS() == 0){
            if(keepOld == 1){
                System.err.println("glue: Glued block has zero right-hand sides. Not adding it.");
            }else{
                System.err.println("glue: Glued block has zero right-hand sides. Not replacing old blocks.");
            }            
            return false;
        }
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
        return true;
    }

    private MrhsEquation createGluedBlock(int iB, int jB) {
        MrhsEquation glued = new MrhsEquation();

        glued.setnRows(nRows);
        glued.setnCols(system.get(iB).getnCols() + system.get(jB).getnCols());
        glued.setLeftSide(createGluedLeftHandSide(iB, jB));
        glued.setRightSides(createGluedRightHandSides(iB, jB));
        glued.setnRHS(glued.getRightSides().size());
        glued.normalize();

        return glued;
    }

    private List<List<Integer>> createGluedLeftHandSide(int iB, int jB) {
        List<List<Integer>> newLeftSide = Utils.copyHandValues(system.get(iB).getLeftSide());
        List<List<Integer>> leftSideOfJ = Utils.copyHandValues(system.get(jB).getLeftSide());

        for (int i = 0; i < leftSideOfJ.size(); i++) {
            List<Integer> rowFromLeftSideOfJ = leftSideOfJ.get(i);
            newLeftSide.get(i).addAll(rowFromLeftSideOfJ);
        }
        
        return newLeftSide;
    }

    private List<List<Integer>> createGluedRightHandSides(int iB, int jB) {
        List<List<Integer>> newRightSides = new ArrayList<>();      
        List<List<Integer>> rightSidesOfI = Utils.copyHandValues(system.get(iB).getRightSides());
        List<List<Integer>> rightSidesOfJ = Utils.copyHandValues(system.get(jB).getRightSides());
        
        for (List<Integer> rhsI : rightSidesOfI) {
            for (List<Integer> rhsJ : rightSidesOfJ) {
                List<Integer> newRhs = new ArrayList<>();
                newRhs.addAll(rhsI);
                newRhs.addAll(rhsJ);
                newRightSides.add(newRhs);
            }
        }
                
        return newRightSides;
    }
    
    //TODO COMMAND with system
    public boolean normalizeEquation(int i) {
        if (Utils.checkBlocksBounds(this, "normalize", i)) {
            system.get(i).normalize();
            return true;
        }
        return false;
    }
    
    //TODO COMMAND
    public boolean normalizeSystem(){
        for(MrhsEquation eq : system){
            eq.normalize();
        }
        return true;
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

    protected int gauss() {
        int nPivots = 0;
        int lead = 0;
        int i;
        MrhsSystem sys = Utils.copySystemValues(this);

        for (int r = 0; r < sys.getnRows(); r++) {
            if (sys.nCols() <= lead) {
                return nPivots;
            } else {
                i = r;
            }
            int pivot = -1;
            while ((pivot = sys.findPivot(i, lead)) == -1) {
                lead++;
                if (sys.nCols() == lead) {
                    return nPivots;
                }
            }
            nPivots++;
            sys.swapRows(r, pivot);
            for (int j = 0; j < sys.getnRows(); j++) {
                if (j != r && (sys.getSystem().get(sys.colToBlockNumber(lead)).getLeftSide().get(j).get(sys.colToRealCol(lead)) == 1)) {
                    sys.addRow(j, r);
                }
            }
            lead++;
        }
        return nPivots;
    }

    protected void addBlock(MrhsEquation equation) {
        system.add(equation);
        nBlocks++;
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
                if (i >= system.get(j).getRightSides().size()) {
                    for (int k = 0; k < 2 * system.get(j).getLeftSide().get(0).size() - 1; k++) {
                        sb.append(" ");
                    }
                    sb.append("   ");
                    continue;
                }
                sb.append(system.get(j).getRightSides().get(i) + " ");
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
            for (List<Integer> rs : e.getRightSides()) {
                sb.append(rs + newLineChar);
            }
            sb.append(newLineChar);
        }
        return sb.toString().replaceAll("[^0-9-\\[\\] \\n]", "");
    }

    public void infoSystem() {
        if (!checkSizes()) {
            System.err.println("Something is wrong with this system, probably some equation has different number of rows than the other, or some row has different number of columns than the other rows.");
            return;
        }
        for (int i = 0; i < system.size(); i++) {
            System.err.println("Equation " + i + " rows " + system.get(i).getnRows() + " cols " + system.get(i).getnCols() + " rhs " + system.get(i).getnRHS());
        }
    }

    public boolean generateRandomSystem(int nRows, int nBlocks, int nCols, int nRHS, int randomRange, double density) {
        Random rand = new Random(System.currentTimeMillis());
        this.nRows = nRows;
        this.nBlocks = nBlocks;
        system = new ArrayList<>(nBlocks);

        if(!(randomRange == 0 || randomRange == 1)){
            System.err.println("random: randomRange should be 0 (false) or 1 (true).");
            return false;
        }
        if (density <= 0 || density >= 1) {
            System.err.println("random: Density is not value in (0,1).");
            return false;
        }
        if (nRows == 0 || nBlocks == 0 || nCols == 0 || nRHS == 0) {
            System.err.println("random: Number of equations, rows, columns and number of right-hand sides should be nonzero value.");
            return false;
        }

        while (system.size() != nBlocks) {
            int newnCols = nCols;
            int newnRHS = nRHS;
            MrhsEquation eq = new MrhsEquation();
            eq.setnRows(nRows);

            if (randomRange >= 1) {
                newnCols = rand.nextInt(nCols) + 1;
                newnRHS = rand.nextInt(nRHS) + 1;
                if (Utils.factorial(newnCols) < newnRHS) {
                    newnRHS = Utils.factorial(newnCols);
                }
            }

            eq.setnCols(newnCols);
            eq.generateRandomLeftSide(rand, nRows, newnCols, density);
            eq.setnRHS(newnRHS);
            eq.generateRandomRightSides(rand, newnRHS, newnCols);
            if (eq.normalize() == newnRHS) {
                system.add(eq);
            }

        }

        isSystemLoaded = true;
        return true;
    }

    public boolean guess(int variable, Integer value){
        
        if(!Utils.checkRowsBounds(this, "guess", variable)){
            return false;
        }
        if(!(value == 0 || value == 1)){
            System.err.println("guess: Value of guessed variable should be either 0 or 1.");
            return false;
        }
        
        for(MrhsEquation eq : system){
            eq.guess(variable, value);
        }
        
        return true;
    }
    
}
