package net.artux.utils;

import net.artux.models.LSA;

public class LSAUtil {

    static String[] commands;

    public static void compileLSA(LSA lsa, Reader reader) {
        String v = lsa.getValue();
        commands = v.split(" ");
        goTo('n', reader);
    }

    private static void goTo(char index, Reader reader) {
        int i = 0;
        if (index != 'n')
            for (int j = 0; j < commands.length; j++)
                if (commands[j].contains("<"))
                    if (index == commands[j].charAt(0))
                        i = j;

            for (; i < commands.length; i++)
                if (commands[i].toLowerCase().contains("x")) {
                    if (reader.next() == 0)
                        if (commands[i + 1].contains(">")) {
                            goTo(commands[i + 1].charAt(1), reader);
                            break;
                        }
                } else if (commands[i].toLowerCase().contains("w")) {
                    if (commands[i + 1].contains(">")) {
                        goTo(commands[i + 1].charAt(1), reader);
                        break;
                    }
                } else if(!commands[i].contains(">") && !commands[i].contains("<"))
                    System.out.print(commands[i]);

    }

}
