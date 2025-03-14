package com.example.indivproj2_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class loggedController {
    @FXML
    Label welcomeLabel;
    @FXML
    Button backBtn;

    Stage stage;
    Parent root;

//    @FXML
//    private void initialize(){
//        stage = (Stage) backBtn.getScene().getWindow();
//        var tempString = stage.getTitle();
//        welcomeLabel.setText(tempString);
//        stage.setTitle("Best Main Page");
//    }

    @FXML
    protected void onBackBtnClick(ActionEvent event) throws Exception
    {
        stage = (Stage) backBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("startPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setWelcomeLabel(String text){
        welcomeLabel.setText(text);
    }
}
