package com.passwordmod.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class PasswordStorage {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("passwordmod").resolve("passwords.json");
    private static final Map<String, WorldData> CACHE = new HashMap<>();

    static {
        load();
    }

    private static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH, StandardCharsets.UTF_8);
                Map<String, WorldData> data = GSON.fromJson(json, new TypeToken<Map<String, WorldData>>(){}.getType());
                if (data != null) CACHE.putAll(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(CACHE), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPassword(String worldId) {
        return CACHE.containsKey(worldId) && CACHE.get(worldId).passwordHash != null;
    }

    public static String setPassword(String worldId, String password) {
        String hash = sha256(password);
        String backup = generateBackupCode();
        WorldData data = new WorldData();
        data.passwordHash = hash;
        data.backupCode = backup;
        CACHE.put(worldId, data);
        save();
        return backup;
    }

    public static boolean verifyPassword(String worldId, String password) {
        WorldData data = CACHE.get(worldId);
        if (data == null || data.passwordHash == null) return false;
        return data.passwordHash.equals(sha256(password));
    }

    public static boolean verifyBackupCode(String worldId, String code) {
        WorldData data = CACHE.get(worldId);
        if (data == null || data.backupCode == null) return false;
        return data.backupCode.equalsIgnoreCase(code);
    }

    public static void removePassword(String worldId) {
        CACHE.remove(worldId);
        save();
    }

    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateBackupCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private static class WorldData {
        String passwordHash;
        String backupCode;
    }
}