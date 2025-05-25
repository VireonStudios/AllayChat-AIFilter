package net.voxelarc.allaychat.aifilter;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import lombok.Getter;
import net.voxelarc.allaychat.aifilter.filter.AIFilter;
import net.voxelarc.allaychat.aifilter.util.AIUtils;
import net.voxelarc.allaychat.api.module.Module;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
public final class AIFilterModule extends Module {

    public static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private OpenAIClient aiClient;

    private final List<PlayerMessage> messagesToSend = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        aiClient = OpenAIOkHttpClient.builder()
                .baseUrl(getConfig().getString("ai.base-url"))
                .apiKey(getConfig().getString("ai.api-key"))
                .build();

        int seconds = getConfig().getInt("ai.send-every");
        if (seconds < 30) {
            getLogger().warning("AI send delay is set to " + seconds + " seconds, which is too low. Setting it to 30 seconds instead.");
            seconds = 30;
        }

        long delay = seconds * 20L;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (messagesToSend.isEmpty()) return;

                EXECUTOR.execute(() -> AIUtils.sendMessages(aiClient, AIFilterModule.this));
            }
        }.runTaskTimer(getPlugin(), delay, delay);

        this.getPlugin().addFilter(new AIFilter(this));
    }

    @Override
    public void onDisable() {
        aiClient.close();
    }

}
