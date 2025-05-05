package com.example.thinkphpgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ThinkPHPKiller extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("ThinkPHPKiller");
        try{
            stage.getIcons().add(new Image(getClass().getResourceAsStream("ikun.png")));
        }catch (Exception ignored){

        }
        FXMLLoader fxmlLoader=new FXMLLoader(ThinkPHPKiller.class.getResource("view.fxml"));
        Scene scene=new Scene(fxmlLoader.load(),900,700);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args ){
            launch();
    }

}
