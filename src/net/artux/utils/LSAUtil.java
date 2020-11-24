package net.artux.utils;

import net.artux.exceptions.StackException;
import net.artux.models.LSA;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class LSAUtil {

    static String[] commands;

    public static void compileLSA(LSA lsa, Reader reader) {
        String v = lsa.getValue();
        commands = v.split(" ");
        stackCount = 0;
        goTo(null, reader);
        System.out.println();
    }

    static int stackCount;
    static String lastIndex;

    private static void goTo(String index, Reader reader) {
        int i = 0;
        if (index != null) {
            checkStack(index);
            for (int j = 0; j < commands.length; j++)
                if (commands[j].contains("<"))
                    if (commands[j].substring(0, commands[j].length()-2).equals(index))
                        i = j;

        }
        for (; i < commands.length; i++)
            if (commands[i].toLowerCase().contains("x")) {
                if (!reader.isOver()) {
                    if (reader.next(commands[i]) == 0 && commands[i + 1].contains(">")) {
                        goTo(commands[i + 1].substring(1, commands[i+1].length()-1), reader);
                        break;
                    }
                }else break;
            } else if (commands[i].toLowerCase().contains("w")) {
                if (commands[i + 1].contains(">")) {
                    goTo(commands[i + 1].substring(1, commands[i+1].length()-1), reader);
                    break;
                }
            } else if(!commands[i].contains(">") && !commands[i].contains("<"))
                System.out.print(commands[i]);
    }

    private static void checkStack(String index){
        if (lastIndex!=null && lastIndex.equals(index))
            stackCount++;
        else {
            lastIndex = index;
            stackCount = 0;
        }
        if (stackCount>10)
            throw new StackException();
    }

    public static ArrayList<LSA> readFromFile() throws Exception {
        FileReader fr = new FileReader("lsa.lcfg");
        Scanner scan = new Scanner(fr);
        ArrayList<LSA> lsa = new ArrayList<>();
        while (scan.hasNextLine()) {
            lsa.add(new LSA(scan.nextLine().trim()));
        }
        fr.close();
        return lsa;
    }
    
    public static LSA read()
    {
        System.out.print("Вводимое ЛСА: ".replaceAll("[\\s]{2,}", " "));
        Scanner scanner = new Scanner(System.in);
        return new LSA(scanner.nextLine());
    }
}
