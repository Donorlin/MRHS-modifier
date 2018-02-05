package main;

import monitor.Monitor;

/**
 * Issues:
 * - when fileName is put as argument, only names without white space are supported.
 */

/**
 *
 * @author Daniel Jahodka
 */
public class Main {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Monitor mon = new Monitor(args); 
        mon.console();
    }
}
