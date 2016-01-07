package numd.coffeerec;

import java.util.ArrayList;
import java.util.List;

public class CoffeeRec {
    List<String> getRec(String flavor) {
        List<String> types = new ArrayList<String>();
        if (flavor.equals("Acidic - Washed Arabica (Above 4,000ft)")) {
            types.add("Acidy: Colombian Excelso");
            types.add("Nippy: Costa Rica SHB");
            types.add("Piquant: Kenya AA");
        } else if (flavor.equals("Mellow - Washed Arabica (Below 4,000ft)")) {
            types.add("Melow: Sumatra - Indonesia");
            types.add("Mild: Guatemala");
            types.add("Delicate: Papua New Guinea");
        } else if (flavor.equals("Winey - Unwashed Arabica (Above 4,000ft)")) {
            types.add("Winey: Ethiopia - Djimma");
            types.add("Tart: Kivu - Congo");
            types.add("Tangy: India Arabica");
        } else if (flavor.equals("Bland - Washed Arabica and Robusta (Below 2,000ft)")) {
            types.add("Bland: El Salvador - Low Grown Central");
            types.add("Neutral: Uganda Robusta");
            types.add("Soft: Santos - Brazil");
        } else if (flavor.equals("Sharp - Robusta (Below 2,000ft)")) {
            types.add("Robusta (Below 2,000')");
            types.add("Sharp: Ivory Coast");
            types.add("Astrigent: Indonesian");
            types.add("Rough: Angola");
        } else if (flavor.equals("Soury - Unwashed Brazils (Above 2,000ft)")) {
            types.add("Soury: Vitoria - Brazil");
            types.add("Hard: Minas - Brazil");
            types.add("Acrid: Rio - Brazil");
        }
        return types;
    }
}
