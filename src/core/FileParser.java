package core;

import java.io.*;

public class FileParser { // TODO find how to relative - absolute path
    // Attribute
	private File currentFile;
    private FileWriter writeFile;
    private FileReader readFile;

    // Constructor
    public FileParser() {

    }

    // Read file method
    public boolean readFile(String filename) {
        try {
            currentFile = new File(filename);
            readFile = new FileReader(currentFile);
            return false;
        }
        catch (Exception e) {
            return true;
        }
    }

    public String stringRead() {
    	char temp[] = new char[20000];
        BufferedReader readFileB = new BufferedReader(readFile);
        StringBuilder tempS = new StringBuilder();
        try {
            while (readFileB.read(temp) != -1) {
            	tempS.append(new String(temp));
                temp = new char[20000];
            }
        }
        catch (Exception e) {
        	System.out.println("Exception: FileParser failed to read File");
        }
        return (tempS.toString().replaceAll("\0", "").replaceAll("\t|\b|\r|\f","").trim() + "\n");
    }


    // Write file method
    public boolean writeFile(String filename) {
	    try {
	    	currentFile = new File(filename);
	    	currentFile.createNewFile();
	    	writeFile = new FileWriter(currentFile);
	    	return false;
	    }
	    catch (Exception e) {
	    	return true;
	    }
    }

    public void stringWrite(String write) {
    	try {
	    	writeFile.write(write);
	        writeFile.flush();
    	}
    	catch (Exception e) {
            System.out.println("Exception: FileParser failed to write File");
        }
    }


    // Close file
    public void closeFile() {
    	try {
    		writeFile.close();
    	}
    	catch (Exception e) {}
    	try {
    		readFile.close();
    	}
    	catch (Exception e) {}
    }
}
