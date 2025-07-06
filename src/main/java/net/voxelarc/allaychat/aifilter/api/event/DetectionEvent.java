package net.voxelarc.allaychat.aifilter.api.event;

import lombok.Getter;
import net.voxelarc.allaychat.aifilter.util.AIResponse;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DetectionEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    @Getter private final String playerName;
    @Getter private final AIResponse.Detection detection;

    public DetectionEvent(String playerName, AIResponse.Detection detection) {
        super(true);

        this.playerName = playerName;
        this.detection = detection;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
