package net.voxelarc.allaychat.aifilter.util;

import com.google.gson.Gson;
import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.StructuredChatCompletion;
import com.openai.models.chat.completions.StructuredChatCompletionCreateParams;
import net.voxelarc.allaychat.aifilter.AIFilterModule;
import net.voxelarc.allaychat.aifilter.api.event.DetectionEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class AIUtils {

    private static final Gson GSON = new Gson();

    public static void sendMessages(OpenAIClient client, AIFilterModule module) {
        System.out.println("Sending messages to AI for processing...");
        String json = GSON.toJson(new ArrayList<>(module.getMessagesToSend()));

        module.getMessagesToSend().clear();

        StructuredChatCompletionCreateParams<AIResponse> params = ChatCompletionCreateParams.builder()
                .model(module.getConfig().getString("ai.model"))
                .responseFormat(AIResponse.class)
                .addSystemMessage(module.getConfig().getString("prompt"))
                .addUserMessage(json)
                .build();

        StructuredChatCompletion.Choice<AIResponse> choice = client.chat().completions().create(params).choices().getFirst();
        AIResponse response = choice.message().content().orElse(null);
        if (response == null) return;

        System.out.println("Received AI response: " + response);

        List<String> actions = new ArrayList<>();
        for (AIResponse.Detection detection : response.detections) {
            String playerName = detection.playerName;
            float point = detection.point;
            AIResponse.Category category = detection.category;
            String message = detection.message;

            if (playerName == null || point < 0 || category == null || message == null) {
                System.out.println("Invalid detection data: " + detection);
                continue;
            }

            if (module.getConfig().getConfigurationSection("punishments." + category.name()) == null) continue;

            double threshold = module.getConfig().getDouble("punishments." + category.name() + ".threshold");
            if (point >= threshold) {
                System.out.println("Processing detection for player: " + playerName + ", category: " + category + ", point: " + point);

                String name = module.getConfig().getString("punishments." + category.name() + ".name", category.name());
                List<String> commands = module.getConfig().getStringList("punishments." + category.name() + ".commands");

                DetectionEvent event = new DetectionEvent(playerName, detection);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (event.isCancelled()) continue;

                for (String command : commands) {
                    actions.add(command.replace("<player>", playerName)
                            .replace("<point>", String.valueOf(point))
                            .replace("<category>", name)
                            .replace("<message>", message)
                    );
                }
            }
        }

        if (actions.isEmpty()) return;

        Bukkit.getScheduler().runTask(module.getPlugin(), () -> {
            for (String command : actions) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        });
    }

}
