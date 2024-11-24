package com.PharmAide.domain.service;


import com.PharmAide.domain.dao.User;
import com.PharmAide.domain.repository.interfaces.IUserRepository;
import jakarta.inject.Inject;

import java.util.List;

public class UserService {
    IUserRepository userRepo;

    @Inject
    public UserService(IUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user) {
        var res = userRepo.addUser(user);
        return res.orElse(null);
    }

    public List<User> getAll() {
        return userRepo.getAllUsers();
    }

    public User getUserbyID(int id) {
        var res = userRepo.getbyID(id);
        return res.orElse(null);
    }

    public User getbyUsername(String name) {
        var res = userRepo.getbyUsername(name);
        return res.orElse(null);
    }

    public User update(User user, String s) {
        var res = userRepo.updateUser(user, s);
        return res.orElse(null);
    }

    public void delete(String s) {
        userRepo.deleteUser(s);
    }
}
