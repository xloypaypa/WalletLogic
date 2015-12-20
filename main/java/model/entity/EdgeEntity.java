package model.entity;

import model.db.DBTable;

/**
 * Created by xlo on 15/12/20.
 * it's the edge entity
 */
public class EdgeEntity extends Entity {

    public EdgeEntity(DBTable.DBData data) {
        super(data);
    }

    public String getFrom() {
        return this.getObject().get("from").toString();
    }

    public String getTo() {
        return this.getObject().get("to").toString();
    }

    public String getScript() {
        return this.getObject().get("script").toString();
    }

    public String setFrom(String from) {
        return this.getObject().put("from", from).toString();
    }

    public String setTo(String to) {
        return this.getObject().put("to", to).toString();
    }

    public String setScript(String script) {
        return this.getObject().put("script", script).toString();
    }
}
