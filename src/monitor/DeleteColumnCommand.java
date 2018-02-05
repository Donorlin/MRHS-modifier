package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class DeleteColumnCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public DeleteColumnCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public void execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 2, "deletecol")
                && Utils.checkTypeOfArguments(arguments, "deletecol")
                && Utils.checkIsSystemLoaded(system, "deletecol")) {
            system.deleteCol(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)));
        }
    }

}
