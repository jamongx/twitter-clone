package com.jason.twitter.userservice.service.impl;

import com.jason.twitter.userservice.dto.FollowDto;

import com.jason.twitter.userservice.dto.FollowerDto;
import com.jason.twitter.userservice.entity.Follow;
import com.jason.twitter.userservice.entity.User;
import com.jason.twitter.userservice.exception.DuplicateResourseException;
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
import java.util.stream.Collectors;

//import org.modelmapper.ModelMapper;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private FollowRepository followRepository;

    private UserRepository userRepository;

    //private ModelMapper modelMapper;

    // 내가 following 하고 있는 사람들
    @Override
    public List<FollowerDto> getFollowers(Long userId) {
        List<Follow> follows = followRepository.findByFollowersId(userId);
        return follows.stream().map((follow) -> {
                    Optional<User> user = userRepository.findById(follow.getFollowingId());
                    if (user.isPresent()) {
                        return UserMapper.mapToFollowerDto(follow.getId(), user.get());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // 나를 following 하고 있는 사람들
    @Override
    public List<FollowerDto> getFollowing(Long userId) {
        List<Follow> follows = followRepository.findByFollowingId(userId);
        return follows.stream().map((follow) -> {
                    // 나를 following 하고 있는 사람들
                    Optional<User> user = userRepository.findById(follow.getFollowersId());
                    if (user.isPresent()) {
                        // Follow의 아이디와 User의 전체정보
                        return UserMapper.mapToFollowerDto(follow.getId(), user.get());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public FollowDto follow(FollowDto followDto) {
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
            throw new DuplicateResourseException("follow already exist. " +follow);
        }

        // Convert saved Follow Jpa entity object into FollowDto object
        // FollowDto savedFollowDto = modelMapper.map(savedFollow, FollowDto.class);
        FollowDto savedFollowDto = new FollowDto();
        savedFollowDto.setFollowersId(savedFollow.getFollowersId());
        savedFollowDto.setFollowingId(savedFollow.getFollowingId());
        return savedFollowDto;
    }

    @Override
    public void deleteById(Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Follow not found with id : " +id));
        System.out.println("TEST="+follow);
        followRepository.deleteById(follow.getId());
    }

}
