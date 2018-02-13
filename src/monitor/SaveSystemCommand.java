package monitor;

import java.io.File;
import java.util.List;
import mrhs.MrhsSystem;
import mrhs.MrhsWriter;

/**
 *
 * @author Daniel Jahodka
 */
public class SaveSystemCommand implements Command {

    private MrhsSystem system;
    private Arguments arguments;

    public SaveSystemCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    private boolean checkNumberOfArguments() {
        List<String> args = arguments.getArguments();
        if (args.size() > 1) {
            System.err.println("save: Illegal number of arguments.");
            return false;
        }

        return true;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (checkNumberOfArguments() && Utils.checkIsSystemLoaded(system, "save")) {
            MrhsWriter write = new MrhsWriter();
            if (args.isEmpty()) {
                return write.writeToFile(null, system);
            } else {
                return write.writeToFile(new File(args.get(0)), system);
            }
        }
        return false;
    }

}
