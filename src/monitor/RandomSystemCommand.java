package monitor;

import java.util.List;
import java.util.regex.Pattern;
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

    private static boolean checkTypeOfArguments(Arguments arguments) {
        List<String> args = arguments.getArguments();
        Pattern patternInt = Pattern.compile("^-?\\d+$");
        Pattern patternDouble = Pattern.compile("^\\d.\\d+$");
        for (int i = 0; i < 5; i++) {
            if (!patternInt.matcher(args.get(i)).matches()) {
                System.err.println("random: Illegal type of arguments.");
                return false;
            }
        }
        if (!patternDouble.matcher(args.get(5)).matches()) {
            System.err.println("random: Density should be type of double.");
            return false;
        }
        return true;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 6, "random")
                && checkTypeOfArguments(arguments)) {
            return system.generateRandomSystem(
                    Integer.parseInt(args.get(0)),
                    Integer.parseInt(args.get(1)),
                    Integer.parseInt(args.get(2)),
                    Integer.parseInt(args.get(3)),
                    Integer.parseInt(args.get(4)),
                    Double.parseDouble(args.get(5))
            );
        }
        return false;
    }
}
