package mrhs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Daniel Jahodka
 */
public class MrhsReader {

    private File chooseFileRead() {
        JFrame jf = new JFrame("Dialog");
        jf.setAlwaysOnTop(true);
        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir + "/Desktop");
        fc.setDialogTitle("Choose a file to load");
        int retVal = fc.showOpenDialog(jf);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        jf.dispose();
        return null;
    }

    public boolean readFromFile(String str, MrhsSystem system) {
        File file;
        if (str == null) {
            file = chooseFileRead();
        } else {
            file = new File(str);
        }
        if (file == null) {
            return false;
        }
        try (Scanner scan = new Scanner(file)) {
            readMetaInfoSystem(scan, system);
            readMetaInfoEquations(scan, system);
            readLeftHandValues(scan, system);
            readRightHandValues(scan, system);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            return false;
        }
        system.setIsSystemLoaded(true);
        return true;
    }

    private void readMetaInfoSystem(Scanner scan, MrhsSystem system) {
        system.setnRows(scan.nextInt());
        system.setnBlocks(scan.nextInt());
        system.setSystem(new ArrayList<>(system.getnBlocks()));
    }

    private void readMetaInfoEquations(Scanner scan, MrhsSystem system) {
        for (int i = 0; i < system.getnBlocks(); i++) {
            MrhsEquation eq = new MrhsEquation();
            eq.setnRows(system.getnRows());
            eq.setnCols(scan.nextInt());
            eq.setnRHS(scan.nextInt());
            eq.setLeftSide(new ArrayList<>(system.getnRows()));
            eq.setRightSide(new ArrayList<>(eq.getnRHS()));
            system.getSystem().add(eq);
        }
    }

    private void readLeftHandValues(Scanner scan, MrhsSystem system) {
        String tmp;
        for (int i = 0; i < system.getnRows(); i++) {
            for (int j = 0; j < system.getnBlocks(); j++) {
                ArrayList<Integer> list = new ArrayList<>();
                if (system.getnBlocks() == 1) {
                    tmp = scan.next();
                    list.add(Integer.parseInt(tmp.substring(1, 2)));
                    for (int k = 0; k < system.getSystem().get(j).getnCols() - 2; k++) {
                        list.add(scan.nextInt());
                    }
                    tmp = scan.next();
                    list.add(Integer.parseInt(tmp.substring(0, 1)));
                } else {
                    if (j == 0) {
                        tmp = scan.next();
                        list.add(Integer.parseInt(tmp.substring(1, 2)));
                        for (int k = 0; k < system.getSystem().get(j).getnCols() - 1; k++) {
                            list.add(scan.nextInt());
                        }
                    } else if (j == system.getnBlocks() - 1) {
                        for (int k = 0; k < system.getSystem().get(j).getnCols() - 1; k++) {
                            list.add(scan.nextInt());
                        }
                        tmp = scan.next();
                        list.add(Integer.parseInt(tmp.substring(0, 1)));
                    } else {
                        for (int k = 0; k < system.getSystem().get(j).getnCols(); k++) {
                            list.add(scan.nextInt());
                        }
                    }
                }
                system.getSystem().get(j).getLeftSide().add(list);
            }
        }
    }

    private void readRightHandValues(Scanner scan, MrhsSystem system) {
        String tmp;
        for (int i = 0; i < system.getnBlocks(); i++) {
            for (int j = 0; j < system.getSystem().get(i).getnRHS(); j++) {
                ArrayList<Integer> list = new ArrayList<>();
                tmp = scan.next();
                if (system.getSystem().get(i).getnCols() == 1) {
                    list.add(Integer.parseInt(Character.toString(tmp.charAt(1))));
                    if (!system.getSystem().get(i).containsRhs(list)) {
                        system.getSystem().get(i).getRightSide().add(list);
                    }
                    continue;
                }
                list.add(Integer.parseInt(tmp.substring(1, 2)));
                for (int k = 1; k < system.getSystem().get(i).getnCols() - 1; k++) {
                    list.add(scan.nextInt());
                }
                tmp = scan.next();
                list.add(Integer.parseInt(tmp.substring(0, 1)));
                if (!system.getSystem().get(i).containsRhs(list)) {
                    system.getSystem().get(i).getRightSide().add(list);
                }
            }
        }
    }

    public List<Integer> readPermutationFromFile(String fileName) {
        List<Integer> permutation = null;
        File file = new File(fileName);

        try (Scanner scan = new Scanner(file)) {
            String line = scan.nextLine();
            permutation = strToIntList(Arrays.asList(line.split(" ")));
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }

        return permutation;
    }

    private List<Integer> strToIntList(List<String> list) {
        List<Integer> result = new ArrayList<>();
        for (String s : list) {
            result.add(Integer.parseInt(s));
        }
        return result;
    }

}
