package com.example.addon.modules;

import com.example.addon.Addon;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.events.entity.DamageEvent;
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

    static int TickCounter; boolean a;

    @Override
    public void onActivate() {
        TickCounter = 0;

    }
    @EventHandler
    private void onTick(TickEvent.Post event) {
        TickCounter+=1;
        if (TickCounter % 20 ==0){ChatUtils.sendPlayerMsg("Tick!"); ChatUtils.sendPlayerMsg(String.valueOf(a));}
        if (a == true && TickCounter % 20 == 0){ChatUtils.sendPlayerMsg(String.valueOf(TickCounter)); ChatUtils.sendPlayerMsg(String.valueOf(delay));}
        //meteor client
        //mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] A non-trusted player appeared in your render distance.")));
    }
    @EventHandler
    private void onDamage(DamageEvent event) {
        if (event.entity.getUuid() == null) return;
        if (!event.entity.getUuid().equals(mc.player.getUuid())) return;

        a = true;
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        toggle();
    }
}
