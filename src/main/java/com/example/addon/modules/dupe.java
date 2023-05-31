package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;

import java.util.List;

public class dupe extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay between specified messages in ticks.")
        .defaultValue(20)
        .min(0)
        .sliderMax(200)
        .build()
    );

    public dupe() {
        super(Addon.CATEGORY, "dupe", "dupe.");
    }

    static int TickCounter;
    @Override
    public void onActivate() {
        TickCounter = 0;
    }
    @EventHandler
    private void onTick(TickEvent.Post event) {
        TickCounter+=1;
        ChatUtils.sendPlayerMsg(String.valueOf(TickCounter));
        ChatUtils.sendPlayerMsg(String.valueOf(TickCounter));
    }

}
