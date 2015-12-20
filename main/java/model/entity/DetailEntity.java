package model.entity;

import model.db.DBTable;
import org.bson.BsonArray;

import java.util.Date;

/**
 * Created by xlo on 15/12/20.
 * it's the detail entity
 */
public class DetailEntity extends Entity {

    public DetailEntity(DBTable.DBData data) {
        super(data);
    }

    public Date getDate() {
        return new Date(Long.parseLong(this.getObject().get("date").toString()));
    }

    public String getEvent() {
        return this.getObject().get("event").toString();
    }

    public BsonArray getRollbackMessage() {
        return (BsonArray) this.getObject().get("roll back call");
    }

    public String getItem(String key) {
        return this.getObject().get(key).toString();
    }

}
