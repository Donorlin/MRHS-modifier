package monitor.command;

import mrhs.MrhsBuilder;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class BuildCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public BuildCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        if (Utils.checkNumberOfArguments(arguments, 0, "build")
                && Utils.checkIsSystemLoaded(system, "build")) {
            MrhsBuilder builder = new MrhsBuilder(system);
            return builder.build();
        }
        return false;
    }

}
