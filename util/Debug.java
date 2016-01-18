package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Debug {
	File file;
	BufferedWriter writer;
	boolean on;
	
	public Debug(String path){
		file = new File(path);
		try {
			writer = new BufferedWriter(new FileWriter(file));
			file.createNewFile();
			on = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean write(String string){
		if (on){
			try {
				writer.write(string);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public boolean close(){
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
