package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class SwapBlockCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;
    
    public SwapBlockCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 2, "swapblocks") 
                && Utils.checkTypeOfArguments(arguments, "swapblocks") 
                && Utils.checkIsSystemLoaded(system, "swapblocks")) {
            return system.swapBlocks(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)));
        }
        return false;
    }
}
