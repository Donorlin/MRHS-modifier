package mrhs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Daniel Jahodka
 */
public class MrhsEquation {

    private int nRows;
    private int nCols;
    private int nRHS;
    private Double g;
    private Integer p;
    private List<List<Integer>> leftSide;
    private List<List<Integer>> rightSides;

    protected void setnRHS(int nRHS) {
        this.nRHS = nRHS;
    }

    protected void setnCols(int nCols) {
        this.nCols = nCols;
    }

    protected void setnRows(int nRows) {
        this.nRows = nRows;
    }

    protected void setG(Double g) {
        this.g = g;
    }

    protected void setP(Integer p) {
        this.p = p;
    }

    protected void setRightSides(List<List<Integer>> leftSide) {
        this.rightSides = leftSide;
    }

    protected void setLeftSide(List<List<Integer>> rightSide) {
        this.leftSide = rightSide;
    }

    protected int getnRows() {
        return nRows;
    }

    protected int getnCols() {
        return nCols;
    }

    protected int getnRHS() {
        return nRHS;
    }

    public Double getG() {
        return g;
    }

    public Integer getP() {
        return p;
    }

    protected List<List<Integer>> getLeftSide() {
        return leftSide;
    }

    protected List<List<Integer>> getRightSides() {
        return rightSides;
    }

    protected void generateRandomLeftSide(Random rand, int nRows, int nCols, double density) {
        leftSide = new ArrayList<>();
        for (int j = 0; j < nRows; j++) {
            List<Integer> l = new ArrayList<>();
            for (int k = 0; k < nCols; k++) {
                double den = rand.nextDouble();
                if (den <= density) {
                    l.add(1);
                } else {
                    l.add(0);
                }
            }
            leftSide.add(l);
        }
    }

    protected void generateRandomRightSides(Random rand, int nRHS, int nCols) {
        rightSides = new ArrayList<>();
        while (rightSides.size() != nRHS) {
            List<Integer> r = new ArrayList<>();
            for (int k = 0; k < nCols; k++) {
                r.add(rand.nextInt(2));
            }
            if (!containsRhs(r)) {
                rightSides.add(r);
            }
        }
    }

    protected boolean containsRhs(List<Integer> rhs) {
        return rightSides.contains(rhs);
    }

    protected boolean checkSizes() {
        if (nRHS != rightSides.size()) {
            nRHS = rightSides.size();
        }
        if (nRows != leftSide.size()) {
            nRows = leftSide.size();
        }
        return checkAllCols();
    }

    private boolean checkAllCols() {
        int sizeFirst = leftSide.get(0).size();

        for (List<Integer> ls : leftSide) {
            if (sizeFirst != ls.size()) {
                return false;
            }
        }
        for (List<Integer> rs : rightSides) {
            if (sizeFirst != rs.size()) {
                return false;
            }
        }
        if (nCols != sizeFirst) {
            nCols = sizeFirst;
        }
        return true;
    }

    protected boolean swapCols(int i, int j) {
        if (Utils.checkColumnsBounds(this, "swapcols", i, j)) {
            for (List<Integer> l : rightSides) {
                Collections.swap(l, i, j);
            }
            for (List<Integer> l : leftSide) {
                Collections.swap(l, i, j);
            }
            return true;
        }
        return false;
    }

    protected boolean addCol(int to, int toAdd) {
        if (Utils.checkColumnsBounds(this, "addcols", to, toAdd) && (to != toAdd)) {
            for (List<Integer> l : leftSide) {
                int valueTo = l.get(to);
                int valueToAdd = l.get(toAdd);
                l.set(to, valueTo ^ valueToAdd);
            }
            for (List<Integer> r : rightSides) {
                int valueTo = r.get(to);
                int valueToAdd = r.get(toAdd);
                r.set(to, valueTo ^ valueToAdd);
            }
            return true;
        }
        return false;
    }

    protected boolean deleteCol(int i) {
        if (Utils.checkColumnsBounds(this, "deletecol", i)) {
            for (List<Integer> l : leftSide) {
                l.remove(i);
            }
            for (List<Integer> r : rightSides) {
                r.remove(i);
            }
            nCols--;
            return true;
        }
        return false;
    }

    private int findPivot(int fromCol, int inRow) {
        int result = -1;
        for (int i = fromCol; i < nCols; i++) {
            if (leftSide.get(inRow).get(i) == 1) {
                return i;
            }
        }
        return result;
    }

    private int gauss() {
        int nPivots = 0;
        int lead = 0;
        int i;
        for (int c = 0; c < nCols; c++) {
            if (nRows <= lead) {
                return nPivots;
            } else {
                i = c;
            }
            int pivot = -1;
            while ((pivot = findPivot(i, lead)) == -1) {
                lead++;
                if (nRows == lead) {
                    return nPivots;
                }
            }
            nPivots++;
            swapCols(c, pivot);
            for (int j = 0; j < nCols; j++) {
                if (j != c && (leftSide.get(lead).get(j) == 1)) {
                    addCol(j, c);
                }
            }
            lead++;
        }
        return nPivots;
    }

    protected int normalize() {
        gauss();
        return checkAndFixZeroColumns();
    }

    private int checkAndFixZeroColumns() {
        for (int i = 0; i < nCols; i++) {
            if (isZeroColumn(i)) {
                Iterator<List<Integer>> it = rightSides.iterator();
                for (; it.hasNext();) {
                    List<Integer> rhs = it.next();
                    if (rhs.get(i) == 1) {
                        it.remove();
                    }
                }
            }
        }
        nRHS = rightSides.size();
        return rightSides.size();
    }

    private boolean isZeroColumn(int i) {
        for (List<Integer> lhs : leftSide) {
            if (lhs.get(i) == 1) {
                return false;
            }
        }
        return true;
    }

    protected int deleteZeroColumns() {
        for (int i = 0; i < nCols; i++) {
            if (isZeroColumn(i)) {
                if (deleteCol(i)) {
                    i--;
                }
            }
        }
        return nCols;
    }

    private void fillRowWithZeros(int row) {
        for (int i = 0; i < nCols; i++) {
            leftSide.get(row).set(i, 0);
        }
    }

    protected void guess(int variable, int value) {
        if (value == 1) {
            for (int i = 0; i < nCols; i++) {
                Integer colValLhs = leftSide.get(variable).get(i);
                for (List<Integer> rhs : rightSides) {
                    Integer colValRhs = rhs.get(i);
                    rhs.set(i, colValRhs ^ colValLhs);
                }
            }
        }
        fillRowWithZeros(variable);
    }

    private List<List<Integer>> generateCombinationTable(int n) {
        int size = 1 << n;
        List<List<Integer>> table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            String binary = Integer.toBinaryString(i);
            if (binary.length() < n) {
                for (int j = 0; j < (n - binary.length()); j++) {
                    row.add(0);
                }
            }
            for (char c : binary.toCharArray()) {
                row.add(Integer.parseInt(Character.toString(c)));
            }
            table.add(row);
        }
        return table;
    }

    private List<List<Integer>> removeBadCombinations(List<List<Integer>> table) {
        for (Iterator<List<Integer>> it = table.iterator(); it.hasNext();) {
            int ones = 0;
            List<Integer> row = it.next();
            for (Integer i : row) {
                if (i == 1) {
                    ones++;
                }
            }
            if (ones == 0 || ones == 1) {
                it.remove();
            }
        }
        return table;
    }

    private MrhsEquation expandEquation() {
        MrhsEquation expanded = Utils.copyEquationValue(this);
        List<List<Integer>> table = removeBadCombinations(generateCombinationTable(nCols));
        for (int i = 0; i < table.size(); i++) {
            for (List<Integer> row : expanded.getLeftSide()) {
                Integer valueRow = 0;
                for (int j = 0; j < expanded.getnCols(); j++) {
                    valueRow = valueRow ^ (table.get(i).get(j) * row.get(j));
                }
                row.add(valueRow);
            }
            for (List<Integer> rhs : expanded.getRightSides()) {
                Integer valueRhs = 0;
                for (int j = 0; j < expanded.getnCols(); j++) {
                    valueRhs = valueRhs ^ (table.get(i).get(j) * rhs.get(j));
                }
                rhs.add(valueRhs);
            }
        }
        expanded.setnCols(expanded.getnCols() + table.size());
        return expanded;
    }

    private MrhsEquation columnsToEquation(int i, int j, MrhsEquation eq) {
        MrhsEquation mrhs = new MrhsEquation();
        mrhs.setLeftSide(new ArrayList<>());
        mrhs.setRightSides(new ArrayList<>());
        mrhs.setnCols(2);
        mrhs.setnRows(eq.getnRows());
        for (List<Integer> row : eq.getLeftSide()) {
            List<Integer> newRow = Arrays.asList(row.get(i), row.get(j));
            mrhs.getLeftSide().add(newRow);
        }
        for (List<Integer> rhs : eq.getRightSides()) {
            List<Integer> newRhs = Arrays.asList(rhs.get(i), rhs.get(j));
            if (!mrhs.getRightSides().contains(newRhs)) {
                mrhs.getRightSides().add(newRhs);
            }
        }
        mrhs.setnRHS(mrhs.getRightSides().size());
        return mrhs;
    }

    private List<MrhsEquation> breakExpandedEquation(MrhsEquation expanded) {
        List<MrhsEquation> result = new ArrayList<>();
        for (int i = 0; i < (expanded.getnCols() - 1); i++) {
            for (int j = i + 1; j < expanded.getnCols(); j++) {
                result.add(columnsToEquation(i, j, expanded));
            }
        }
        return result;
    }

    private List<MrhsEquation> cleanUpSameEquations(List<MrhsEquation> mini) {
        Set<MrhsEquation> set = new HashSet<>(mini);
        return new ArrayList<>(set);
    }

    protected List<MrhsEquation> expansion() {
        List<MrhsEquation> exp = new ArrayList<>();
        List<List<Integer>> table = generateCombinationTable(2);
        MrhsEquation expanded = expandEquation();
        List<MrhsEquation> mini = breakExpandedEquation(expanded);
        for (MrhsEquation eq : mini) {
            if(!eq.getRightSides().containsAll(table)){
            //if (eq.getRightSides().size() != 4) {
                eq.normalize();
                exp.add(eq);
            }
        }
        return cleanUpSameEquations(exp);
    }

    @Override
    public String toString() {
        String newLineChar = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append(newLineChar);
        for (List<Integer> row : leftSide) {
            for (Integer i : row) {
                sb.append(i).append(" ");
            }
            sb.append(newLineChar);
        }
        for (int i = 0; i < (2 * nCols - 1); i++) {
            sb.append("-");
        }
        sb.append(newLineChar);
        for (List<Integer> row : rightSides) {
            for (Integer i : row) {
                sb.append(i).append(" ");
            }
            sb.append(newLineChar);
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.leftSide);
        hash = 37 * hash + Objects.hashCode(this.rightSides);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MrhsEquation other = (MrhsEquation) obj;
        if (!Objects.equals(this.leftSide, other.leftSide)) {
            return false;
        }
        if (!Objects.equals(this.rightSides, other.rightSides)) {
            return false;
        }
        return true;
    }

}
