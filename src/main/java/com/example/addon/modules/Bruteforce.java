package com.example.addon.modules;
import java.util.Arrays;
import com.example.addon.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;

import meteordevelopment.meteorclient.settings.*;


import java.util.List;

public class Bruteforce extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<String>> messages = sgGeneral.add(new StringListSetting.Builder()
        .name("messages")
        .description("Messages to use for spam.")
        .defaultValue(List.of("Meteor on Crack!"))
        .build()
    );

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("The delay between specified messages in ticks.")
        .defaultValue(20)
        .min(0)
        .sliderMax(200)
        .build()
    );

    private final Setting<Boolean> disableOnLeave = sgGeneral.add(new BoolSetting.Builder()
        .name("disable-on-leave")
        .description("Disables spam when you leave a server.")
        .defaultValue(true)
        .build()
    );


    private final Setting<Boolean> disableOnDisconnect = sgGeneral.add(new BoolSetting.Builder()
        .name("disable-on-disconnect")
        .description("Disables spam when you are disconnected from a server.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> random = sgGeneral.add(new BoolSetting.Builder()
        .name("randomise")
        .description("Selects a random message from your spam message list.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> bypass = sgGeneral.add(new BoolSetting.Builder()
        .name("bypass")
        .description("Add random text at the end of the message to try to bypass anti spams.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Integer> length = sgGeneral.add(new IntSetting.Builder()
        .name("length")
        .description("Number of characters used to bypass anti spam.")
        .visible(bypass::get)
        .defaultValue(16)
        .sliderRange(1, 256)
        .build()
    );

    private int messageI, timer;

    public Bruteforce() {
        super(Addon.CATEGORY, "Brute-force", "Brute-force.");
    }

    @Override
    public void onActivate() {
        //ChatUtils.sendPlayerMsg("I am a very bad person!");
        //for (int i=0; i<20; i++){ChatUtils.sendPlayerMsg("a"+i+"b");}
        String line = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789!№@#";
        char[] chars;
        chars = new char[line.length()];
        for(int i=0; i<line.length();i++){
            chars[i] = line.charAt(i);
            //System.out.println(chars);
        }

        //char[] chars = {'a', 'b', 'c', 'd'};
        int maxLength = 3;

        printCombinations(chars, maxLength);
    }
    public static void printCombinations(char[] chars, int maxLength) {
        for (int length = 1; length <= maxLength; length++) {
            char[] combination = new char[length];
            generateCombinations(chars, combination, 0, length);
        }
    }

    public static void generateCombinations(char[] chars, char[] combination, int start, int length) {
        if (length == 0) {
            String str = new String(combination);
            ChatUtils.sendPlayerMsg(str);
            return;
        }

        for (int i = 0; i < chars.length; i++) {
            combination[combination.length - length] = chars[i];
            generateCombinations(chars, combination, i, length - 1);
        }
    }
}
