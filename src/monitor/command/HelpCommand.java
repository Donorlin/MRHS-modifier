package monitor.command;

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
                    case "normalizeE":
                        helpNormalizeE();
                        break;
                    case "normalizeS":
                        helpNormalizeS();
                        break;
                    case "build":
                        helpBuild();
                        break;
                    case "guess":
                        helpGuess();
                        break;
                    case "expand":
                        helpExpand();
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
        System.err.println("[normalizeE i]                              Echelonize equation i and delete zero columns");
        System.err.println("[normalizeS]                                Apply normalizeE on each equation in system");
        System.err.println("[guess variable value]                      Guess value of specific variable");
        System.err.println("[build]                                     Apply greedy reordering and modifying algorithm on system");
        System.err.println("[expand]                                    TODO popisok, expanduje");
    }

    private void helpLoad() {
        System.err.println("NAME");
        System.err.println("    load - load MRHS system from file");
        System.err.println("SYNOPSIS");
        System.err.println("    load [FILE]");
        System.err.println("DESCRIPTION");
        System.err.println("    Load MRHS system from FILE (from the current directory by default).");
        System.err.println("    If FILE is not specified, file chooser pops up.");
    }

    private void helpSave() {
        System.err.println("NAME");
        System.err.println("    save - save MRHS system to file");
        System.err.println("SYNOPSIS");
        System.err.println("    save [FILE]");
        System.err.println("DESCRIPTION");
        System.err.println("    Save MRHS system to FILE. New FILE is created if it does not exists.");
        System.err.println("    If FILE is not specified, file chooser pops up.");
    }

    private void helpRandom() {
        System.err.println("NAME");
        System.err.println("    random - generate random MRHS system");
        System.err.println("SYNOPSIS");
        System.err.println("    random nVARIABLES nEQUATIONS nCOLUMNS nRHS RANDOMRANGE DENSITY");
        System.err.println("DESCRIPTION");
        System.err.println("    Generate random MRHS system with nVARIABLES variables and nEQUATIONS equations.");
        System.err.println("    Each equation with nCOLUMN columns and nRHS right-hand-sides. DENSITY specifies");
        System.err.println("    ratio between 1/0 in joined system matrix.");
        System.err.println("    If RANDOMRANGE is set to 1 instead of 0, all equations will have their numbers of");
        System.err.println("    columns and right-hand-sides generated randomly from range <1,nCOLUMNS>, nRHS respectively.");
        System.err.println("EXAMPLE");
        System.err.println("    random 5 15 3 7 0 0.05");
        System.err.println("        generates random system with 5% of ones in joined system matrix. System has 5 variables,");
        System.err.println("        15 equations, each with 3 columns and 7 right-hand-sides.");
        System.err.println("    random 5 5 5 5 1 0.5");
        System.err.println("        generates random system with 5 variables, 5 equations, each with random number of ");
        System.err.println("        columns and right-hand-sides, both in range <1,5>. Density of ones ine joined system");
        System.err.println("        matrix is 50% (1/0 = 0.5).");
    }

    private void helpSolve() {
        System.err.println("NAME");
        System.err.println("    solve - send MRHS system for solving to external solver");
        System.err.println("SYNOPSIS");
        System.err.println("    solve SOLVER FILE");
        System.err.println("DESCRIPTION");
        System.err.println("    Execute external solver executable SOLVER with MRHS system saved in FILE");
    }

    private void helpInfo() {
        System.err.println("NAME");
        System.err.println("    info - print size of system and each equation");
        System.err.println("SYNOPSIS");
        System.err.println("    info");
        System.err.println("DESCRIPTION");
        System.err.println("    Print number of variables, equations, columns and right-hand-sides of each equation.");
    }

    private void helpShow() {
        System.err.println("NAME");
        System.err.println("    show - print system");
        System.err.println("SYNOPSIS");
        System.err.println("    show");
        System.err.println("DESCRIPTION");
        System.err.println("    Print system on output.");
    }

    private void helpEcho() {
        System.err.println("NAME");
        System.err.println("    echo - print message");
        System.err.println("SYNOPSIS");
        System.err.println("    echo [MESSAGE]...");
        System.err.println("DESCRIPTION");
        System.err.println("    Print MESSAGEs on output.");
    }

    private void helpSwapRows() {
        System.err.println("NAME");
        System.err.println("    swapRows - swap rows");
        System.err.println("SYNOPSIS");
        System.err.println("    swapRows I J");
        System.err.println("DESCRIPTION");
        System.err.println("    Swaps row I with row J.");
    }

    private void helpAddRow() {
        System.err.println("NAME");
        System.err.println("    addRow - xor rows");
        System.err.println("SYNOPSIS");
        System.err.println("    addRow TO TOADD");
        System.err.println("DESCRIPTION");
        System.err.println("    Xor row TO with row TOADD.");
    }

    private void helpDeleteRow() {
        System.err.println("NAME");
        System.err.println("    deleteRow - delete row");
        System.err.println("SYNOPSIS");
        System.err.println("    deleteRow I");
        System.err.println("DESCRIPTION");
        System.err.println("    Delete row I.");
    }

    private void helpSwapCols() {
        System.err.println("NAME");
        System.err.println("    swapCols - Swap columns in equation");
        System.err.println("SYNOPSIS");
        System.err.println("    swapCols iE I J");
        System.err.println("DESCRIPTION");
        System.err.println("    In equation iE swap column I with column J.");
    }

    private void helpAddCol() {
        System.err.println("NAME");
        System.err.println("    addCol - xor columns in equation");
        System.err.println("SYNOPSIS");
        System.err.println("    addCol iE TO TOADD");
        System.err.println("DESCRIPTION");
        System.err.println("    In equation iE xor column TO with column TOADD.");
    }

    private void helpDeleteCol() {
        System.err.println("NAME");
        System.err.println("    deleteCol - delete column in equation");
        System.err.println("SYNOPSIS");
        System.err.println("    deleteCol iE I");
        System.err.println("DESCRIPTION");
        System.err.println("    In equation iE delete column I.");
    }

    private void helpSwapBlocks() {
        System.err.println("NAME");
        System.err.println("    swapBlocks - swap equations");
        System.err.println("SYNOPSIS");
        System.err.println("    swapBlocks I J");
        System.err.println("DESCRIPTION");
        System.err.println("    Swap equation I with equation J.");
    }

    private void helpDeleteBlock() {
        System.err.println("NAME");
        System.err.println("    deleteBlock - delete equation");
        System.err.println("SYNOPSIS");
        System.err.println("    deleteBlock iE");
        System.err.println("DESCRIPTION");
        System.err.println("    Delete equation iE from system.");
    }

    private void helpGlue() {
        System.err.println("NAME");
        System.err.println("    glue - glue two equations together");
        System.err.println("SYNOPSIS");
        System.err.println("    glue iE jE KEEPOLD");
        System.err.println("DESCRIPTION");
        System.err.println("    Glue equations iE and jE together. If KEEPOLD is 1 then glued equation is added on the");
        System.err.println("    end of system. If KEEPOLD is 0 original iE and jE equations are deleted and new glued equation");
        System.err.println("    is added at position of equation iE.");
    }

    private void helpPermuteBlocks() {
        System.err.println("NAME");
        System.err.println("    permuteBlocks - permute system's equations");
        System.err.println("SYNOPSIS");
        System.err.println("    permuteBlocks [FILE]");
        System.err.println("DESCRIPTION");
        System.err.println("    Apply permutation given by FILE on loaded system. If FILE is not specified permutation is random.");
    }

    private void helpNormalizeE() {
        System.err.println("NAME");
        System.err.println("    normalizeE - echelonize equation and delete redudant right-hand-sides");
        System.err.println("SYNOPSIS");
        System.err.println("    normalizeE iE");
        System.err.println("DESCRIPTION");
        System.err.println("    Echelonize equation iE and if any column is full of zeros, delete those right-hand-sides which does");
        System.err.println("    not have 0 in that column.");
    }

    private void helpNormalizeS() {
        System.err.println("NAME");
        System.err.println("    normalizeS - echelonize all equations of loaded system and delete redudant right-hand-sides.");
        System.err.println("SYNOPSIS");
        System.err.println("    normalizeE iE");
        System.err.println("DESCRIPTION");
        System.err.println("    Apply normalizeE on all equations. For more info about normalizeE write \"help normalizeE\".");
    }

    private void helpBuild() {
        System.err.println("NAME");
        System.err.println("    build - apply greedy reordering and modifying algorithm on loaded system");
        System.err.println("SYNOPSIS");
        System.err.println("    build");
        System.err.println("DESCRIPTION");
        System.err.println("    TODO about algorithm");
    }

    private void helpGuess() {
        System.err.println("NAME");
        System.err.println("    guess - guess value of variable");
        System.err.println("SYNOPSIS");
        System.err.println("    guess VARIABLE VALUE");
        System.err.println("DESCRIPTION");
        System.err.println("    Set variable VARIABLE to value VALUE");
    }

    private void helpExpand() {
        System.err.println("NAME");
        System.err.println("    expand - TODO");
        System.err.println("SYNOPSIS");
        System.err.println("    expand");
        System.err.println("DESCRIPTION");
        System.err.println("    TODO");
    }
}
