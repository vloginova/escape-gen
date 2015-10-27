package escapegen.model;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

/**
 * @author - Vita Loginova
 */
public class ContainerTest {

    @Test
    public void testTryOpen() throws Exception {
        Container container = new Container("TestContainer", Item.Size.Small) {
            @Override
            public void showContent() {
                /* do nothing */
            }
        };

        assertTrue(container.tryOpen(new LinkedList<>()));
    }
}