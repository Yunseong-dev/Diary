package com.diary.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="black_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistToken {

    @Id
    private String token;

    private String userId;

}
