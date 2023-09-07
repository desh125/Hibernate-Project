package lk.ijse.orm.hibernate_project.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {
    private String id;
    private String name;
    private String password;
}

