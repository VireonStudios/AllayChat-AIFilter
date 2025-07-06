package net.voxelarc.allaychat.aifilter.util;

import java.util.List;

public class AIResponse {

    public List<Detection> detections;

    public static class Detection {
        public String playerName;
        public float point;
        public Category category;
        public String message;
    }

    public enum Category {
        VIOLENT,
        SELF_HARM,
        HARASSMENT,
        HATE,
        SEXUAL,
        OTHER
    }

}
