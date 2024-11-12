package com.managers;
import java.util.*;

import com.models.Ability;
import com.models.Character;
public class CharacterManager {
    private static CharacterManager instance;
    private Map<Integer, Character> characters;

    private CharacterManager() {
        characters = new HashMap<>();
    }

    public static CharacterManager getInstance() {
        if (instance == null) {
            instance = new CharacterManager();
        }
        return instance;
    }
    public Character getCharacter(int characterId) {
        return characters.get(characterId);
    }
}