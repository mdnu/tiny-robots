package numd.coffeehouse;

/**
 * Created by m on 06/01/2016.
 */
public class Drink {
    private String name;
    private String description;
    private int imageResourceId;

    // 'drinks' is an array of Drinks
    public static final Drink[] drinks = {
            new Drink("Mocaccino", "Chocolate variant of Caffe Latte", R.drawable.mocaccino),
            new Drink("Ristretto", "A short shot of Espresso", R.drawable.ristretto),
            new Drink("Latte", "Espresso served with milk", R.drawable.latte),
    };

    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String toString() {
        return this.name;
    }
}
