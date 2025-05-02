package rut.miit.k2pgshard.service;


import rut.miit.k2pgshard.dto.UserAddDto;
import rut.miit.k2pgshard.dto.UserDto;
import rut.miit.k2pgshard.dto.UserUpdateDto;

public interface UserService {
    UserDto registerUser(UserAddDto dto);
    void updateUserInfo(UserUpdateDto dto);
    UserDto getUser(String id);
}
