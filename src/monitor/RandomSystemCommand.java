package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class RandomSystemCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public RandomSystemCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 5, "random")
                && Utils.checkTypeOfArguments(arguments, "random")) {
            system.generateRandomSystem(
                    Integer.parseInt(args.get(0)),
                    Integer.parseInt(args.get(1)),
                    Integer.parseInt(args.get(2)),
                    Integer.parseInt(args.get(3)),
                    Integer.parseInt(args.get(4))
            );
            return true;
        }
        return false;
    }

}
