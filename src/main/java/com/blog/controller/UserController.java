package com.blog.controller;

import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.service.PostService;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthTreeUI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/posts")
    public Page<Post> findPostByUser(@RequestParam(defaultValue = "1" ) int page,
                                     @RequestParam(defaultValue = "5") int limit,
                                     @RequestParam(defaultValue = "id,asc") String[] sort,
                                     @PathVariable Long id) {
        return postService.getPostsByUserId(page,limit,sort,id);
    }

    //Pagination and Sorting
    @GetMapping("")
    public Page<User> getUsersWithPagination(@RequestParam(defaultValue = "1" ) int page,
                                                     @RequestParam(defaultValue = "5") int limit,
                                                     @RequestParam(defaultValue = "id,asc") String[] sort,
                                                     @RequestParam(defaultValue = "") String pattern){
        return userService.getUsersWithPagination(page, limit,sort,pattern);
    }


    @PostMapping("")
    public List<User> addUser( @RequestBody List<User> users) {
        return userService.saveUsers(users);
    }
    @PutMapping("/{id}")
    public User updateUser(@RequestBody User newUpdateUser, @PathVariable Long id) {
        return userService.updateUser(id, newUpdateUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
         return userService.deleteUser(id);
    }

    @DeleteMapping("")
    public String deleteUser() {
        return userService.deleteAllUsers();
    }
}
