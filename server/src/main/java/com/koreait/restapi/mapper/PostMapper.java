package com.koreait.restapi.mapper;

import com.koreait.restapi.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(PostDTO post);

    List<PostDTO> getPosts(@Param("offset") int offset, @Param("limit") int limit);

    int countAllPosts();

    PostDTO findById(int id);

    void deletePost(int id);

    void updatePost(PostDTO post);
}
