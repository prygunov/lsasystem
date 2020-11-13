package net.artux;


import net.artux.models.LSA;
import net.artux.utils.LSAUtil;
import net.artux.utils.Reader;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void menu()
    {
        Scanner scanner = new Scanner(System.in);;
        boolean exit = false;
        while(!exit) {
            System.out.println("---------------МЕНЮ---------------");
            System.out.println("1: Ввести ЛСА с клавиатуры");
            System.out.println("2: Считать ЛСА из текстового файла");
            System.out.println("3: Вывести ЛСА");
            System.out.println("4: Выход");
            switch (scanner.nextInt()) {
                case 1:

                    break;
                case 2:
                    try {
                        List<LSA> list = LSAUtil.readFromFile();
                        System.out.println("Полученные LSA:");
                        for (int i = 0; i<list.size(); i++)
                            System.out.println((i + 1) + " : " + list.get(i).getValue());
                        System.out.println("Какую моделировать?");
                        startLSA(list.get(scanner.nextInt()-1));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:

                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Неверное значение");
                    break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        menu();
    }

    public static void startLSA(LSA lsa){
        System.out.println("Какой режим работы?");

        System.out.println("1. Последовательный ввод значений");
        System.out.println("2. Ввод значений всех логических условий");
        System.out.println("3. Полный перебор всех значений");
        Scanner scanner = new Scanner(System.in);
        Reader reader = null;
        switch (scanner.nextInt()){
            case 1:
                reader = new Reader() {
                    @Override
                    public byte next() {
                        Scanner scanner = new Scanner(System.in);
                        return scanner.nextByte();
                    }
                };
                break;
            case 2:
                reader = new Reader() {
                    byte[] bytes = getInput();
                    int i = -1;
                    @Override
                    public byte next() {
                        i++;
                        if (i < bytes.length)
                            return bytes[i];
                    }
                };
                break;
            case 3:

                break;
            default:
                System.out.println("Некорректный ввод");
                startLSA(lsa);
        }
        if (reader != null)
            LSAUtil.compileLSA(lsa, reader);
    }

    public static byte[] getInput() {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        byte[] bytes = new byte[input.length()];
        for (int i = 0; i < input.length(); i++) {
            bytes[i] = Byte.parseByte(String.valueOf(input.charAt(i)));
        }
        return bytes;
    }
}
