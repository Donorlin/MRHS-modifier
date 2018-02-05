package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class AddRowCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public AddRowCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public void execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 2, "addrow")
                && Utils.checkTypeOfArguments(arguments, "addrow")
                && Utils.checkIsSystemLoaded(system, "addrow")) {
            system.addRow(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)));
        }
    }
}
