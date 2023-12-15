package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.FollowDto;

import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.DuplicateResourceException;
import com.jason.twitter.userservice.exception.ResourceNotFoundException;
import com.jason.twitter.userservice.mapper.UserMapper;
import com.jason.twitter.userservice.repository.FollowRepository;
import com.jason.twitter.userservice.repository.UserRepository;
import com.jason.twitter.userservice.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

//import org.modelmapper.ModelMapper;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;

    private UserRepository userRepository;

    private FollowerDto mapFollowToDto(Follow follow, Function<Long, Optional<User>> userFinder) {
        return userFinder.apply(follow.getFollowingId())
                .map(user -> UserMapper.mapToFollowerDto(follow.getId(), user))
                .orElse(null);
    }

    // 내가 following 하고 있는 사람들
    @Override
    public List<FollowerDto> getFollowers(Long userId) {
        return followRepository.findByFollowersId(userId)
                .stream()
                .map(follow -> mapFollowToDto(follow, userRepository::findById))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 나를 following 하고 있는 사람들
    @Override
    public List<FollowerDto> getFollowing(Long userId) {
        return followRepository.findByFollowingId(userId)
                .stream()
                .map(follow -> mapFollowToDto(follow, id -> userRepository.findById(follow.getFollowersId())))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public FollowDto createFollow(FollowDto followDto) {
        //Convert FollowDto into Follow Jpa entity
        //Not Work
        //Follow follow = modelMapper.map(followDto, Follow.class);

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
        // FollowDto savedFollowDto = modelMapper.map(savedFollow, FollowDto.class);
        FollowDto savedFollowDto = new FollowDto();
        savedFollowDto.setFollowersId(savedFollow.getFollowersId());
        savedFollowDto.setFollowingId(savedFollow.getFollowingId());
        return savedFollowDto;
    }

    @Override
    public void deleteFollow(Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Follow not found with id : " +id));
        followRepository.deleteById(follow.getId());
    }

}
