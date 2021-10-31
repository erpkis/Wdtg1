package wdtg1;

import java.util.Scanner;

public class Wdtg1 {

    public static void main(String[] args) {
        System.out.println("ACHI");
        Scanner sc = new Scanner(System.in);
        int opcja;
        boolean zakoncz = false;
        do {
            System.out.println("Ktora wersja gry Achi?:");
            System.out.println("3 - wersja na 3 pionki ('Tapatan')");
            System.out.println("4 - wersja na 4 pionki");
            System.out.println("(0 - zakoncz program)");
            if (sc.hasNextInt()) {
                opcja = sc.nextInt();
            } else {
                System.out.println("Wprowadzono bledna dana, koncze program");
                break;
            }
            switch (opcja) {
                case 3:
                    Funkcje.ilePartii();
                    System.out.println();
                    System.out.println(Zlozonosc.ZlozonoscMetoda1(3, false));
                    System.out.println(Zlozonosc.ZlozonoscMetoda2(3));
                    System.out.println();
                    System.out.println(MiniMax.graj(1,3, true));                //testowe
                    break;
                case 4:
                    Funkcje.ilePartii();
                    System.out.println();
                    System.out.println(Zlozonosc.ZlozonoscMetoda1(4, false));
                    System.out.println(Zlozonosc.ZlozonoscMetoda2(4));
                    System.out.println();
                    System.out.println(MiniMax.graj(1,4, true));                //testowe
                    break;
                case 0:
                    System.out.println();
                    System.out.println("Do widzenia");
                    zakoncz = true;
                    break;
                default:
                    System.out.println();
                    System.out.println("Wprowadzono bledna dana, sprobuj ponownie");
                    System.out.println();
                    break;
            }
        } while (!zakoncz);
        System.out.println();
    }
}
