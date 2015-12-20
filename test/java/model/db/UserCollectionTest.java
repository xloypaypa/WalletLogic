package model.db;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by xlo on 2015/11/3.
 * it's testing for user collection
 */
public class UserCollectionTest extends DBTesting {

    @Test
    public void shouldHaveUserDataAfterAddUser() {
        UserCollection userCollection = new UserCollection();
        userCollection.addUser("test", "pass");
        userCollection.submit();
        assertNotNull(userCollection.getUserData("test"));
    }

    @Test
    public void shouldGetNullWhenNotHaveData() throws Exception {
        UserCollection userCollection = new UserCollection();
        userCollection.addUser("test", "pass");
        userCollection.submit();
        assertNull(userCollection.getUserData("test1"));
    }

    @Test
    public void shouldChangeUserDataAfterChangeUserData() {
        UserCollection userCollection = new UserCollection();
        userCollection.addUser("test", "pass");
        userCollection.submit();

        userCollection.getUser("test").setPassword("password");
        userCollection.submit();

        assertEquals("password", userCollection.getUserData("test").getPassword());
    }

}