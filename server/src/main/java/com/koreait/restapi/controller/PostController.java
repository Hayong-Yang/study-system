package com.koreait.restapi.controller;

import com.koreait.restapi.dto.PostDTO;
import com.koreait.restapi.jwt.JwtUtil;
import com.koreait.restapi.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final JwtUtil jwtUtil;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostDTO post, HttpServletRequest request) {
        int userId = jwtUtil.getUserIdFromRequest(request);
        post.setWriterId(userId);
        postService.insertPost(post);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        List<PostDTO> posts = postService.getPosts(page, size);
        int totalCount = postService.getTotalPostCount();

        Map<String, Object> result = new HashMap<>();
        result.put("posts", posts);
        result.put("totalCount", totalCount);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id,
                                        HttpServletRequest request) {
        int userId = jwtUtil.getUserIdFromRequest(request);

        PostDTO post = postService.findById(id);
        if (post.getWriterId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("삭제 권한이 없습니다.");
        }

        postService.deletePost(id);
        return ResponseEntity.ok().body("삭제 완료");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable int id,
                                        @RequestBody PostDTO dto,
                                        HttpServletRequest request) {
        int userId = jwtUtil.getUserIdFromRequest(request);

        PostDTO post = postService.findById(id);
        if (post.getWriterId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("수정 권한이 없습니다.");
        }

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        postService.updatePost(post);

        return ResponseEntity.ok("수정 완료");
    }



} // class
