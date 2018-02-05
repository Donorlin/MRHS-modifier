package monitor;

/**
 *
 * @author Daniel Jahodka
 */
public class ExitCommand implements Command{

    private Arguments arguments;
    
    public ExitCommand(Arguments arguments){
        this.arguments = arguments;
    }
    
    @Override
    public void execute() {
        if(Utils.checkNumberOfArguments(arguments, 0, "exit")){
            System.exit(0);            
        }
    }

}
