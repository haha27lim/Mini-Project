package sg.edu.nus.iss.miniproject.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.Role;
import sg.edu.nus.iss.miniproject.models.User;


@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private RoleRepository roleRepo;


    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);

        Long userId = keyHolder.getKey().longValue();
        user.setId(userId);

        for (Role role : user.getRoles()) {
            if (role.getId() == null) {
                role = roleRepo.save(role);
            }
            template.update(
                 "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)",
                 userId,
                 role.getId()
            );
        }
        return user;
    }

    
    public Optional<User> findByUsername(String username) {
        try {
            User user = template.queryForObject("SELECT * FROM users WHERE username = ?", BeanPropertyRowMapper.newInstance(User.class), username);
            if (user != null) {
                List<Role> roles = template.query("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?", BeanPropertyRowMapper.newInstance(Role.class), user.getId());
                user.setRoles(new HashSet<>(roles));
    
                System.out.println("User: " + user.toString());
                System.out.println("Roles: " + roles.toString());
            }
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    public Optional<User> findByEmail(String email) {
        try {
            User user = template.queryForObject("SELECT * FROM users WHERE email = ?", BeanPropertyRowMapper.newInstance(User.class), email);
            if (user != null) {
                List<Role> roles = template.query("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?", BeanPropertyRowMapper.newInstance(Role.class), user.getId());
                user.setRoles(new HashSet<>(roles));
    
                System.out.println("User: " + user.toString());
                System.out.println("Roles: " + roles.toString());
            }
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    public Boolean existsByUsername(String username) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM users WHERE username = ?", Integer.class, username);
        return count != null && count > 0;
    }

    
    public Boolean existsByEmail(String email) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, email);
        return count != null && count > 0;
    }
    
    public List<User> findAll() {
        List<User> users = template.query("SELECT * FROM users", BeanPropertyRowMapper.newInstance(User.class));
        for (User user : users) {
            List<Role> roles = template.query("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?",
                    BeanPropertyRowMapper.newInstance(Role.class), user.getId());
            user.setRoles(new HashSet<>(roles));
        }
        return users;
    }
    
    public User findById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User user = template.queryForObject(query, BeanPropertyRowMapper.newInstance(User.class), id);
        if (user != null) {
            List<Role> roles = template.query("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?",
                    BeanPropertyRowMapper.newInstance(Role.class), id);
            user.setRoles(new HashSet<>(roles));
        }
        return user;
    }
    

    public void deleteById(Long id) {
        String deleteRoles = "DELETE FROM user_roles WHERE user_id = ?";
        template.update(deleteRoles, id);
    
        String deleteUser = "DELETE FROM users WHERE id = ?";
        template.update(deleteUser, id);
    }
    

    public User update(User user) {
        template.update(
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?",
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getId()
        );
    
        template.update("DELETE FROM user_roles WHERE user_id = ?", user.getId());
        for (Role role : user.getRoles()) {
            if (role.getId() == null) {
                role = roleRepo.save(role);
            }
            template.update(
                "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)",
                user.getId(),
                role.getId()
            );
        }
    
        return user;
    }
    
    
    
    
}

