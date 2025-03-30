package org.example.postbin.Post.persistence.db.model;

import java.io.Serializable;

public interface BaseEntity <T extends Serializable> {
    void setId(T id);

    T getId();
}
