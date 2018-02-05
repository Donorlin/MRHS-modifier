package mrhs;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Daniel Jahodka
 */
public class MrhsEquationComparator implements Comparator<MrhsEquation>{

    private Map<MrhsEquation, Integer> sortOrder;
    protected MrhsEquationComparator(Map<MrhsEquation, Integer> sortOrder){
        this.sortOrder = sortOrder;
    }
    
    @Override
    public int compare(MrhsEquation eq1, MrhsEquation eq2) {
        Integer pos1 = sortOrder.get(eq1);
        Integer pos2 = sortOrder.get(eq2);
        return pos1.compareTo(pos2);
    }

}
