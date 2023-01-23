package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import application.config.AppConfig;
import application.models.*;

public class MainSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField urlTextField;
    
    public int index  = 0;

    @FXML
    void downloadButtonClicked(ActionEvent event) {
    	String url = urlTextField.getText().trim();
    	String filename = url.substring(url.lastIndexOf("/") + 1);
    	String status = "STARTING";
    	String action = "OPEN";
    	String path = AppConfig.DOWNLOAD_PATH + File.separator + filename;
    	FileInfo file = new FileInfo((index + 1), filename, url, status, action, path);
    	DownloadThread downloader = new DownloadThread(file, this);
    	downloader.start();
    }

    @FXML
    void initialize() {
        assert urlTextField != null : "fx:id=\"urlTextField\" was not injected: check your FXML file 'MainScene.fxml'.";

    }

	public void updateUI(FileInfo metaFile) {
		System.out.println(metaFile);
		System.out.println("------------------------------------------");
	}

}