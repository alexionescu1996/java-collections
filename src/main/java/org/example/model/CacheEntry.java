package org.example.model;

import lombok.Data;

import java.util.Date;

@Data
public class CacheEntry {
    private String token;
    private Date createdOn;
    private Date updatedOn;

    public CacheEntry(String token, Date createdOn, Date updatedOn) {
        this.token = token;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public CacheEntry() {
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheEntry that)) return false;

        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        return 31 * token.hashCode();
    }
}
