package com.iit.masi.repository;

import com.google.gson.*;
import javafx.scene.paint.Color;
import java.lang.reflect.Type;

/**
 * Adaptateur pour sérialiser/désérialiser les couleurs JavaFX avec Gson
 */
public class ColorTypeAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("red", color.getRed());
        jsonObject.addProperty("green", color.getGreen());
        jsonObject.addProperty("blue", color.getBlue());
        jsonObject.addProperty("opacity", color.getOpacity());
        return jsonObject;
    }

    @Override
    public Color deserialize(JsonElement json, Type type, JsonDeserializationContext context) 
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double red = jsonObject.get("red").getAsDouble();
        double green = jsonObject.get("green").getAsDouble();
        double blue = jsonObject.get("blue").getAsDouble();
        double opacity = jsonObject.get("opacity").getAsDouble();
        return new Color(red, green, blue, opacity);
    }
}
