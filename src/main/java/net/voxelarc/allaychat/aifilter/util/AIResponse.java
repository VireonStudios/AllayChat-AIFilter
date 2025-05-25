package net.voxelarc.allaychat.aifilter.util;

import java.util.List;

public class AIResponse {

    public List<Detection> detections;

    public static class Detection {
        public String playerName;
        public float point;
        public Category category;
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
