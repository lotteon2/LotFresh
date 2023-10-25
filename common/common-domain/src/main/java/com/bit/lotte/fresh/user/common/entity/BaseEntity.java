package com.bit.lotte.fresh.user.common.entity;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity<ID> {

    protected BaseEntity() {
        this.entityId = null;
    }

    private ID entityId;

    public ID getEntityId() {
        return entityId;
    }

    public void setEntityId(ID id) {
        this.entityId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return entityId.equals(that.entityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId);
    }
}
