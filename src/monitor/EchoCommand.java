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
    public void execute() {
        List<String> args = arguments.getArguments();
        for(String echo: args){
            System.err.print(echo + " ");
        }
        System.err.println("");
    }
}
