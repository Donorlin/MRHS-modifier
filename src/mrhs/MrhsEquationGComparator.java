package mrhs;

import java.util.Comparator;

/**
 *
 * @author Daniel Jahodka
 */
public class MrhsEquationGComparator implements Comparator<MrhsEquation>{

    
    @Override
    public int compare(MrhsEquation t, MrhsEquation t1) {
        Double t1g = t.getG();
        Double t2g = t1.getG();
        int result = t1g.compareTo(t2g);
        return result;
    }

}
