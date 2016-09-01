package escapegen.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author - Vita Loginova
 */
@Builder
@Getter
public class ItemProperties {
    @NonNull
    private String id;

    private Size size = Size.Medium;
    private Matter matter = Matter.General;
    private Shape shape = Shape.General;


    public enum Size {
        Small, Medium, Large, RoomSize;

        private static boolean isCompatible(Size e1, Size e2) {
            return e1.ordinal() <= e2.ordinal();
        }
    }

    public enum Matter {
        Hard, Soft, General;

        private static boolean isCompatible(Matter e1, Matter e2) {
            return e1 == e2  || e1 == General;
        }
    }

    public enum Shape {
        Flat, Thin, General;
        private static boolean isCompatible(Shape e1, Shape e2) {
            return e1 == e2 || e2 == General;
        }

    }

    public static boolean isCompatible(Item innerItem, Item outerItem) {
        ItemProperties inner = innerItem.getItemProperties();
        ItemProperties outer = outerItem.getItemProperties();
        return Size.isCompatible(inner.size, outer.size) &&
                Matter.isCompatible(inner.matter, outer.matter) &&
                Shape.isCompatible(inner.shape, outer.shape);
    }

}
