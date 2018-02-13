package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author Daniel Jahodka
 */
public class SolveCommand implements Command {

    private Arguments arguments;

    public SolveCommand(MrhsSystem system, Arguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public boolean execute() {
        List<String> args = arguments.getArguments();
        if (Utils.checkNumberOfArguments(arguments, 2, "solve")) {
            try {
                ProcessBuilder proc = new ProcessBuilder("./" + args.get(0), "-f", args.get(1));
                proc.inheritIO();
                Process p = proc.start();
                p.waitFor();
                System.out.println(getOutput(p));
            } catch (InterruptedException ex) {
                System.err.println("solve: Interrupted exception.");
                return false;
            } catch (IOException ex) {
                System.err.println("solve: IO exception occured.");
                return false;
            }
            return true;
        }
        return false;
    }

    private String getOutput(Process process) {
        try (BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = read.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println("solve: Cant get output from process.");
        }
        return null;
    }
}
