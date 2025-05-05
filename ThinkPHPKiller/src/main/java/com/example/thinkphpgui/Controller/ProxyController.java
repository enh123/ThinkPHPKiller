package com.example.thinkphpgui.Controller;

import com.example.thinkphpgui.ConfigManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProxyController {
    @FXML
    RadioButton enableProxyRadioButton, disableProxyRadioButton;

    @FXML
    Button saveButton;

    @FXML
    TextField ipTextField, portTextField;

    ToggleGroup toggleGroup = new ToggleGroup();

    ConfigManager configManager = ConfigManager.getInstance();

    @FXML
    public void initialize() {

        enableProxyRadioButton.setToggleGroup(toggleGroup);
        disableProxyRadioButton.setToggleGroup(toggleGroup);
        ipTextField.setText("127.0.0.1");
        portTextField.setText("8080");
        if(configManager.enableProxy!=null&&configManager.enableProxy) {
            enableProxyRadioButton.setSelected(true);
        }else{
            disableProxyRadioButton.setSelected(true);
        }


        if(configManager.proxyIp!=null&&!configManager.proxyIp.isEmpty()){
            ipTextField.setText(configManager.proxyIp);
        }
        if(configManager.proxyPort!=null&&!configManager.proxyPort.isEmpty()){
            portTextField.setText(configManager.proxyPort);
        }


    }

    @FXML
    public void saveButtonOnAction() {
        saveButton.getScene().getWindow().hide();
        if (enableProxyRadioButton.isSelected()) {
            configManager.enableProxy=true;
        }else{
            configManager.enableProxy=false;
        }


        if(this.ipTextField!=null&&!this.ipTextField.getText().isEmpty()){
            configManager.proxyIp=this.ipTextField.getText();
        }

        if(this.portTextField!=null&&!this.portTextField.getText().isEmpty()){
            configManager.proxyPort=this.portTextField.getText();
        }

        if (enableProxyRadioButton.isSelected()) {
            String ip = ipTextField.getText();
            String port = portTextField.getText();
            if ((ip == null || ip.isEmpty()) || (port == null || port.isEmpty())) {
                configManager.setAlert("代理配置错误", "请设置ip和port");
            }
        }
    }
}
