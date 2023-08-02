package com.blog.service;

import com.blog.entity.User;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User " + id + " removed!!";
    }

    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "Delete all User!!";
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setPhone(user.getPhone());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }
    public Page<User> getUsersWithPagination(int page, int limit, String[] sort, String pattern) {

        Pageable pageable = getPageable(page, limit, sort);
        Page<User> allUsers = userRepository.findByNameOrEmailContaining(pattern, pattern, pageable);
        //Need caculate offset = limit*pageIndex -> If want in page 1 GUI, we need pageIndex =0 -> offset = 0*10=0;
        return allUsers;
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
