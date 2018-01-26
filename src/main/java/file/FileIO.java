package main.java.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;

public class FileIO {
	
	public void createFolder(String folderPath) {
		File dir = new File(folderPath);
		dir.mkdir();
	}
	
	public void clearFolder(String folderPath){
		Path dir = Paths.get(folderPath);
		
		if(!Files.exists(dir)) {
			createFolder(folderPath);
		}
		
		try {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
				 @Override
                 public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                     Files.delete(file);
                     return FileVisitResult.CONTINUE;
                 }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createFile(String filePath){
		File file =new File(filePath);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void appendFile(String fullFilePath, String content) throws IOException{
		File file =new File(fullFilePath);
		if(!file.exists()){
			createFile(fullFilePath);
	    }
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(content);
    	bw.newLine();
    	bw.close();
	}
	
	
	public void writeString(String filePath, String content) {
		try {
			appendFile(filePath + ".txt", content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readFile(String fullFilePath) {
		String content = new String();
		File file =new File(fullFilePath);
		
		try {
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while(br.ready()) {
				content += br.readLine() + "\n";
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public ArrayList<String> readList(String filePath) {
		ArrayList<String> content;
		content = new ArrayList<>(Arrays.asList(readFile(filePath + ".txt").split("\n")));
		//System.out.println(content);
		return content;
	}
	
	public JSONObject readJson(String filePath) {
		JSONObject content;
		content =  new JSONObject(readFile(filePath + ".txt"));
		//System.out.println(content);
		return content;
	}
}
