package org.launchcode.sprinklespre.models.dto;

public class RegisterFormDTO extends LoginFormDTO  {
    private String verifyPassword;
     private String email;

    private String role;

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

}