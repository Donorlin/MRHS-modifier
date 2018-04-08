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
        int nRows = pool.get(0).getnRows();
        system.setnRows(nRows);
        int nPivotsSystem = 0;
        while (nPivotsSystem != nRows) {
            MrhsEquation picked = pickBlock();
            system.addBlock(picked);
            pool.remove(picked);
            if (pool.isEmpty()) {
                break;
            }
            nPivotsSystem = nPivotsSystem + system.getSystem().get(system.getnBlocks() - 1).getP();
            for (MrhsEquation eq : pool) {
                Integer newnPivotsSystem;
                system.addBlock(eq);
                if (nPivotsSystem == nRows) {
                    newnPivotsSystem = nPivotsSystem;
                } else {
                    newnPivotsSystem = system.gauss();
                }
                eq.setP(newnPivotsSystem - nPivotsSystem);
                eq.setG(newnPivotsSystem - nPivotsSystem - eq.getnCols() + Math.log(eq.getRightSides().size()) / Math.log(2));
                system.deleteBlock(system.getnBlocks() - 1);
            }
        }
        if (!pool.isEmpty()) {
            pool.sort(new MrhsEquationGComparator());
            for (MrhsEquation eq : pool) {
                system.addBlock(eq);
            }
        }
        system.cleanUp();
        return system;
    }

    private void copyBackSystemValues(MrhsSystem system) {
        this.system.setnBlocks(system.getnBlocks());
        this.system.setnRows(system.getnRows());
        this.system.setSystem(Utils.copyEquationsValues(system.getSystem()));
    }
}
