package sg.edu.nus.iss.miniproject.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.miniproject.models.ERole;
import sg.edu.nus.iss.miniproject.models.Role;


@Repository
public class RoleRepository {
    
    @Autowired
    private JdbcTemplate template;
    
    public Role save(Role role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO roles (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getName().toString());
            return ps;
        }, keyHolder);

        Integer roleId = (Integer) keyHolder.getKeys().get("id");
        role.setId(roleId);
        
        return role;
    }

    
    public Optional<Role> findByName(ERole eRole) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        Role role = template.queryForObject(sql, BeanPropertyRowMapper.newInstance(Role.class), eRole.toString());
        return Optional.ofNullable(role);
    }
}