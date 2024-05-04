package cz.DBProjekt;

import com.google.gson.*;

import java.lang.reflect.Type;

public class KnihaDeserializer implements JsonDeserializer<Kniha> {
    @Override
    public Kniha deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        try {
            jsonObject.get("zanr").getAsString();
            return jsonDeserializationContext.deserialize(jsonElement, Romany.class);
        }
        catch (NullPointerException e) {
            return jsonDeserializationContext.deserialize(jsonElement, Ucebnice.class);
        }
    }
}
