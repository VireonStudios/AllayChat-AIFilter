package net.voxelarc.allaychat.aifilter.mute.impl;

import litebans.api.Database;
import net.voxelarc.allaychat.aifilter.mute.MuteManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class LiteBansMuteManager implements MuteManager {

    @Override
    public boolean isBannedOrMuted(String playerName) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        UUID uuid = offlinePlayer.getUniqueId();

        boolean isMuted = Database.get().isPlayerMuted(uuid, null);
        boolean isBanned = Database.get().isPlayerBanned(uuid, null);

        return  isMuted || isBanned;
    }
}
