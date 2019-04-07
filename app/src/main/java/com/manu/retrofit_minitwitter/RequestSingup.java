
package com.manu.retrofit_minitwitter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSingup {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestSingup() {
    }

    /**
     * 
     * @param username
     * @param email
     * @param code
     * @param password
     */
    public RequestSingup(String username, String email, String password, String code) {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
