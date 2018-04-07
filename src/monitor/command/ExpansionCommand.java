package monitor.command;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class ExpansionCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public ExpansionCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 0, "expand")
                && Utils.checkTypeOfArguments(arguments, "expand")
                && Utils.checkIsSystemLoaded(system, "expand")) {
            system.expansion();
            return true;
        }
        return false;
    }
}
