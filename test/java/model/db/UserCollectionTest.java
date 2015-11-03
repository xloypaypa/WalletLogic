package model.db;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by xlo on 2015/11/3.
 * it's testing for user collection
 */
public class UserCollectionTest {

    @Test
    public void testAddAndGet() {
        UserCollection userCollection = new UserCollection();
        userCollection.addUser("test", "pass");
        userCollection.submit();
        assertNotNull(userCollection.getUser("test"));
    }

}