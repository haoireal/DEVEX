package com.Devex.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails { 
	private User user;
//    private String name;
//    private String password;
//    private List<GrantedAuthority> authorities;

//    public UserInfoUserDetails(User userInfo) {
//        name = userInfo.getUsername();
//        password = userInfo.getPassword();
//        System.out.println();
//        List<UserRole> userRoles = userInfo.getRoles(); // Danh sách các UserRole
//        authorities = userRoles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getRole()))
//                .collect(Collectors.toList());
//    }
	
	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (UserRole roleUser : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleUser.getRole().getId()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
