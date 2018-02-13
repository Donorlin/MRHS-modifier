package monitor;

import java.util.List;

/**
 *
 * @author Daniel Jahodka
 */
public class EchoCommand implements Command {

    private Arguments arguments;

    public EchoCommand(Arguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if(args.isEmpty()){
            return false;
        }
        for(String echo: args){
            System.err.print(echo + " ");
        }
        System.err.println("");
        return true;
    }
}
