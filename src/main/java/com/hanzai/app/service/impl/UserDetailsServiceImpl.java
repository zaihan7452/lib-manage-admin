package com.hanzai.app.service.impl;

import com.hanzai.app.entity.PermissionEntity;
import com.hanzai.app.entity.UserEntity;
import com.hanzai.app.service.IPermissionService;
import com.hanzai.app.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;
    private final IPermissionService permissionService;

    public UserDetailsServiceImpl(IUserService userService,
                                  IPermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    /**
     * Load user details by username
     *
     * @param username the username to load user details
     * @return UserDetails with the user information
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userService.getUserByUsername(username);

        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserEntity userEntity = userEntityOptional.get();
        List<PermissionEntity> userPermissions = permissionService.getUserPermissionsByUserId(userEntity.getId());
        List<GrantedAuthority> authorities;
        if (CollectionUtils.isEmpty(userPermissions)) {
            authorities = List.of();
        } else {
            authorities = userPermissions.stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                    .collect(Collectors.toList());
        }

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }

}
