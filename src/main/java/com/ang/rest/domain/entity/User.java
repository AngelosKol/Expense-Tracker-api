package com.ang.rest.domain.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue
    public Long id;
    public String firstname;
    public String lastname;
    public String email;
    public String password;

    @OneToMany(mappedBy = "user")
    public List<Token> tokens;

    @Override
    public boolean equals(final Object o) {
        if(o == this) return true;
        if( o == null ||  getClass() != o.getClass()) return false;
        final User other = (User)  o;
        return Objects.equals(id, other.id);
    }
    @Override
    public int hashCode() {
        final int prime = 59;
        int result = 1;
        result = result * prime + (id == null ? 43 : id.hashCode());
        return result;
    }

}
