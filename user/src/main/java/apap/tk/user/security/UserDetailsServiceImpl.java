package apap.tk.user.security;

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

import apap.tk.user.repository.UserDb;
import apap.tk.user.model.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDb userDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDb.findByUsername(username); 
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
<<<<<<< HEAD
        // grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRoleid().getRole()));
=======
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().getRole()));
>>>>>>> d31e0feb0e76c049ff3e109873f2753fdc21bff3
        return new User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }
    
}
