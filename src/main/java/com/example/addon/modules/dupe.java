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
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.Text;

import java.util.List;

public class dupe extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay before disconnect. 20 Ticks = 1 sec.")
        .defaultValue(300)
        .min(0)
        .sliderMax(1200)
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
        if (a == true) {TickCounter += 1;};
        if (TickCounter % 20 == 0 && TickCounter % 20 != 0){ info(String.valueOf(TickCounter) + "ticks have passed");}
        //if (TickCounter % 20 ==0){ChatUtils.sendPlayerMsg("Tick!"); ChatUtils.sendPlayerMsg(String.valueOf(a));}
        //if (a == true && TickCounter % 20 == 0){ChatUtils.sendPlayerMsg(String.valueOf(TickCounter)); ChatUtils.sendPlayerMsg(String.valueOf(delay));}
        //meteor client
        //mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] A non-trusted player appeared in your render distance.")));
        if (a == true && TickCounter  == delay.get()){ChatUtils.sendPlayerMsg(String.valueOf(TickCounter)); mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("dupe."))); a = false;}
        if (TickCounter  >= delay.get()){ChatUtils.sendPlayerMsg(String.valueOf(TickCounter)); mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("ERROR!"))); a = false;}
    }
    @EventHandler
    private void onDamage(DamageEvent event) {
        //if (event.entity.getUuid() == null) return;
        //if (!event.entity.getUuid().equals(mc.player.getUuid())) return;

        a = true;
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        a = false;
        toggle();
    }
}
