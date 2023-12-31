package com.jason.twitter.userservice.service;


import com.jason.twitter.userservice.dto.FollowDto;
import com.jason.twitter.userservice.dto.FollowerDto;

import java.util.List;

public interface FollowService {

    List<FollowerDto> getFollowing(Long userId);

    List<FollowerDto> getFollowers(Long userId);

    FollowDto createFollow(FollowDto followDto);

    void deleteFollow(Long id);

}
