package mrhs;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Daniel Jahodka
 */
public class MrhsBuilder {

    private List<MrhsEquation> pool;
    private MrhsSystem system;

    public MrhsBuilder(MrhsSystem system) {
        this.system = system;
        pool = Utils.copyEquationsValues(system.getSystem());
    }

    public boolean build() {
        phaseA();
        MrhsSystem builtSystem = phaseB();
        copyBackSystemValues(builtSystem);
        return true;
    }

    private void phaseA() {
        for (MrhsEquation eq : pool) {
            eq.normalize();
            eq.deleteZeroColumns();
            eq.setG(Math.log(eq.getRightSides().size()) / Math.log(2));
            eq.setP(eq.getnCols());
        }
    }

    private MrhsEquation pickBlock() {
        return Collections.min(pool, new MrhsEquationGComparator());
    }

    private MrhsSystem phaseB() {
        MrhsSystem system = new MrhsSystem();
        system.setnRows(pool.get(0).getLeftSide().size());
        int nRows = pool.get(0).getnRows();
        int nPivotsSystem = 0;
        
        while (nPivotsSystem != nRows) {
            MrhsEquation picked = pickBlock();
            system.addBlock(picked);
            pool.remove(picked);

            if (pool.isEmpty()) {
                break;
            }
            nPivotsSystem = system.gauss();
            for (MrhsEquation eq : pool) {
                system.addBlock(eq);
                Integer newnPivotsSystem = system.gauss();
                eq.setP(newnPivotsSystem - nPivotsSystem);
                eq.setG(newnPivotsSystem - nPivotsSystem - eq.getnCols() + Math.log(eq.getRightSides().size()) / Math.log(2));
                system.deleteBlock(system.getnBlocks() - 1);
            }
        }
        pool.sort(new MrhsEquationGComparator());
        for (MrhsEquation eq : pool) {
            system.addBlock(eq);
        }
        return system;
    }

    private void copyBackSystemValues(MrhsSystem system) {
        this.system.setnBlocks(system.getnBlocks());
        this.system.setnRows(system.getnRows());
        this.system.setSystem(Utils.copyEquationsValues(system.getSystem()));
    }
}
