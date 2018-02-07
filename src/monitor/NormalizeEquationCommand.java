package monitor;

import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class NormalizeEquationCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public NormalizeEquationCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public void execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 1, "normalize")
                && Utils.checkTypeOfArguments(arguments, "normalize")
                && Utils.checkIsSystemLoaded(system, "normalize")) {
            system.normalize(Integer.parseInt(args.get(0)));
        }
    }

}
