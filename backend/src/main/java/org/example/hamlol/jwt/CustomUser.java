package org.example.hamlol.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUser implements UserDetails {

    private final String username;
    private final String gameName;
    private final String tagLine;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUser(String username,
                      String gameName,
                      String tagLine,
                      Collection<? extends GrantedAuthority> authorities) {
        this.username    = username;
        this.gameName    = gameName;
        this.tagLine     = tagLine;
        this.authorities = authorities;
    }
    public String getGameName() { return gameName; }
    public String getTagLine()  { return tagLine;  }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
