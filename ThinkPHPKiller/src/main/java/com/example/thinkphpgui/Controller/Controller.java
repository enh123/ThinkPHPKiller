package com.example.thinkphpgui.Controller;

import com.example.thinkphpgui.Check;
import com.example.thinkphpgui.ConfigManager;
import com.example.thinkphpgui.Scan;
import com.example.thinkphpgui.ThinkPHPKiller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML
    TextField urlTextField,urlFileTextField,threadsTextField;

    @FXML
    Button importUrlButton,startScanButton,clearButton,getShellButton;

    @FXML
    ComboBox<String> vulnComboBox;

    @FXML
    TextArea infoTextArea;

    ConfigManager configManager =ConfigManager.getInstance();

    public void initialize(){
        this.vulnComboBox.getItems().addAll(
                "ALL",
                "Tp2 RCE",
                "Tp3 Log RCE",
                "Tp5 数据库信息泄露",
                "Tp5 文件包含",
                "Tp5 PHPSESSID 文件包含 RCE",
                "Tp5 sql注入",
                "Tp CVE-2018-20062",
                "Tp CVE-2019-9082",
                "Tp CVE-2022-25481",
                "Tp6 Lang 文件包含 RCE",
                "Tp 日志泄露"

        );

        // 设置默认选中项
        this.vulnComboBox.setValue("ALL");
        this.threadsTextField.setText("50");

        urlTextField.setOnMouseClicked(event -> {
            configManager.scanType="byUrl";
            urlTextField.setStyle("-fx-background-color:white");
            urlFileTextField.setStyle("-fx-background-color:lightgray");
        });

        urlFileTextField.setOnMouseClicked(event -> {
            configManager.scanType="byFile";
            urlFileTextField.setStyle("-fx-background-color:white");
            urlTextField.setStyle("-fx-background-color:lightgray");
        });


        clearButton.setOnAction(event -> {
            infoTextArea.clear();
        });



    }


    public void importUrlButtonOnAction(){
        Stage stage=(Stage) importUrlButton.getScene().getWindow();
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File","*.txt"));
        File currentDir =new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(currentDir);
        File file=fileChooser.showOpenDialog(stage);
        if (file != null) {
            configManager.scanType="byFile";
            urlFileTextField.setStyle("-fx-background-color:white");
            urlTextField.setStyle("-fx-background-color:lightgray");
            urlFileTextField.setText(file.getAbsolutePath());
        }

    }
    public void startScanButtonOnAction(){
          configManager.initialize( urlTextField,  urlFileTextField,threadsTextField, vulnComboBox, importUrlButton,  startScanButton,  clearButton,  getShellButton,  infoTextArea);

          if(this.startScanButton.getText().equals("开始")){
              Check check=new Check();
              if(check.checkConfig()){
                  Platform.runLater(()->{
                      infoTextArea.setText("开始扫描..."+"\n");
                      startScanButton.setText("停止");
                      configManager.isRunning=true;
                  });
                  new Thread(() -> {
                      Scan scan = new Scan();
                      try {
                          scan.startScan();
                      } catch (Exception ignored) {
                      }
                  }).start();
              }
          } else if (this.startScanButton.getText().equals("停止")) {
              startScanButton.setText("开始");
              configManager.isRunning=false;
          }
    }


    @FXML
    public void setProxyMenuItemOnAction() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(ThinkPHPKiller.class.getResource("proxy.fxml"));
        Scene scene=new Scene(fxmlLoader.load(),272,340);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setHeadersMenuItemOnAction() throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(ThinkPHPKiller.class.getResource("headers.fxml"));
        Scene scene=new Scene(fxmlLoader.load(),600,400);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }


}