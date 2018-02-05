package monitor;

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
    public void execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 1, "deleteblock")
                && Utils.checkTypeOfArguments(arguments, "deleteblock")
                && Utils.checkIsSystemLoaded(system, "deleteblock")) {
            system.deleteBlock(Integer.parseInt(args.get(0)));
        }
    }
}
