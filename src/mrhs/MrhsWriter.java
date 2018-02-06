/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrhs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author mrave
 */
public class MrhsWriter {

    private File chooseFileWrite() {
        JFrame jf = new JFrame("Dialog");
        jf.setAlwaysOnTop(true);

        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir + "/Desktop");

        fc.setDialogTitle("Choose a file to save to or create a new file");
        int retVal = fc.showSaveDialog(jf);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        jf.dispose();
        return null;
    }

    public boolean writeToFile(File file, MrhsSystem system) {
        if(file == null){
            file = chooseFileWrite();
        }
        if(file == null){
            return false;
        }
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e){
            System.err.println("writeToFile: Can't create file");
        }
        if (file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){                
                bw.write(system.toFileString());
                bw.flush();
                return true;
            } catch (IOException e) {
                System.err.println("writeToFile: Can't write to file.");
            } 
        }      
       
        return false;
    }
}
