package com.koreait.restapi.service;

import com.koreait.restapi.dto.PostDTO;
import com.koreait.restapi.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;

    @Override
    public void insertPost(PostDTO postDTO) {
        postMapper.insertPost(postDTO);
    }

    @Override
    public List<PostDTO> getPosts(int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.getPosts(offset, size);
    }

    @Override
    public int getTotalPostCount() {
        return postMapper.countAllPosts();
    }

    @Override
    public PostDTO findById(int postId) {
        PostDTO result = postMapper.findById(postId);
        return result;
    }

    @Override
    public void deletePost(int postId) {
        PostDTO result = postMapper.findById(postId);
        if (result == null) {
            throw new RuntimeException("게시물이 존재하지 않습니다.");
        }
        postMapper.deletePost(postId);
    }

    @Override
    public void updatePost(PostDTO post) {
        postMapper.updatePost(post);
    }
} // class