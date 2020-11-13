package net.artux;

import java.io.*;
import net.artux.models.LSA;
import net.artux.utils.LSAUtil;
import net.artux.utils.Reader;


import java.util.Scanner;

public class Main {



    public void Menu()
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

    public static void main(String[] args)throws Exception{
        Reader reader = new Reader() {
            @Override
            public byte next() {
                Scanner scanner = new Scanner(System.in);
                return scanner.nextByte();
            }
        };

        LSAUtil.compileLSA(new LSA("Yn 1< X1 >1 Y1 2< Y2 X2 >2 Y4 X3 >3 Y3 3< Yk"), reader);
    }
}
