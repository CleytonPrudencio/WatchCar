package com.system.watchCar.service;

import com.system.watchCar.dto.UserDTO;
import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.User;
import com.system.watchCar.repository.RoleRepository;
import com.system.watchCar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public UserDTO save(UserDTO userDTO) {
        User entity = new User();
        entity.setName(userDTO.getName());
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        entity.setCpf(userDTO.getCpf());

        Role role = roleRepository.findById(userDTO.getRoles().get(0).getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        entity.addRole(role);
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO login(UserDTO userDTO) {
        User entity = userRepository
                .findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email and password"));
        return new UserDTO(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByCpf(username);
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User not found with username");
        }
        return userEntity;
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User entity = authService.authenticated();
        return new UserDTO(entity);
    }
}
