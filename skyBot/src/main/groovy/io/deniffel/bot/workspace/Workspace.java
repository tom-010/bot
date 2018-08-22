package io.deniffel.bot.workspace;

import java.util.HashMap;
import java.util.Map;

public class Workspace {

    Map<String, Room> rooms = new HashMap<>();

    public static Workspace build() {
        return new Workspace();
    }

    public Room enterRoom(String roomId) {
        if(!rooms.containsKey(roomId))
            rooms.put(roomId, new Room(roomId));
        return rooms.get(roomId);
    }
}
