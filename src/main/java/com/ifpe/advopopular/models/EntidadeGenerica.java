package com.ifpe.advopopular.models;

import java.io.Serial;
import java.io.Serializable;

public abstract class EntidadeGenerica<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -6600392431737812418L;

    public abstract void setId(T object);
    public abstract  T getId();

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !this.getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }

        EntidadeGenerica comparable = (EntidadeGenerica) obj;

        return (this.getId() != null && this.getId().equals(comparable.getId()) || super.equals(obj));
    }
}
