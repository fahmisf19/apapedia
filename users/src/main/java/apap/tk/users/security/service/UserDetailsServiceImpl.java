package apap.tk.users.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import apap.tk.users.repository.UserDb;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserDb userDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userDb.findByUsername(username);

        if (user == null || user.getDeleted()) {
            throw new UsernameNotFoundException("Username or password incorrect");
        }
        
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole()));
        return new User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }
}

