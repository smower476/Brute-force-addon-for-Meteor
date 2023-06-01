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

public class dupe extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> TickDelay = sgGeneral.add(new IntSetting.Builder()
        .name("Delay.")
        .description("Delay before disconnect. 20 Ticks = 1 sec.")
        .defaultValue(300)
        .min(0)
        .sliderMax(1200)
        .build()
    );
    private final Setting<Integer> MsDelay = sgGeneral.add(new IntSetting.Builder()
        .name("Advanced delay.")
        .description("Delay after ticks. (ms)")
        .defaultValue(0)
        .min(0)
        .sliderMax(50)
        .build()
    );

    private final Setting<Boolean> spam = sgGeneral.add(new BoolSetting.Builder()
        .name("Spam.")
        .description("Spam message instead of disconnecting.")
        .defaultValue(false)
        .build()
    );
    private final Setting<String> message = sgGeneral.add(new StringSetting.Builder()
        .name("Message.")
        .description("Message to use for spam.")
        .defaultValue("/ping")
        .build()
    );

    public dupe() {
        super(Addon.CATEGORY, "Dupe", "Combat dupe.");
    }

    static int TickCounter; boolean flag;

    @Override
    public void onActivate() {
        TickCounter = 0;

    }
    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (flag) {TickCounter += 1;}

        if (TickCounter % 20 == 0 && TickCounter % 20 != 0){ info(TickCounter + "ticks have passed");}

        if (flag && TickCounter  == TickDelay.get() && !spam.get()){
            flag = false;
            try{
                Thread.sleep(MsDelay.get());
                mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("dupe.")));
            } catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
            return;



        }
        else if (flag && TickCounter  == TickDelay.get() && spam.get())
        {
            try{
                Thread.sleep(MsDelay.get());
            for (int i = 0; i < 25; i++) {
                ChatUtils.sendPlayerMsg(String.valueOf(message.get()));
            }
            flag = false;
            } catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
            toggle();
        }
        if (TickCounter   >= TickDelay.get() + 60){
            flag = false;
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("ERROR!")));
        }
    }
    @EventHandler
    private void onDamage(DamageEvent event) {
        flag = true;
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        flag = false;
        toggle();
    }
}
