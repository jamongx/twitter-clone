package com.jason.twitter.userservice.controller;

import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.ProfileDto;
import com.jason.twitter.userservice.dto.UserDto;
import com.jason.twitter.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto addUserDto = userService.addUser(userDto);
        return new ResponseEntity<>(addUserDto, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long id){
        UserDto updatedUser = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Build Active User REST API
    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("{id}/active")
    public ResponseEntity<UserDto> activeUser(@PathVariable("id") Long id){
        UserDto updatedUser = userService.activeUser(id);
        return ResponseEntity.ok(updatedUser);
    }

    // Build Deactive USER REST API
    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("{id}/deactive")
    public ResponseEntity<UserDto> inCompleteTodo(@PathVariable("id") Long id){
        UserDto updatedUser = userService.deactiveUser(id);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("{id}/avatar")
    public ResponseEntity<AvatarDto> addAvatar(@PathVariable("id") Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        AvatarDto updatedAvatar = userService.addAvatar(id, file);
        return ResponseEntity.ok(updatedAvatar);
    }

    @PatchMapping("{id}/profile")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable("id") Long id, @RequestBody ProfileDto profileDto){
        ProfileDto updatedProfileDto = userService.updateProfile(profileDto);
        return ResponseEntity.ok(updatedProfileDto);
    }

}
