package monitor.command;

import monitor.command.Arguments;
import java.util.List;
import java.util.regex.Pattern;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class Utils {

    protected static boolean checkNumberOfArguments(Arguments arguments, int allowedLength, String sender) {
        List<String> args = arguments.getArguments();
        if (allowedLength == 0) {
            if (!args.isEmpty()) {
                System.err.println(sender + ": This method does not accept any arguments.");
                return false;
            }
        } else {
            if (args.size() != allowedLength) {
                System.err.println(sender + ": Illegal number of arguments.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkTypeOfArguments(Arguments arguments, String sender) {
        List<String> args = arguments.getArguments();
        Pattern patternInt = Pattern.compile("^-?\\d+$");
        for (int i = 0; i < args.size(); i++) {
            if (!patternInt.matcher(args.get(i)).matches()) {
                System.err.println(sender + ": Illegal type of arguments.");
                return false;
            }
        }
        return true;
    }

    protected static boolean checkIsSystemLoaded(MrhsSystem system, String sender) {
        if (!system.isSystemLoaded()) {
            System.err.println(sender + ": System not loaded.");
            return false;
        }
        return true;
    }
}
