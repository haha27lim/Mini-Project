package sg.edu.nus.iss.miniproject.models;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private Date createdAt;
    private Date updatedAt;
    private String role;
    private String status;
}
