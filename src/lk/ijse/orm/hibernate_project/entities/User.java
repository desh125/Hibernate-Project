package lk.ijse.orm.hibernate_project.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class User {
    @Id
    private String id1;
    private String name;
    private String password;
}
