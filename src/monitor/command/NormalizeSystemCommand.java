package monitor.command;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class NormalizeSystemCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public NormalizeSystemCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        if (Utils.checkNumberOfArguments(arguments, 0, "normalizeS")
                && Utils.checkIsSystemLoaded(system, "normalizeS")) {            
            return system.normalizeSystem();
        }
        return false;
    }
}
