package rut.miit.k2pgshard.service;


import rut.miit.k2pgshard.dto.UserDto;
import rut.miit.k2pgshard.dto.UserUpdateDto;

public interface UserService {
    void registerUser(UserDto dto);
    void updateUserInfo(UserUpdateDto dto);
    UserDto getUser(String id);
}
