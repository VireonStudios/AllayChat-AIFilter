package net.voxelarc.allaychat.aifilter.listener;

import lombok.RequiredArgsConstructor;
import net.voxelarc.allaychat.aifilter.AIFilterModule;
import net.voxelarc.allaychat.aifilter.api.event.DetectionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@RequiredArgsConstructor
public class DetectionListener implements Listener {

    private final AIFilterModule module;

    @EventHandler
    public void onDetect(DetectionEvent event) {
        module.getLogger().info("Detected " + event.getDetection().category.name().toLowerCase()
                + " for player " + event.getPlayerName()
                + ": " + event.getDetection().message + "(" + event.getDetection().point + ")");
    }

}
