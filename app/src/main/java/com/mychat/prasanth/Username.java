package com.mychat.prasanth;

/**
 * Created by prashanth on 15-08-2017.
 */

public class Username {

    String email;
    String user;

    public Username(String email,String user){

        this.email = email;
        this.user = user;

    }

    public Username() {
    }

    public String getEmail() {
        return email;
    }

    public String getUser() {
        return user;
    }
}
