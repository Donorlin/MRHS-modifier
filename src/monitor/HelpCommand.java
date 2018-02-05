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
        System.out.println("List of console commands:");
        //help for utils
        System.out.println("");
        System.out.println("[load filename]                             Loads system from file with name filename");
        System.out.println("[load]                                      Loads system from file chosen by filechooser");
        System.out.println("[save filename]                             Saves system to file named filename");
        System.out.println("[save]                                      Saves system to file chosen by filechooser");
        System.out.println("[random blocks rows cols rhs randomRange]   Generates random mrhs system to work with.");
        System.out.println("[info]                                      Prints info about system");
        System.out.println("[show]                                      Prints actual mrhs system");
        System.out.println("[help]                                      Prints list of commands");
        System.out.println("[exit]                                      Exits program");

        //help for mrhs modifaction methods
        System.out.println("");
        System.out.println("[swaprows i j]                              Swaps i-th row with j-th row");
        System.out.println("[addrow to toAdd]                           XORs to-th row with toAdd-th row");
        System.out.println("[deleterow i]                               Deletes i-th row");
        
        System.out.println("[swapcols block i j]                        Swaps i-th column wiht j-th column in block-th block");
        System.out.println("[addcol block to toAdd]                     XORs to-th column with toAdd-th column in block-th block");
        System.out.println("[deletecol block i]                         Deletes i-th column in block-th block");
        
        System.out.println("[swapblocks i j]                            Swaps i-th block with j-th");
        System.out.println("[deleteblock i]                             Deletes i-th block");
        System.out.println("[glue i j keepOld]                          Apply gluing on i-th and j-th block");
        System.out.println("[permutateblocks]                           Apply random permutation on blocks");
        System.out.println("[permutateblocks fileName                   Apply permutation contained in file named fileName on blocks");

    }
    
}
