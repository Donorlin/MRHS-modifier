package monitor;

/**
 *
 * @author Daniel Jahodka
 */
public class HelpCommand implements Command{

    private Arguments arguments;
    
    public HelpCommand(Arguments arguments){
        this.arguments = arguments;
    }
    
    @Override
    public void execute() {
        if(Utils.checkNumberOfArguments(arguments, 0, "help")){
            printListOfCommands();
        }
    }

    private void printListOfCommands(){
        System.err.println("List of console commands:");
        //help for utils
        System.err.println("");
        System.err.println("[load filename]                             Loads system from file with name filename");
        System.err.println("[load]                                      Loads system from file chosen by filechooser");
        System.err.println("[save filename]                             Saves system to file named filename");
        System.err.println("[save]                                      Saves system to file chosen by filechooser");
        System.err.println("[random blocks rows cols rhs randomRange]   Generates random mrhs system to work with.");
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
        System.err.println("[permutateblocks]                           Apply random permutation on blocks");
        System.err.println("[permutateblocks fileName]                  Apply permutation contained in file named fileName on blocks");
        System.err.println("[normalize block]                           TODO");
    }
    
}
