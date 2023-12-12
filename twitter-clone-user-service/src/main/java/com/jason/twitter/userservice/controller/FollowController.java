package com.jason.twitter.userservice.controller;

import com.jason.twitter.userservice.dto.FollowDto;
import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.service.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/follows")
@AllArgsConstructor
public class FollowController {

    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);
    private final FollowService followService;

    @GetMapping("/{id}/following")
    public ResponseEntity<List<FollowerDto>> getFollowing(@PathVariable Long id) {
        logger.info("Getting following for user id: {}", id);
        List<FollowerDto> followDtos = followService.getFollowing(id);
        return ResponseEntity.ok(followDtos);
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<FollowerDto>> getFollowers(@PathVariable Long id) {
        logger.info("Getting followers for user id: {}", id);
        List<FollowerDto> followerDtos = followService.getFollowers(id);
        return ResponseEntity.ok(followerDtos);
    }

    @PostMapping()
    public ResponseEntity<FollowDto> addFollow(@RequestBody FollowDto followDto) {
        logger.info("Creating followers for follower id: {}, following id: {}",
                followDto.getFollowersId(), followDto.getFollowingId());
        FollowDto addFollowDto = followService.createFollow(followDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addFollowDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFollow(@PathVariable Long id) {
        logger.info("Deleting a follow by follow id: {}", id);
        followService.deleteFollow(id);
        return ResponseEntity.noContent().build();
    }

}
