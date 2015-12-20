package model.entity;

import model.db.DBTable;

/**
 * Created by xlo on 15/12/20.
 * it's the user entity
 */
public class UserEntity extends Entity {
    public UserEntity(DBTable.DBData data) {
        super(data);
    }

    public String getName() {
        return this.getObject().get("username").toString();
    }

    public String getPassword() {
        return this.getObject().get("password").toString();
    }

    public String setPassword(String password) {
        return this.getObject().put("password", password).toString();
    }
}
