package main.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import main.java.config.ConfigReader;
import main.java.file.FileIO;

/*
 * Copy all dependency artifacts to specific file
 */
public class CopyDependencies {
	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		FileIO fileIO = new FileIO();
		
		ArrayList<String> list;
		ArrayList<String> sources;
		String configPath = "D:\\Users\\user\\git\\MavenDependencyParser\\src\\config.properties";
		
		sources = new ArrayList<>(Arrays.asList(configReader.readConfig(configPath, "sources").split(",")));
		
		for(String source: sources) {
			list = fileIO.readList(source + "\\reference path");
			for(String srPath: list) {
				fileIO.createFolder(source + "\\Dependencies");
				File f1 = new File(srPath);
				String dtPath = source + "\\Dependencies\\" + f1.getName();
				File f2 = new File(dtPath);
				try {
					Files.copy(f1.toPath(), f2.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("Finished");
	}
}