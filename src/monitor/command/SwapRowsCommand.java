package monitor.command;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class SwapRowsCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public SwapRowsCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 2, "swaprows") 
                && Utils.checkTypeOfArguments(arguments, "swaprows") 
                && Utils.checkIsSystemLoaded(system, "swaprows")) {
            return system.swapRows(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)));
        }
        return false;
    }
    
}
