package edu.whut.cs.jee.mooc.upms.security;

import edu.whut.cs.jee.mooc.client.UserClient;
import edu.whut.cs.jee.mooc.upms.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDto> userDtos = userClient.getUserByUsername(username);
        UserDto userDto = userDtos.size() > 0 ? userDtos.get(0) : null;

        if (userDto == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(userDto);
        }
    }
}
