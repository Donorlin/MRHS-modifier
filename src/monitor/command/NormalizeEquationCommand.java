package monitor.command;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class NormalizeEquationCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public NormalizeEquationCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 1, "normalizeE")
                && Utils.checkTypeOfArguments(arguments, "normalizeE")
                && Utils.checkIsSystemLoaded(system, "normalizeE")) {
            system.normalizeEquation(Integer.parseInt(args.get(0)));
            return true;
        }
        return false;
    }
    
}
