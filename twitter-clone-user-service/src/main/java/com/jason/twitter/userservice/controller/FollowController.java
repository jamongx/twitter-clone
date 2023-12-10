package com.jason.twitter.userservice.controller;

import com.jason.twitter.userservice.dto.FollowDto;
import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/api/following/{userId}")
    public ResponseEntity<List<FollowerDto>> getFollowing(@PathVariable Long userId) {
        List<FollowerDto> followDtos = followService.getFollowing(userId);
        System.out.println("TEST=" + followDtos);
        return ResponseEntity.ok(followDtos);
    }

    @GetMapping("/api/followers/{userId}")
    public ResponseEntity<List<FollowerDto>> getFollowers(@PathVariable Long userId) {
        List<FollowerDto> followerDtos = followService.getFollowers(userId);
        System.out.println("TEST=" + followerDtos);
        return ResponseEntity.ok(followerDtos);
    }


    @PostMapping("/api/follow")
    public ResponseEntity<FollowDto> addFollow(@RequestBody FollowDto followDto) {
        FollowDto addFollowDto = followService.follow(followDto);
        return new ResponseEntity<>(addFollowDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/follow/{id}")
    public ResponseEntity<Void> deleteFollow(@PathVariable Long id) {
        System.out.println("TEST=" + id);
        followService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
