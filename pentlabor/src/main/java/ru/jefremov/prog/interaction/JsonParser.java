package ru.jefremov.prog.interaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.jefremov.prog.models.DoubleAdapter;
import ru.jefremov.prog.models.LocalDateAdapter;
import ru.jefremov.prog.models.Ticket;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;

public class JsonParser {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(java.time.LocalDate.class, new LocalDateAdapter())
            .registerTypeAdapter(Double.class, new DoubleAdapter())
            .setPrettyPrinting()
            .serializeNulls()
            .create();
    private final Type type = new TypeToken<LinkedHashSet<Ticket>>() {}.getType();
    public LinkedHashSet<Ticket> parseText(String text){
        return gson.fromJson(text, type);
    }

    public String encodeCollection(LinkedHashSet<Ticket> collection) {
        return gson.toJson(collection, type);
    }
}
