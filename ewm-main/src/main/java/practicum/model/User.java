package practicum.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private UUID id;

    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;

}
