package monitor.command;

import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class InfoAboutSystemCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public InfoAboutSystemCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        if (Utils.checkNumberOfArguments(arguments, 0, "info")
                && Utils.checkIsSystemLoaded(system, "info")) {
            system.infoSystem();
            return true;
        }
        return false;
    }

}
