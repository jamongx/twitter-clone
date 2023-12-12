package com.jason.twitter.userservice.controller;

import com.jason.twitter.userservice.dto.AvatarDto;
import com.jason.twitter.userservice.dto.StatusDto;
import com.jason.twitter.userservice.dto.UserProfileDto;
import com.jason.twitter.userservice.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user_profiles")
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<StatusDto> updateUserStatus(@PathVariable Long id, @RequestBody boolean isActive) {
        StatusDto updatedUserProfileStatus = userProfileService.updateStatus(id, isActive);
        return ResponseEntity.ok(updatedUserProfileStatus);
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<AvatarDto> updateAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }
        AvatarDto updatedAvatar = userProfileService.updateAvatar(id, file);
        return ResponseEntity.ok(updatedAvatar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateProfile(@PathVariable Long id, @RequestBody UserProfileDto userProfileDto) {
        UserProfileDto updatedUserProfileDto = userProfileService.updateProfile(userProfileDto);
        return ResponseEntity.ok(updatedUserProfileDto);
    }

}
