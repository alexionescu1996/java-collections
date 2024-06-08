package org.example.model;


import lombok.Data;

import java.util.Objects;

@Data
public class UserProfile {

    private String name;
    private int id;

    public UserProfile(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public UserProfile(String name) {
        this.name = name;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;

        UserProfile that = (UserProfile) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + id;
        return result;
    }
}
