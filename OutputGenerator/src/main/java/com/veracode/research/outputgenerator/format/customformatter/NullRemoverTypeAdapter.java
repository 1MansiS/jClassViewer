package com.veracode.research.outputgenerator.format.customformatter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class NullRemoverTypeAdapter implements JsonSerializer<List<?>> {

    @Override
    public JsonElement serialize(List<?> objects, Type type, JsonSerializationContext jsonSerializationContext) {
        if (objects == null || objects.isEmpty() || objects.size() == 0) // exclusion is made here
            return null;

        JsonArray array = new JsonArray();

        for (Object child : objects) {
            JsonElement element = jsonSerializationContext.serialize(child);
            array.add(element);
        }

        return array;
    }
}

