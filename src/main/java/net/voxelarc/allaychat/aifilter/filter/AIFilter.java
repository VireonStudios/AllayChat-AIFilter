package net.voxelarc.allaychat.aifilter.filter;

import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.aifilter.AIFilterModule;
import net.voxelarc.allaychat.aifilter.PlayerMessage;
import net.voxelarc.allaychat.api.filter.ChatFilter;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class AIFilter implements ChatFilter {

    private final AIFilterModule module;

    @Override
    public void onEnable() {

    }

    @Override
    public boolean checkMessage(Player player, String message) {
        if (player.hasPermission("allaychat.bypass.ai")) return false;

        module.getMessagesToSend().add(new PlayerMessage(player.getName(), message));
        return false;
    }

}
