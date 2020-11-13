package net.artux;

import net.artux.models.LSA;
import net.artux.utils.LSAUtil;
import net.artux.utils.Reader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Reader reader = new Reader() {
            @Override
            public byte next() {
                Scanner scanner = new Scanner(System.in);
                return scanner.nextByte();
            }
        };

//        LSAUtil.compileLSA(new LSA("Yn 1< X1 >1 Y1 2< Y2 X2 >2 Y4 X3 >3 Y3 3< Yk"), reader);
        LSAUtil.readFromFile();
    }
}
