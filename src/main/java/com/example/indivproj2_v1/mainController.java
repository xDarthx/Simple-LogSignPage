package com.example.indivproj2_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;

public class mainController
{
    @FXML
    Button logInBtn, signInBtn, backBtn;
    @FXML
    TextField signUser, signEmail, logUser;
    @FXML
    PasswordField signPass, logPass;
    @FXML
    Label logFail, signFail, welcomeLabel;
    Stage stage;
    Parent root;

    @FXML
    protected void onLogInBtnClick(ActionEvent event) throws Exception
    {
        stage = (Stage) logInBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("logIN.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onSignInBtnClick(ActionEvent event) throws Exception
    {
        stage = (Stage) logInBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("signUP.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onBackBtnClick(ActionEvent event) throws Exception
    {
        stage = (Stage) backBtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("startPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onSignUPBtnClick(ActionEvent event) throws Exception
    {
        String name = signUser.getText();
        String password = signPass.getText();
        String email = signEmail.getText();
        if (mainFunctions.insertInfo(name, password, email))
        {
            signFail.setText("Email Is Already Used");
        }
        else
        {
//            signEmail.setText("");
//            signPass.setText("");
//            signUser.setText("");
            stage = (Stage) backBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
            root = loader.load();
            loggedController Loggedcontoller = loader.getController();
            Loggedcontoller.setWelcomeLabel("Hello, " +mainFunctions.getName(email));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    protected void onLogINBtnClick(ActionEvent event) throws Exception
    {
        String email = logUser.getText();
        String password = logPass.getText();
        if(mainFunctions.checkInfo(email, password))
        {
            stage = (Stage) backBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
            root = loader.load();
            loggedController Loggedcontoller = loader.getController();
            Loggedcontoller.setWelcomeLabel("Hello, " +mainFunctions.getName(email));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            logFail.setText("Log In Fail, Email or Password is incorrect");
        }
    }




}