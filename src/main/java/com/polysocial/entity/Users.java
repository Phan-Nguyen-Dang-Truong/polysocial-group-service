package com.polysocial.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String fullName;

    private String studentCode;

    private String email;

    private String password;

    private String avatar;

    private boolean isActive;

    private LocalDateTime createdDate;

    private Long roleId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Users users = (Users) o;
        return userId != null && Objects.equals(userId, users.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
