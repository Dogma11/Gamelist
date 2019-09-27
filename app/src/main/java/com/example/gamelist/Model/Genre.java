package com.example.gamelist.Model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Genre implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public static Map<String, String> shortGenre = new HashMap<String, String>() {
        {
            put("Role-playing (RPG)", "RPG");
            put("Quiz/Trivia", "Quiz");
            put("Turn-based strategy (TBS)", "Turn-Based");
            put("Real Time Strategy (RTS)", "RTS");
            put("Hack and slash/Beat 'em up", "Hack 'Slash");
        }
    };

    public String getSanitazedName () {
        if ( shortGenre.get(this.getName()) != null ){
            return shortGenre.get(this.getName());
        }else {
            return this.getName();
        }
    }
}
