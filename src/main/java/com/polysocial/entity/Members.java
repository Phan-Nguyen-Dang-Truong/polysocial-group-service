package com.polysocial.entity;

import com.polysocial.entity.id.MemberId;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(MemberId.class)
public class Members implements Serializable {

    @Id
    private Long userId;

    @Id
    private Long groupId;

    private Boolean isTeacher;

    private Boolean confirm ;

    @ManyToOne
    @JoinColumn(name = "groupId", insertable = false, updatable = false)
    private Groups group;



    public Members(Long userId, Long groupId, Boolean isTeacher, Boolean confirm) {
        this.userId = userId;
        this.groupId = groupId;
        this.isTeacher = isTeacher;
        this.confirm = confirm;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Members members = (Members) o;
        return userId != null && Objects.equals(userId, members.userId)
                && groupId != null && Objects.equals(groupId, members.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, groupId);
    }
}
