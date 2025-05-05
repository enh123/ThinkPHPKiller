package com.example.thinkphpgui.Controller;

import com.example.thinkphpgui.ConfigManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class HeadersController {
    @FXML
    RadioButton enableRadioButton,disableRadioButton;

    @FXML
    Button saveButton;

    @FXML
    TextArea headersTextArea;

    ToggleGroup toggleGroup=new ToggleGroup();

    ConfigManager configManager=ConfigManager.getInstance();

    @FXML
    public void initialize(){

        enableRadioButton.setToggleGroup(toggleGroup);
        disableRadioButton.setToggleGroup(toggleGroup);
        if(ConfigManager.enableHeaders !=null&& ConfigManager.enableHeaders){
            enableRadioButton.setSelected(true);
        }else{
            disableRadioButton.setSelected(true);
        }
        if(configManager.headers!=null&&!configManager.headers.isEmpty()){
            headersTextArea.setText(configManager.headers);
        }


    }

    @FXML
    public void saveButtonOnAction(){
        saveButton.getScene().getWindow().hide();
        if(headersTextArea!=null&&!headersTextArea.getText().isEmpty()){
            ConfigManager.headersMap.clear();
            String[] lines = headersTextArea.getText().split("\\r?\\n");
            for(String line:lines){
                if(line.isEmpty()){
                    continue;
                }
                int colonIndex=line.indexOf(":");
                if(colonIndex<=0){
                    continue;
                }
                String key=line.substring(0,colonIndex);
                String value=line.substring(colonIndex+1);
                ConfigManager.headersMap.put(key,value);
            }
        }
        ConfigManager.enableHeaders = enableRadioButton.isSelected();
        if(headersTextArea.getText()!=null&&!headersTextArea.getText().isEmpty()){
            configManager.headers=headersTextArea.getText();
        }

    }


}
