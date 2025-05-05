package com.example.thinkphpgui;


import javafx.scene.control.TextField;


import java.io.File;

public class Check {
     ConfigManager configManager=ConfigManager.getInstance();
     TextField urlTextField=configManager.urlTextField;
     TextField urlFileTextField=configManager.urlFileTextField;

     public Boolean checkUrlFile(){

         if(urlTextField.getText().isEmpty()&&urlFileTextField.getText().isEmpty()){
             configManager.setAlert("初始配置错误","请设置要扫描的目标");
             return false;
         }
         if(configManager.scanType.equals("byFile")){
             File file=new File(urlFileTextField.getText());
             if (!file.exists()) {
                 configManager.setAlert("导入url文件时出错","找不到文件"+file.getAbsolutePath());
                 return false;
             }else if (!file.canRead()){
                 configManager.setAlert("导入url文件时出错","无法读取文件"+file.getAbsolutePath());
                 return false;
             }
             else if (file.length()==0){
                 configManager.setAlert("导入url文件时出错","url文件内容为空");
                 return false;
             }
         }

         return true;
     }

     public void checkTreads(){
         if(configManager.threadsTextField.getText()==null||configManager.threadsTextField.getText().isEmpty()){
             configManager.threadsTextField.setText("10");
         }
         try{
             Double threads=Double.valueOf(configManager.threadsTextField.getText());
             if(threads <= 0){
                 configManager.threadsTextField.setText("50");
             }
             if(threads %1!=0){
                 configManager.threadsTextField.setText("50");
             }
             if(threads>100){
                 configManager.threadsTextField.setText("100");
             }
         }catch (Exception e){
             configManager.threadsTextField.setText("50");
         }
     }

     public Boolean checkConfig(){
         String url=urlTextField.getText();
         String urlFilePath=urlFileTextField.getText();
          if(url==null&&urlFilePath==null){
              System.out.println(urlTextField);
              configManager.setAlert("初始配置错误","请设置url或url文件");
              return false;
          }
          if (checkUrlFile()){
              checkTreads();
              return true;
          }else{
              return false;
          }

     }

}
