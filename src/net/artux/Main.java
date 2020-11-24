package net.artux;

import net.artux.models.LSA;
import net.artux.utils.LSAUtil;
import net.artux.utils.Reader;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while(!exit) {
            System.out.println("---------------МЕНЮ---------------");
            System.out.println("1: Ввести ЛСА с клавиатуры");
            System.out.println("2: Считать ЛСА из текстового файла");
            System.out.println("3: Выход");
            switch (scanner.nextInt()) {
                case 1:
                    startLSA(LSAUtil.read());
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
                    exit = true;
                    break;
                default:
                    System.out.println("Неверное значение");
                    break;
            }
        }
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
                    public byte next(String x) {
                        System.out.println();
                        byte b = 0;
                        boolean validInput = false;
                        while (!validInput) {
                            try {
                                System.out.print("Запрос " + x + ":");
                                Scanner scanner = new Scanner(System.in);
                                b = scanner.nextByte();
                                validInput = true;
                            }
                            catch (Exception e) {
                                System.out.println("Некорректный ввод.");
                            }
                        }
                        return b;
                    }

                    @Override
                    public boolean isOver() {
                        return false;
                    }
                };
                break;
            case 2:
                reader = new Reader() {
                    byte[] bytes = getInput();
                    int i = -1;
                    @Override
                    public byte next(String x) {
                        i++;
                        return bytes[i];
                    }

                    @Override
                    public boolean isOver() {
                        return i + 1 >= bytes.length;
                    }
                };
                break;
            case 3:
                System.out.println("Какая глубина перебора?");
                startBrute(lsa, scanner.nextInt());
                break;
            default:
                System.out.println("Некорректный ввод");
                startLSA(lsa);
        }
        if (reader != null)
            LSAUtil.compileLSA(lsa, reader);
    }

    public static byte[] getInput() {
        byte[] b = {};
        boolean validInput = false;
        while (!validInput) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Введите все занчения без пробелов:");
                b = getBytes(scanner.nextLine());
                validInput = true;
            } catch (Exception e) {
                System.out.println("Некорректный ввод.");
            }
        }
        return b;
    }

    public static void startBrute(LSA lsa, int depth){
        start(lsa, "", depth);
    }

    public static void start(LSA lsa, String input, int depth){
        if (!input.equals("")){
            Reader reader = new Reader() {
                byte[] bytes = getBytes(input);
                int i = -1;
                @Override
                public byte next(String x) {
                    i++;
                    return bytes[i];
                }
                @Override
                public boolean isOver() {
                    return i + 1 >= bytes.length; }
            };
            System.out.print(input + " : ");
            LSAUtil.compileLSA(lsa, reader);
        }

        if (input.length()<depth) {
            start(lsa,input + "0", depth);
            start(lsa,input + "1", depth);
        }
    }

    public static byte[] getBytes(String input) {
        byte[] bytes = new byte[input.length()];
        for (int i = 0; i < input.length(); i++) {
            bytes[i] = Byte.parseByte(String.valueOf(input.charAt(i)));
        }
        return bytes;
    }

}
