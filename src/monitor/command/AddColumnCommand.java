package monitor.command;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class AddColumnCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public AddColumnCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }
    
    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 3, "Add column")
                && Utils.checkTypeOfArguments(arguments, "Add column")
                && Utils.checkIsSystemLoaded(system, "Add column")) {
            return system.addCol(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)));
        }
        return false;
    }
}
