package sg.edu.nus.iss.miniproject.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classify {
    
    private String category;
    private double probability;


    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("category", getCategory())
                .add("probability", getProbability())
                .build();
    }
    
    public static Classify toClassify(JsonObject obj) {
        Classify classify = new Classify();
        classify.setCategory(obj.getString("category"));
        classify.setProbability(obj.getJsonNumber("probability").doubleValue());
        return classify;
    }
}

