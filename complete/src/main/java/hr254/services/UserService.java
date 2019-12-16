package hr254.services;

import hr254.models.User;

public interface UserService {
    public User findUserByEmail(String email);
    public User findUserByUsername(String email);
    public User findByEmailOrUsername(String email,String username);
    public void saveUser(User user);
    public void deleteUser(User user);
}