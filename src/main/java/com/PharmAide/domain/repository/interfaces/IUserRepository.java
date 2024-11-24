package com.PharmAide.domain.repository.interfaces;



import com.PharmAide.domain.dao.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    public List<User> getAllUsers();

    public Optional<User> addUser(User user);

    public Optional<User> getbyID(int id);

    public Optional<User> getbyUsername(String username);

    public Optional<User> updateUser(User newuser, String s);

    public void deleteUser(String username);
}
