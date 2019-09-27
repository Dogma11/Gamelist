package com.example.gamelist.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GamePlatform implements Serializable {
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

    public static Map<String, String> shortPlatform = new HashMap<String, String>() {
        {
            put("Playstation", "PS1");
            put("PlayStation 2", "PS2");
            put("PlayStation 3", "PS3");
            put("PlayStation 4", "PS4");
            put("PlayStation 5", "PS5");
            put("Super Nintendo Entertainment System (SNES)", "SNES");
            put("Xbox 360", "X360");
            put("Nintendo 64", "N64");
            put("PlayStation Vita", "PSVita");
            put("New Nintendo 3DS", "New 3DS");
            put("Nintendo 3DS", "3DS");
            put("Nintendo Switch", "Switch");
            put("Call-A-Computer time-shared mainframe computer system", "PC");
            put("Nintendo Entertainment System (NES)", "NES");
            put("Nintendo GameCube", "GameCube");
            put("Nintendo DS", "DS");
            put("PC (Microsoft Windows)", "PC");
        }
    };

    public String getSanitazedName () {
        if ( shortPlatform.get(this.getName()) != null ){
            return shortPlatform.get(this.getName());
        }else {
            return this.getName();
        }
    }
}
