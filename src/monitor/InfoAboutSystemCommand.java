package monitor;

import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class InfoAboutSystemCommand implements Command{
    private MrhsSystem system;
    private Arguments arguments;
    
    public InfoAboutSystemCommand(MrhsSystem system, Arguments arguments) {
        this.system = system;
        this.arguments = arguments;
    }
    
    @Override
    public void execute() {
        if(Utils.checkNumberOfArguments(arguments, 0, "info") 
                && Utils.checkIsSystemLoaded(system, "info")){
            system.infoSystem();
        }
    }

}
