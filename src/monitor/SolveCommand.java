/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import mrhs.MrhsSystem;

/**
 *
 * @author daniel
 */
public class SolveCommand implements Command {

    private Arguments arguments;

    public SolveCommand(MrhsSystem system, Arguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute() {
        List<String> args = arguments.getArguments();
        try {
            ProcessBuilder proc = new ProcessBuilder("./" + args.get(0), "-f", args.get(1));
            proc.inheritIO();
            Process p = proc.start();
            p.waitFor();
            System.out.println(getOutput(p));
        } catch (InterruptedException ex) {
        } catch (IOException ex) {
        }
    }

    private String getOutput(Process process){
        try (BufferedReader read = new BufferedReader(new InputStreamReader(process.getInputStream()))){            
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = read.readLine()) != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
            return sb.toString();
        } catch (IOException e) {
            System.out.println("solve: Cant get output from process.");
        }   
        return null;
    }
}
