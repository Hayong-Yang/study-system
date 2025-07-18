package com.koreait.restapi.service;

import com.koreait.restapi.dto.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void insertPost(PostDTO postDTO);
    List<PostDTO> getPosts(int page, int size);
    int getTotalPostCount();
    PostDTO findById(int id);

    void deletePost(int id);
    void updatePost(PostDTO postDTO);

}
