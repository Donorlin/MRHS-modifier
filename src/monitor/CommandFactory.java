package monitor;

import java.util.HashMap;
import java.util.Map;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class CommandFactory {

    private Map<String, Command> commands;
    
    public CommandFactory(MrhsSystem system, Arguments args) {
        commands = new HashMap<>();
        
        commands.put("swapblocks", new SwapBlockCommand(system, args));
        commands.put("deleteblock", new DeleteBlockCommand(system, args));
        commands.put("permuteblocks", new PermuteBlocksCommand(system, args));
        commands.put("glue", new GlueCommand(system, args));
        commands.put("normalizeE", new NormalizeEquationCommand(system, args));
        commands.put("normalizeS", new NormalizeSystemCommand(system, args));

        commands.put("addrow", new AddRowCommand(system, args));
        commands.put("swaprows", new SwapRowsCommand(system, args));
        commands.put("deleterow", new DeleteRowCommand(system, args));

        commands.put("addcol", new AddColumnCommand(system, args));
        commands.put("swapcols", new SwapColumnsCommand(system, args));
        commands.put("deletecol", new DeleteColumnCommand(system, args));

        commands.put("random", new RandomSystemCommand(system, args));
        commands.put("info", new InfoAboutSystemCommand(system, args));
        commands.put("show", new ShowSystemCommand(system, args));
        commands.put("load", new LoadSystemCommand(system, args));
        commands.put("save", new SaveSystemCommand(system, args));
        commands.put("exit", new ExitCommand(args));
        commands.put("help", new HelpCommand(args));
        commands.put("solve", new SolveCommand(system, args));
        commands.put("echo", new EchoCommand(args));
        
        
        commands.put("guess", new GuessCommand(system, args));
        commands.put("build", new BuildCommand(system,args));
    }

    public boolean getAndExecuteCommand(String commandName) {
        if (commands.containsKey(commandName)) {
            return commands.get(commandName).execute();            
        }
        System.err.println("Command not found. For list of commands type \"help\"");
        return false;
    }

}
