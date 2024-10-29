package jp.games_ranc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "user")
public class User {

    @Id
    private Long id;

    @Field
    private String email;

    @Field
    private String nickname;

    @Field
    private String password;

    @Field
    private String secondaryPassword;

    @Field
    private String phoneNumber;

    @Field
    private LocalDateTime createdAt;

    @Field
    private LocalDateTime updatedAt;
}
