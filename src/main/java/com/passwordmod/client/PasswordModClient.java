package com.passwordmod.client;

import com.passwordmod.client.screen.CreatePasswordScreen;
import com.passwordmod.client.screen.PasswordScreen;
import com.passwordmod.storage.PasswordStorage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class PasswordModClient implements ClientModInitializer {
    public static boolean authenticated = false;
    public static String currentWorld = "";

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && !authenticated && client.currentScreen == null) {
                String worldId = getWorldId(client);
                if (worldId != null && !worldId.isEmpty()) {
                    currentWorld = worldId;
                    if (PasswordStorage.hasPassword(worldId)) {
                        client.execute(() -> client.setScreen(new PasswordScreen()));
                    } else {
                        client.execute(() -> client.setScreen(new CreatePasswordScreen()));
                    }
                }
            }
        });
    }

    private String getWorldId(net.minecraft.client.MinecraftClient client) {
        if (client.getServer() != null) {
            try {
                return client.getServer().getSaveProperties().getLevelName();
            } catch (Exception e) {
                return "singleplayer_world";
            }
        }
        if (client.getCurrentServerEntry() != null) {
            return client.getCurrentServerEntry().address;
        }
        return null;
    }
}