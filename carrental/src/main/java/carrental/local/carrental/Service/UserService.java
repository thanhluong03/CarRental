package carrental.local.carrental.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrental.local.carrental.Entity.User;
import carrental.local.carrental.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public Optional<User> findByCccd(String cccd) {
        return userRepository.findByCccd(cccd);
    }


    

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }


    public User updateUser(User user) {
        return userRepository.save(user);  
    }
    


    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
