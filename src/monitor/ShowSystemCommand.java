package monitor;

import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class ShowSystemCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public ShowSystemCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        if (Utils.checkNumberOfArguments(arguments, 0, "show")
                && Utils.checkIsSystemLoaded(system, "show")) {
            System.out.println(system.toString());
            return true;
        }
        return false;
    }

}
