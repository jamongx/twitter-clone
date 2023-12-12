package com.jason.twitter.userservice.service;


import com.jason.twitter.userservice.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Long id);

    UserDto getUser(Long id);

    void deleteById(Long id);

}
