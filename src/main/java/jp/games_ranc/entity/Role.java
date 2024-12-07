package jp.games_ranc.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Role {
    private String name;  // "ROLE_USER", "ROLE_ADMIN" 등
} 