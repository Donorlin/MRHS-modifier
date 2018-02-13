package monitor;

import java.util.List;

/**
 *
 * @author Daniel Jahodka
 */
public class HelpCommand implements Command {

    private Arguments arguments;

    public HelpCommand(Arguments arguments) {
        this.arguments = arguments;
    }

    private boolean checkNumberOfArguments() {
        List<String> args = arguments.getArguments();
        if (args.size() > 1) {
            System.err.println("help: Illegal number of arguments.");
            return false;
        }
        return true;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (checkNumberOfArguments()) {
            if (args.isEmpty()) {
                printListOfCommands();
            } else {
                switch (args.get(0)) {
                    case "load":
                        helpLoad();
                        break;
                    case "save":
                        helpSave();
                        break;
                    case "random":
                        helpRandom();
                        break;
                    case "solve":
                        helpSolve();
                        break;
                    case "info":
                        helpInfo();
                        break;
                    case "show":
                        helpShow();
                        break;
                    case "echo":
                        helpEcho();
                        break;
                    case "swaprows":
                        helpSwapRows();
                        break;
                    case "addrow":
                        helpAddRow();
                        break;
                    case "deleterow":
                        helpDeleteRow();
                        break;
                    case "swapcols":
                        helpSwapCols();
                        break;
                    case "addcol":
                        helpAddCol();
                        break;
                    case "deletecol":
                        helpDeleteCol();
                        break;
                    case "swapblocks":
                        helpSwapBlocks();
                        break;
                    case "deleteblock":
                        helpDeleteBlock();
                        break;
                    case "glue":
                        helpGlue();
                        break;
                    case "permuteBlocks":
                        helpPermuteBlocks();
                        break;
                    case "normalize":
                        helpNormalize();
                        break;
                    default:
                        System.err.println("Command " + args.get(0) + " not recognized.");
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    private void printListOfCommands() {
        System.err.println("List of console commands:");
        //help for utils
        System.err.println("");
        System.err.println("[load filename]                             Loads system from file with name filename");
        System.err.println("[load]                                      Loads system from file chosen by filechooser");
        System.err.println("[save filename]                             Saves system to file named filename");
        System.err.println("[save]                                      Saves system to file chosen by filechooser");
        System.err.println("[random blocks rows cols rhs randomRange]   Generates random mrhs system to work with.");
        System.err.println("[solve solver fileName]                     Execute external solver executable with file fileName to solve");        
        System.err.println("[info]                                      Prints info about system");
        System.err.println("[show]                                      Prints actual mrhs system");
        System.err.println("[help]                                      Prints list of commands");
        System.err.println("[echo your message]                         Prints all arguments given to echo");
        System.err.println("[exit]                                      Exits program");

        //help for mrhs modifaction methods
        System.err.println("");
        System.err.println("[swaprows i j]                              Swaps row i with row j");
        System.err.println("[addrow to toAdd]                           XORs row to with row toAdd");
        System.err.println("[deleterow i]                               Deletes row i");

        System.err.println("[swapcols block i j]                        Swaps column i with column j in equation block");
        System.err.println("[addcol block to toAdd]                     XORs column to with column toAdd in equation block");
        System.err.println("[deletecol block i]                         Deletes column i in equation block");

        System.err.println("[swapblocks i j]                            Swaps block i with block j");
        System.err.println("[deleteblock i]                             Deletes block i");
        System.err.println("[glue i j keepOld]                          Apply gluing on equations i and j");
        System.err.println("[permuteblocks]                             Apply random permutation on blocks");
        System.err.println("[permuteblocks fileName]                    Apply permutation contained in file named fileName on blocks");
        System.err.println("[normalize block]                           TODO");
    }

    private void helpLoad() {

    }

    private void helpSave() {

    }

    private void helpRandom() {

    }
    
    private void helpSolve(){
        
    }

    private void helpInfo() {

    }

    private void helpShow() {

    }

    private void helpEcho() {

    }

    private void helpSwapRows() {

    }

    private void helpAddRow() {

    }

    private void helpDeleteRow() {

    }

    private void helpSwapCols() {

    }

    private void helpAddCol() {

    }

    private void helpDeleteCol() {

    }

    private void helpSwapBlocks() {

    }

    private void helpDeleteBlock() {

    }

    private void helpGlue() {

    }

    private void helpPermuteBlocks() {

    }

    private void helpNormalize() {

    }
}
