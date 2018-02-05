/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        commands.put("permutateblocks", new PermutateBlocksCommand(system, args));
        commands.put("glue", new GlueCommand(system, args));
        
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
    }

    public void getAndExecuteCommand(String commandName) {
        if (commands.containsKey(commandName)) {
            commands.get(commandName).execute();
            return;
        }
        System.out.println("Command not found. For list of commands type \"help\"");
    }

}
