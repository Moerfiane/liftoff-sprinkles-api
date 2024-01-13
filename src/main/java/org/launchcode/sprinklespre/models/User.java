package org.launchcode.sprinklespre.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{
    @OneToMany(mappedBy = "createdBy")
    private List<Review> reviews= new ArrayList<>();

    @NotNull
    private String username;

    @NotNull
    private String pwHash;

    @NotNull
    private String role;


   // private boolean enabled = false;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public User() {}

    public User(String username, String password ) {
        this.username = username;

        this.pwHash = encoder.encode(password);
        this.role = "user";
    }

    public String getUsername() {
        return username;
    }
    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
