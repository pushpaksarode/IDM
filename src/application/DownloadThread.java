package application;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import application.models.FileInfo;

public class DownloadThread extends Thread {
	
	private FileInfo file;
	private MainSceneController manager;
	
	public DownloadThread (FileInfo file, MainSceneController manager) {
		this.file = file;
		this.manager = manager;
	}
	
	public void run() {
		// Download Logic
		this.file.setStatus("DOWNLOADING");
		this.manager.updateUI(this.file);
		try {
			Files.copy(new URL(this.file.getUrl()).openStream(), Paths.get(this.file.getPath()));
			this.file.setStatus("DONE");
		} catch (IOException e) {
			this.file.setStatus("FAILED");
			System.out.println("Downloading Error!");
			e.printStackTrace();
		}
		this.manager.updateUI(this.file);
	}
	
}
