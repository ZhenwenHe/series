package cn.edu.cug.cs.gtl.series.app.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 配置文件管理
 * 数据文件管理
 *
 */
public class SeriesApplication extends Application {

    public ExperimentalConfig experimentalConfig;

    public URL resourceRoot;//资源文件的根目录

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  {
        primaryStage.setTitle("Series Application");
        try {
            resourceRoot = getClass().getResource("/");
            URL url= new URI(resourceRoot.toString()+"MainWindow.fxml").toURL();
            Parent parent = FXMLLoader.load(url);
            Scene scene=new Scene(parent);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException | URISyntaxException e){
            e.printStackTrace();
        }

    }
}
