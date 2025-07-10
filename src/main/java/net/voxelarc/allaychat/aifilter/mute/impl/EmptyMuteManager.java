package net.voxelarc.allaychat.aifilter.mute.impl;

import net.voxelarc.allaychat.aifilter.mute.MuteManager;

public class EmptyMuteManager implements MuteManager {
    @Override
    public boolean isBannedOrMuted(String playerName) {
        return false;
    }
}
