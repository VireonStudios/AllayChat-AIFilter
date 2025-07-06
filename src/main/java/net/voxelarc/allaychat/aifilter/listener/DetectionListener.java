package net.voxelarc.allaychat.aifilter.listener;

import net.voxelarc.allaychat.aifilter.api.event.DetectionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DetectionListener implements Listener {

    @EventHandler
    public void onDetect(DetectionEvent event) {
        System.out.println(
            "Detected message from player " + event.getPlayerName() + ": " +
            event.getDetection().message + " with category " +
            event.getDetection().category + " and point " +
            event.getDetection().point
        );
    }

}
