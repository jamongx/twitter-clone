package com.jason.twitter.userservice.controller;

import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.UserProfileDto;
import com.jason.twitter.userservice.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user_profiles")
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PatchMapping("/{id}/active")
    public ResponseEntity<UserProfileDto> activeUser(@PathVariable Long id) {
        UserProfileDto updatedProfileUser = userProfileService.activeUser(id);
        return ResponseEntity.ok(updatedProfileUser);
    }

    @PatchMapping("/{id}/deactive")
    public ResponseEntity<UserProfileDto> deactivateUser(@PathVariable Long id) {
        UserProfileDto updatedProfileUser = userProfileService.deactiveUser(id);
        return ResponseEntity.ok(updatedProfileUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateProfile(@PathVariable Long id, @RequestBody UserProfileDto userProfileDto) {
        UserProfileDto updatedUserProfileDto = userProfileService.updateProfile(userProfileDto);
        return ResponseEntity.ok(updatedUserProfileDto);
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<AvatarDto> updateAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        AvatarDto updatedAvatar = userProfileService.updateAvatar(id, file);
        return ResponseEntity.ok(updatedAvatar);
    }

}
