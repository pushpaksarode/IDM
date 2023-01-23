package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<FileInfo> downloadListTable;

    @FXML
    void downloadButtonClicked(ActionEvent event) {
    	String url = urlTextField.getText().trim();
    	String filename = url.substring(url.lastIndexOf("/") + 1);
    	String status = "STARTING";
    	String action = "OPEN";
    	String path = AppConfig.DOWNLOAD_PATH + File.separator + filename;
    	FileInfo file = new FileInfo((index + 1) + "", filename, url, status, action, path);
    	this.index = this.index + 1;
    	DownloadThread downloader = new DownloadThread(file, this);
    	this.downloadListTable.getItems().add(Integer.parseInt(file.getIndex())-1, file);
    	downloader.start();
    	this.urlTextField.setText("");
    }

    @FXML
    void initialize() {
        assert urlTextField != null : "fx:id=\"urlTextField\" was not injected: check your FXML file 'MainScene.fxml'.";
        
        TableColumn<FileInfo, String> sn = (TableColumn<FileInfo, String>) this.downloadListTable.getColumns().get(0);
        sn.setCellValueFactory(p-> {
        	return p.getValue().indexProperty();
        });
        
        TableColumn<FileInfo, String> filename = (TableColumn<FileInfo, String>) this.downloadListTable.getColumns().get(1);
        filename.setCellValueFactory(p-> {
        	return p.getValue().nameProperty();
        });
        
        TableColumn<FileInfo, String> url = (TableColumn<FileInfo, String>) this.downloadListTable.getColumns().get(2);
        url.setCellValueFactory(p-> {
        	return p.getValue().urlProperty();
        });
        
        TableColumn<FileInfo, String> status = (TableColumn<FileInfo, String>) this.downloadListTable.getColumns().get(3);
        status.setCellValueFactory(p-> {
        	return p.getValue().statusProperty();
        });
        
        TableColumn<FileInfo, String> action = (TableColumn<FileInfo, String>) this.downloadListTable.getColumns().get(4);
        action.setCellValueFactory(p-> {
        	return p.getValue().actionProperty();
        });
        
    }

	public void updateUI(FileInfo metaFile) {
		System.out.println(metaFile);
		FileInfo fileInfo = this.downloadListTable.getItems().get(Integer.parseInt(metaFile.getIndex())-1);
		fileInfo.setStatus(metaFile.getStatus());
		this.downloadListTable.refresh();
		System.out.println("------------------------------------------");
	}

}