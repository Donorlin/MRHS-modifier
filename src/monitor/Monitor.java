package monitor;

import args.ArgsInterpreter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class Monitor {

    private static final String VERSION = "0.1";
    // 0.1 glue added, permfile added, save fixed

    private final MrhsSystem system = new MrhsSystem();
    private final Arguments arguments = new Arguments();
    private final CommandFactory commands = new CommandFactory(system, arguments);
      
    public Monitor(String[] args) {
        if (args.length > 0) {
            ArgsInterpreter interpreter = new ArgsInterpreter(args, system);
            if (interpreter.parseArgs()) {
                interpreter.interpretArgs();
            }
        }
    }

    public void console() {
        Scanner scan = new Scanner(System.in);
        Integer counter = 1;
        System.err.println("Welcome in MRHS MODIFIER v" + VERSION);
        System.err.println("For list of commands type \"help\"");
        while (true) {
            System.err.print("> ");
            String inputFromConsole = scan.nextLine();
            List<String> parsedInputFromConsole = new ArrayList<>(Arrays.asList(inputFromConsole.split(" ")));
            String commandName = parsedInputFromConsole.get(0);
            parsedInputFromConsole.remove(0);
            arguments.setArguments(parsedInputFromConsole);
            if(commands.getAndExecuteCommand(commandName)){
                System.err.println(counter + ": " + inputFromConsole + " DONE");
            }else{
                System.err.println(counter + ": " + inputFromConsole + " FAILED");
            }
            counter += 1;
        }
    }
}
