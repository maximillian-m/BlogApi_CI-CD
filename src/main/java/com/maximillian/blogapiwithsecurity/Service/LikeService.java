package com.maximillian.blogapiwithsecurity.Service;

import com.maximillian.blogapiwithsecurity.Dto.LikeDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;

public interface LikeService {
    void likeOrUnlikePost(LikeDto likeDto) throws CustomException;

    void checkLike(LikeDto likeDto) throws CustomException;
}
