package monitor.command;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class PermuteBlocksCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public PermuteBlocksCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    private boolean checkNumberOfArguments(){
        List<String> args = arguments.getArguments();
        if (args.size() > 1) {
            System.err.println("permutateblocks: Illegal number of arguments.");
            return false;
        }
        return true;
    }
    // TODO PERM FILE
    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (checkNumberOfArguments() && Utils.checkIsSystemLoaded(system, "permutateblocks")) {
            if(args.size() == 1){
                return system.permutateBlocks(args.get(0));
            }else{
                return system.permutateBlocks();
            }
        }
        return false;        
    }
}
