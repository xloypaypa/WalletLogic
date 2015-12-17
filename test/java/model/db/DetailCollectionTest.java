package model.db;

import org.bson.Document;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by xlo on 2015/12/17.
 * it's the testing of detail collection
 */
public class DetailCollectionTest extends DBTesting {

    @Test
    public void should_have_1_data_after_add() throws Exception {
        DetailCollection detailCollection = new DetailCollection();
        detailCollection.addDetail("username", new Date(100), "event", new Document());
        detailCollection.submit();

        assertEquals(1, detailCollection.findDetails("username", new Date(99), new Date(101)).size());
    }

    @Test
    public void should_have_2_data_after_add() throws Exception {
        DetailCollection detailCollection = new DetailCollection();
        detailCollection.addDetail("username", new Date(100), "event", new Document());
        detailCollection.addDetail("username", new Date(101), "event", new Document());
        detailCollection.addDetail("username", new Date(161), "event", new Document());
        detailCollection.submit();

        assertEquals(2, detailCollection.findDetails("username", new Date(50), new Date(150)).size());
    }

    @Test
    public void should_have_data_data_after_add() throws Exception {
        DetailCollection detailCollection = new DetailCollection();
        detailCollection.addDetail("username", new Date(100), "event", new Document());
        detailCollection.addDetail("username", new Date(101), "event", new Document());
        detailCollection.addDetail("username", new Date(161), "event", new Document("message", "abc"));
        detailCollection.submit();

        assertEquals("abc", detailCollection.findDetails("username", new Date(150), new Date(200)).get(0).object.get("message"));
    }

}