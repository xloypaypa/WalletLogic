package model.db;

import org.bson.Document;

import java.util.List;

/**
 * Created by xlo on 2015/12/28.
 * it's the user license
 */
public class UserLicenseCollection extends WalletCollection {

    public void giveLicense(String username, String license) {
        Document document = new Document("username", username).append("license", license);
        this.insertData(document);
    }

    public boolean haveLicense(String username, String license) {
        List<DBData> dataList = this.getAllDataListData(new Document("username", username).append("license", license));
        return dataList.size() != 0;
    }

    public void removeLicense(String username, String license) {
        this.lockCollection();
        Document document = new Document("username", username).append("license", license);
        this.remove(document);
    }

}
