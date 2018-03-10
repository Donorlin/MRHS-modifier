package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class GuessCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public GuessCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 2, "guess")
                && Utils.checkTypeOfArguments(arguments, "guess")
                && Utils.checkIsSystemLoaded(system, "guess")) {
            return system.guess(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)));
        }
        return false;
    }
}
