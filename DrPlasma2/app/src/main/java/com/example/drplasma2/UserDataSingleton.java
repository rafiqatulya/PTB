package com.example.drplasma2;

public class UserDataSingleton {
    private static final UserDataSingleton instance = new UserDataSingleton();
    private UserData userData;

    private UserDataSingleton() {

    }

    public static UserDataSingleton getInstance() {
        return instance;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
