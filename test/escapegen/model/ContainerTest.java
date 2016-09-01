//package escapegen.model;
//
//import org.junit.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
///**
// * @author - Vita Loginova
// */
//public class ContainerTest {
//    private class TestingContainer extends AbstractContainer {
//
//        protected TestingContainer(String id) {
//            super(id);
//            lock = new Lock() {
//                @Override
//                protected boolean unlock(Tool tool) {
//                    return true;
//                }
//            };
//            lock.addTool(new Tool(id + "Tool") {
//                @Override
//                public void examine() {
//
//                }
//            });
//        }
//
//        @Override
//        public void showContent() { }
//
//        @Override
//        public void examine() {
//
//        }
//    }
//
//    @Test
//    public void testTryOpen() throws Exception {
//        AbstractContainer container = new AbstractContainer("TestContainer") {
//            @Override
//            public void examine() {
//
//            }
//
//            @Override
//            public void showContent() {
//                /* do nothing */
//            }
//        };
//
//        assertTrue(container.tryOpen(null));
//    }
//
//    @Test
//    public void testDependsOn() throws Exception {
//        AbstractContainer c1 = new TestingContainer("1");
//        List<Tool> tools1 = new LinkedList<>(c1.getLockTools());
//        AbstractContainer c2 = new TestingContainer("2");
//        List<Tool> tools2 = new LinkedList<>(c2.getLockTools());
//        AbstractContainer c3 = new TestingContainer("3");
//        List<Tool> tools3 = new LinkedList<>(c3.getLockTools());
//
//        c1.addDependency(c2);
//        c1.addDependency(c3);
//
//        assertTrue(c1.dependsOn(tools2.get(0)));
//        assertTrue(c1.dependsOn(tools3.get(0)));
//        assertTrue(c1.dependsOn(c2));
//        assertTrue(c1.dependsOn(c3));
//
//        assertFalse(c2.dependsOn(tools1.get(0)));
//        assertFalse(c2.dependsOn(c1));
//
//        assertFalse(c3.dependsOn(tools1.get(0)));
//        assertFalse(c3.dependsOn(c1));
//    }
//
//    @Test
//    public void testDependsOnTransitive() throws Exception {
//        AbstractContainer c1 = new TestingContainer("1");
//        List<Tool> tools1 = new LinkedList<>(c1.getLockTools());
//        AbstractContainer c2 = new TestingContainer("2");
//        List<Tool> tools2 = new LinkedList<>(c2.getLockTools());
//        AbstractContainer c3 = new TestingContainer("3");
//        List<Tool> tools3 = new LinkedList<>(c3.getLockTools());
//
//        c1.addDependency(c2);
//        c2.addDependency(c3);
//
//        assertTrue(c1.dependsOn(tools2.get(0)));
//        assertTrue(c1.dependsOn(tools3.get(0)));
//        assertTrue(c1.dependsOn(c2));
//        assertTrue(c1.dependsOn(c3));
//
//        assertFalse(c2.dependsOn(tools1.get(0)));
//        assertFalse(c2.dependsOn(c1));
//        assertTrue(c2.dependsOn(tools3.get(0)));
//        assertTrue(c2.dependsOn(c3));
//
//        assertFalse(c3.dependsOn(tools1.get(0)));
//        assertFalse(c3.dependsOn(c1));
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testDependsOnCircular() throws Exception {
//        AbstractContainer c1 = new TestingContainer("1");
//        AbstractContainer c2 = new TestingContainer("2");
//        AbstractContainer c3 = new TestingContainer("3");
//
//        c2.addDependency(c3);
//        c1.addDependency(c2);
//        c3.addDependency(c1);
//    }
//}