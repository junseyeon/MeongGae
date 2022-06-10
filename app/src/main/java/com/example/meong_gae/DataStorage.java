package com.example.meong_gae;

import java.util.HashMap;

public class DataStorage {
    String useremail;
    String userpwd;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public void userhashmap(String email, String pwd)
    {
        HashMap<String,Object> userdatamap = new HashMap<String, Object>();
        userdatamap.put("Useremail",email);
        userdatamap.put("Userpwd",pwd);
    }


}
