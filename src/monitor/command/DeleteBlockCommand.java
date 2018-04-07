package monitor.command;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class DeleteBlockCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public DeleteBlockCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 1, "deleteblock")
                && Utils.checkTypeOfArguments(arguments, "deleteblock")
                && Utils.checkIsSystemLoaded(system, "deleteblock")) {
            return system.deleteBlock(Integer.parseInt(args.get(0)));
        }
        return false;
    }
}
