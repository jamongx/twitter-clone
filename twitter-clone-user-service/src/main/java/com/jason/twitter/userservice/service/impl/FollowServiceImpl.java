package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.FollowDto;

import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.exception.DuplicateResourceException;
import com.jason.twitter.userservice.exception.ResourceNotFoundException;
import com.jason.twitter.userservice.mapper.Mapper;
import com.jason.twitter.userservice.repository.FollowRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;

    private UserRepository userRepository;

    // Users who I'm following
    @Override
    public List<FollowerDto> getFollowers(Long userId) {
        return followRepository.findByFollowersId(userId)
                .stream()
                .map(follow -> Mapper.mapFollowerDto(follow, userRepository::findById))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // Users who are following me
    @Override
    public List<FollowerDto> getFollowing(Long userId) {
        return followRepository.findByFollowingId(userId)
                .stream()
                .map(follow -> Mapper.mapFollowerDto(follow, id -> userRepository.findById(follow.getFollowersId())))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public FollowDto createFollow(FollowDto followDto) {
        Follow follow = new Follow();
        follow.setFollowersId(followDto.getFollowersId());
        follow.setFollowingId(followDto.getFollowingId());

        Follow savedFollow = null;
        try {
            // Follow Jpa entity
            savedFollow = followRepository.save(follow);
        } catch (Exception e) {
            throw new DuplicateResourceException("follow already exist. " +follow);
        }

        // Convert saved Follow Jpa entity object into FollowDto object
        FollowDto savedFollowDto = new FollowDto(
                savedFollow.getFollowersId(),
                savedFollow.getFollowingId()
        );
        return savedFollowDto;
    }

    @Override
    public void deleteFollow(Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Follow not found with id : " +id));
        followRepository.deleteById(follow.getId());
    }

}
