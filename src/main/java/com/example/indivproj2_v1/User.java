package com.example.indivproj2_v1;

public class User {
    private String name;
    private String Password;
    private String Email;

    public User(){
        super();
    }
    public User(String name, String passWord, String eMail) {
        this.name = name;
        this.Password = passWord;
        this.Email = eMail;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
