package escapegen.model;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author - Vita Loginova
 */
public class LockTest {

    private class TestingLock extends Lock {
        int count = 0;

        public TestingLock(String name) {
            tools.put(name, new Tool(name) {
                @Override
                public void examine() {

                }
            });
        }

        @Override
        protected boolean unlock(Tool tool) {
            count++;
            return tools.containsValue(tool);
        }
    }

    @Test
    public void testTryUnlock() throws Exception {
        TestingLock lock = new TestingLock("Key");
        List<Tool> tools = new LinkedList<>(lock.getTools());

        assertFalse(lock.tryUnlock(null));
        assertTrue(lock.tryUnlock(tools.get(0)));

        assertTrue(lock.tryUnlock(null));
        assertEquals(2, lock.count);
    }
}