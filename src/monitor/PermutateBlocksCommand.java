package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class PermutateBlocksCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public PermutateBlocksCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    private boolean checkNumberOfArguments(){
        List<String> args = arguments.getArguments();
        if (args.size() > 1) {
            System.out.println("permutateblocks: Illegal number of arguments.");
            return false;
        }
        return true;
    }
    // TODO PERM FILE
    @Override
    public void execute() {
        List<String> args = arguments.getArguments();
        if (checkNumberOfArguments() && Utils.checkIsSystemLoaded(system, "permutateblocks")) {
            if(args.size() == 1){
                system.permutateBlocks(args.get(0));
            }else{
                system.permutateBlocks();
            }
        }
    }
}
