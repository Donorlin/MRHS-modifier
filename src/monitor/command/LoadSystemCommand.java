package monitor.command;

import java.util.List;
import mrhs.MrhsReader;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class LoadSystemCommand implements Command{

    private MrhsSystem system;
    private Arguments arguments;

    public LoadSystemCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }

    private boolean checkNumberOfArguments(){
        List<String> args = arguments.getArguments();
        if (args.size() > 1) {
            System.err.println("load: Illegal number of arguments.");
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if(checkNumberOfArguments()){
            MrhsReader read = new MrhsReader();
            if(args.isEmpty()){                
                return read.readFromFile(null, system);
            }else{
                return read.readFromFile(args.get(0), system);
            }
        }
        return false;
    }
}
