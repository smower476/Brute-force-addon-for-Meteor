package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;

public class Bruteforce extends Module {
    public Bruteforce() {
        super(Addon.CATEGORY, "Brute-force", "Brute-force.");
    }
    @Override
    public void onActivate() {
        ChatUtils.sendPlayerMsg("I am a very bad person!");
    }
}
