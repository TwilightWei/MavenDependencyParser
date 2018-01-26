package main.java.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class FileParser {
	
	// Get content of a file
	public String getFileContent(String filePath) {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			char[] buf = new char[10];
			int numRead = 0;
			try {
				while((numRead = reader.read(buf)) != -1) {
					String readData = String.valueOf(buf, 0, numRead);
					fileData.append(readData);
					buf = new char[1024];
				} 
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			} 
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		return fileData.toString();
	}
	
	// Get all path of files in root with specific extension
	public ArrayList<String> getFilePaths(String rootPath, String extension) {
		ArrayList<String> filePaths = new ArrayList<String>();
		File rootFolder = new File(rootPath);
		List<File> files = getFiles(rootFolder, extension);
		
		for(File file : files) {
			filePaths.add(file.toString());
		}
		
		return filePaths;
	}
	
	// Get all files in root with specific extension
	public List<File> getFiles(String rootPath, String extension) {
		File rootFolder = new File(rootPath);
		List<File> files = getFiles(rootFolder, extension);
		
		return files;
	}
	
	private List<File> getFiles(File folder, String extension) {
		List<File> files = new ArrayList<File>();

	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	files.addAll(getFiles(fileEntry, extension));
	        } else if (FilenameUtils.getName(fileEntry.toString()).endsWith(extension)) {
	        	//System.out.println(FilenameUtils.getName(fileEntry.toString()));
	        	files.add(fileEntry);
	        }
	    }
	    
		return files;
	}
}
