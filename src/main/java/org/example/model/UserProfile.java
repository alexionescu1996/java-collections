package org.example.model;


import lombok.Data;

import java.util.Objects;

@Data
public class UserProfile {

    private String name;
    private Integer id;

    public UserProfile(String name, Integer id) {
        this.name = name;
        this.id = id;
    }
    public UserProfile(String name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile that)) return false;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
       return 31 * id.hashCode();
    }
}
