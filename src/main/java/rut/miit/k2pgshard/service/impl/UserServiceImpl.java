package rut.miit.k2pgshard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.k2pgshard.dto.UserAddDto;
import rut.miit.k2pgshard.dto.UserDto;
import rut.miit.k2pgshard.dto.UserUpdateDto;
import rut.miit.k2pgshard.entity.User;
import rut.miit.k2pgshard.repository.UserRepository;
import rut.miit.k2pgshard.service.UserService;


import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto registerUser(UserAddDto dto) {
        User user = new User(dto.firstName(), dto.email(), dto.phoneNumber(), dto.birthDate(), dto.country());
        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public void updateUserInfo(UserUpdateDto dto) {
        User user = userRepository.findById(UUID.fromString(dto.id())).orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.id()));
        user.setFirstName(dto.firstName());
        user.setEmail(dto.email());
        user.setPhoneNumber(dto.phoneNumber());
        user.setCountry(dto.country());
        userRepository.update(user);
    }

    @Override
    public UserDto getUser(String id) {
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toDto(user);
    }


    private UserDto toDto(User user){
        return new UserDto(user.getId().toString(), user.getFirstName(), user.getEmail(), user.getPhoneNumber(), user.getBirthDate(), user.getCountry());
    }

}
