package com.blog.service;

import com.blog.entity.Post;
import com.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public List<Post> savePosts(List<Post> posts) {
        return postRepository.saveAll(posts);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public String deletePost(Long id) {
        postRepository.deleteById(id);
        return "Post " + id + " removed!!";
    }

    public String deleteAllPosts() {
        postRepository.deleteAll();
        return "Delete all Post!!";
    }

    public Post updatePost(Long id, Post post) {
        Post existingPost = postRepository.findById(id).orElse(null);
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        return postRepository.save(existingPost);
    }

    public Page<Post> getPostsByUserId(int page, int limit, String[] sort, Long userId) {

        Pageable pageable = getPageable(page, limit, sort);
        Page<Post> allPosts = postRepository.findByUserId(userId, pageable);
        return allPosts;
    }

    public Page<Post> getPostsWithPagination(int page, int limit, String[] sort, String pattern) {

        Pageable pageable = getPageable(page, limit, sort);
        Page<Post> allPosts = postRepository.findByTitleContaining(pattern, pageable);
        return allPosts;
    }

    private Pageable getPageable(int page, int limit, String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if(sort[0].contains(",")){
            for(String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                orders.add(getSortWithFieldAndDirection(_sort[1], _sort[0]));
            }
        } else {
            orders.add(getSortWithFieldAndDirection(sort[1],sort[0]));
        }

        Pageable pageable = PageRequest.of(page-1, limit, Sort.by(orders));
        return pageable;
    }
    private Sort.Order getSortWithFieldAndDirection(String direction, String field) {
        Sort.Direction directionObj = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return new Sort.Order(directionObj, field);
    }
}
