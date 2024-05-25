import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Box {
    private String from;
    private String material;
    private String color;
    private MaxLiftingCapacity maxLiftingCapacity;
    private Cargo cargo;
    private Date deliveryDate;

    public Box(String from, String material, String color, MaxLiftingCapacity maxLiftingCapacity, Cargo cargo, Date deliveryDate) {
        this.from = from;
        this.material = material;
        this.color = color;
        this.maxLiftingCapacity = maxLiftingCapacity;
        this.cargo = cargo;
        this.deliveryDate = deliveryDate;
    }

    // Геттеры и сеттеры

    private static class MaxLiftingCapacity {
        private String unit;
        private int value;

        // Геттеры и сеттеры
    }

    private static class Cargo {
        private String name;
        private String classType;

        // Геттеры и сеттеры
    }

    public static Box createBoxFromJson(String filePath) throws IOException, ParseException {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(new FileReader(filePath));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        String from = jsonObject.get("from").getAsString();
        String material = jsonObject.get("material").getAsString();
        String color = jsonObject.get("color").getAsString();

        JsonObject maxLiftingCapacityJson = jsonObject.getAsJsonObject("max-lifting-capacity");
        MaxLiftingCapacity maxLiftingCapacity = gson.fromJson(maxLiftingCapacityJson, MaxLiftingCapacity.class);

        JsonObject cargoJson = jsonObject.getAsJsonObject("cargo");
        Cargo cargo = gson.fromJson(cargoJson, Cargo.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date deliveryDate = sdf.parse(jsonObject.get("delivery-date").getAsString());

        return new Box(from, material, color, maxLiftingCapacity, cargo, deliveryDate);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = "src/main/resources/box.json"; // Укажите полный путь к файлу box.json
            Box box = Box.createBoxFromJson(filePath);
            System.out.println("Box created successfully!");
            // Теперь можно использовать объект box для дальнейших операций
        } catch (IOException | ParseException e) {
            System.err.println("Error creating Box: " + e.getMessage());
        }
    }
}
