package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class DeleteRowCommand implements Command{
    private MrhsSystem system;
    private Arguments arguments;

    public DeleteRowCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    } 
    
    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 1, "deleterow")
                && Utils.checkTypeOfArguments(arguments, "deleterow")
                && Utils.checkIsSystemLoaded(system, "deleterow")) {            
            return system.deleteRow(Integer.parseInt(args.get(0)));
        }
        return false;
    }
    
}
