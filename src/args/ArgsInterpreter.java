package args;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mrhs.MrhsReader;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class ArgsInterpreter {

    private final Map<String,List<String>> parsedArgs = new HashMap<>();
    private String[] args;
    private MrhsSystem system;
    
    public ArgsInterpreter(String[] args, MrhsSystem system){
        this.args = args;
        this.system = system;
    }
    
    public boolean parseArgs() {
        List<String> options = null;
        for (String cmd : args) {
            if (cmd.charAt(0) == '-') {
                if (cmd.length() < 2) {
                    return false;
                }
                options = new ArrayList<>();
                parsedArgs.put(cmd.substring(1), options);
            } else if (options != null) {
                options.add(cmd);
            } else {
                return false;
            }
        }
        return true;
    }
    
    public void interpretArgs() {
        for (Map.Entry<String, List<String>> ent : parsedArgs.entrySet()) {
            List<String> param = ent.getValue();
            switch (ent.getKey()) {
                case "f":       
                    MrhsReader read = new MrhsReader();
                    if (ent.getValue().size() > 0) {
                        read.readFromFile(param.get(0), system);
                    } else {
                       read.readFromFile(null, system);
                    }
                    break;
                case "help":
                    about();
                    break;
                default:
                    helpArgs();
                    break;
            }
        }
    }
    
    private void helpArgs() {
        System.out.println("List of args options:");
        System.out.println("[-f]                    Loads system from file chosen by filechooser");
        System.out.println("[-f filename]           Loads system from file with name filename. If filename is null filechooser pops up");
    }
    
    private void about(){
        System.out.println("This program is meant to help you with manipulation over MRHS system. Simply load one, make a change and save it. "
                + "Program is compatible only with system over GF(2), the field with two elements.");
    }
}
