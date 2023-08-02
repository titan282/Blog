package com.blog.controller;

import com.blog.entity.Post;
import com.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    //SortAll
    @GetMapping("/posts/all")
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public Post findPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    //Pagination and Sorting
    @GetMapping("/posts")
    public Page<Post> getPostsWithPagination(@RequestParam(defaultValue = "1" ) int page,
                                             @RequestParam(defaultValue = "5") int limit,
                                             @RequestParam(defaultValue = "id,asc") String[] sort,
                                             @RequestParam(defaultValue = "") String pattern){
        return postService.getPostsWithPagination(page, limit,sort,pattern);
    }

    @PostMapping("/posts")
    public List<Post> addPost( @RequestBody List<Post> posts) {
        return postService.savePosts(posts);
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@RequestBody Post newUpdatePost, @PathVariable Long id) {
        return postService.updatePost(id, newUpdatePost);
    }

    @DeleteMapping("/posts/{id}")
    public String deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }

    @DeleteMapping("/posts")
    public String deletePost() {
        return postService.deleteAllPosts();
    }
}
