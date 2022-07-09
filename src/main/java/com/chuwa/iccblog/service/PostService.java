package com.chuwa.iccblog.service;

import com.chuwa.iccblog.payload.PostDto;
import com.chuwa.iccblog.payload.PostResponse;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPost();

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
