package escapegen.model;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author - Vita Loginova
 */
public class ContainerTest {
    private class TestingContainer extends Container {

        protected TestingContainer(String id) {
            super(id, Size.Medium);
            lock = new Lock() {
                @Override
                protected boolean unlock(Tool tool) {
                    return true;
                }
            };
            lock.addTool(new Tool(id + "Tool", Size.Small) {
                @Override
                public void examine() {

                }
            });
        }

        @Override
        public void showContent() { }

        @Override
        public void examine() {

        }
    }

    @Test
    public void testTryOpen() throws Exception {
        Container container = new Container("TestContainer", Item.Size.Small) {
            @Override
            public void examine() {

            }

            @Override
            public void showContent() {
                /* do nothing */
            }
        };

        assertTrue(container.tryOpen(null));
    }

    @Test
    public void testDependsOn() throws Exception {
        Container c1 = new TestingContainer("1");
        List<Tool> tools1 = new LinkedList<>(c1.getLockTools());
        Container c2 = new TestingContainer("2");
        List<Tool> tools2 = new LinkedList<>(c2.getLockTools());
        Container c3 = new TestingContainer("3");
        List<Tool> tools3 = new LinkedList<>(c3.getLockTools());

        c1.addDependencies(c2);
        c1.addDependencies(c3);

        assertTrue(c1.dependsOn(tools2.get(0)));
        assertTrue(c1.dependsOn(tools3.get(0)));
        assertTrue(c1.dependsOn(c2));
        assertTrue(c1.dependsOn(c3));

        assertFalse(c2.dependsOn(tools1.get(0)));
        assertFalse(c2.dependsOn(c1));

        assertFalse(c3.dependsOn(tools1.get(0)));
        assertFalse(c3.dependsOn(c1));
    }

    @Test
    public void testDependsOnTransitive() throws Exception {
        Container c1 = new TestingContainer("1");
        List<Tool> tools1 = new LinkedList<>(c1.getLockTools());
        Container c2 = new TestingContainer("2");
        List<Tool> tools2 = new LinkedList<>(c2.getLockTools());
        Container c3 = new TestingContainer("3");
        List<Tool> tools3 = new LinkedList<>(c3.getLockTools());

        c1.addDependencies(c2);
        c2.addDependencies(c3);

        assertTrue(c1.dependsOn(tools2.get(0)));
        assertTrue(c1.dependsOn(tools3.get(0)));
        assertTrue(c1.dependsOn(c2));
        assertTrue(c1.dependsOn(c3));

        assertFalse(c2.dependsOn(tools1.get(0)));
        assertFalse(c2.dependsOn(c1));
        assertTrue(c2.dependsOn(tools3.get(0)));
        assertTrue(c2.dependsOn(c3));

        assertFalse(c3.dependsOn(tools1.get(0)));
        assertFalse(c3.dependsOn(c1));
    }

    @Test(expected = RuntimeException.class)
    public void testDependsOnCircular() throws Exception {
        Container c1 = new TestingContainer("1");
        Container c2 = new TestingContainer("2");
        Container c3 = new TestingContainer("3");

        c2.addDependencies(c3);
        c1.addDependencies(c2);
        c3.addDependencies(c1);
    }
}