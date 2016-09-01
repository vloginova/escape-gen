//package escapegen.context;
//
//import escapegen.model.Item;
//import escapegen.model.ItemDescription;
//import lombok.Getter;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author - Vita Loginova
// */
//public class Context {
//    private static Context ourInstance = new Context();
//
//    @Getter
//    Map<Class<?>, ItemDescription<?, ? extends Item>> descriptionMap;
//    List<Item> goals;
//    List<Item> mainItems;
//
//    public static Context getInstance() {
//        return ourInstance;
//    }
//
//    private Context() {
//    }
//}
