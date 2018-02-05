package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class SwapColumnsCommand implements Command{
    private MrhsSystem system;
    private Arguments arguments;

    public SwapColumnsCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }
       
    @Override
    public void execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 3, "swapcols") 
                && Utils.checkTypeOfArguments(arguments, "swapcols") 
                && Utils.checkIsSystemLoaded(system, "swapcols")) {
            system.swapCols(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), Integer.parseInt(args.get(2)));
        }
    }
    
}
