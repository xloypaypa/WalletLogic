package model.entity;

import model.db.DBTable;

import java.util.Map;

/**
 * Created by xlo on 15-12-20.
 * it's the entity
 */
public abstract class Entity {

    protected DBTable.DBData data;

    protected Entity(DBTable.DBData data) {
        this.data = data;
    }

    protected Map<String, Object> getObject() {
        return data.object;
    }

}
