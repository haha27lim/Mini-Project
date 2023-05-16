package sg.edu.nus.iss.miniproject.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.miniproject.exception.NotFoundException;
import sg.edu.nus.iss.miniproject.models.User;
import sg.edu.nus.iss.miniproject.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User saveUser(User user) {
		return userRepo.save(user);
	}

	public User getUserById(Long id) {
		return userRepo.findById(id);
	}

	public User updateUser(User user) {
		return userRepo.update(user);
	}

	public void deleteUserById(Long id) {
		User user = userRepo.findById(id);
		if (user == null) {
			throw new NotFoundException("User not found.");
		}
		userRepo.deleteById(id);
	}
}
