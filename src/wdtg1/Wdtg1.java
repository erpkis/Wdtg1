package wdtg1;                                                                  //link do opisu gry: https://www.boardgamegeek.com/boardgame/28931/achi

import java.util.Scanner;

public class Wdtg1 {

    static int opcja;

    public static void main(String[] args) {
        System.out.println("ACHI");
        boolean zakoncz = false;
        do {
            System.out.println();
            System.out.println("Ktora wersja gry Achi?:");
            System.out.println("3 - wersja na 3 pionki ('Tapatan')");
            System.out.println("4 - wersja na 4 pionki ('Klasyczne Achi')");
            //System.out.println();
            System.out.println("33 - wersja na 3 pionki - MonteCarloSearch");
            System.out.println("44 - wersja na 4 pionki - MonteCarloSearch");
            System.out.println("(0 - zakoncz program)");
            Funkcje.wybierzOpcje();
            switch (opcja) {
                case 3:
                    Wdtg1.silnik1(3);
                    break;
                case 4:
                    Wdtg1.silnik1(4);
                    break;
                case 33:
                    Wdtg1.silnik2(3);
                    break;
                case 44:
                    Wdtg1.silnik2(4);
                case 0:
                    System.out.println();
                    System.out.println("Do widzenia");
                    zakoncz = true;
                    break;
                default:
                    System.out.println("Wprowadzono bledna dana, sprobuj ponownie");
                    break;
            }
        } while (!zakoncz);
        System.out.println();
    }

    static void silnik1(int opcja) {
        Funkcje.ilePartii();
        Funkcje.czyPokazacPrzebiegPartii();
        System.out.println();
        System.out.println(Zlozonosc.ZlozonoscMetoda1(opcja, Funkcje.czyPokazac));
        System.out.println(Zlozonosc.ZlozonoscMetoda2(opcja));
        System.out.println();
        MiniMax_VS_Random.graj(Funkcje.ilosc, opcja, Funkcje.czyPokazac, false);
        MiniMax_VS_Random.graj(Funkcje.ilosc, opcja, false, true);
        System.out.println();
    }

    static void silnik2(int opcja) {
        System.out.println("Prosze uzupelnic plansze pionkami graczy 1 i 2");
        System.out.println("Prosze podac ciag skladajacy sie z zer, jedynek\n"
                + "lub dwojek oddzielonych spacjami, ktore reprezentowac beda\n"
                + "polozenie pionkow na planszy - zostana one wczytane na plansze 3x3\n "
                + "uzupelniajac rzad od lewej do prawej a nastepnie przechodzac kolumne nizej");
        Scanner sc = new Scanner(System.in);
        String ustawienieNaPlanszy = sc.nextLine();
        int tab[][] = MonteCarloSearch.uzupelnianiePlanszy(ustawienieNaPlanszy);
        System.out.println("Wprowadzona plansza:");
        Funkcje.pokazPlansze(tab, true);
        System.out.println("Ktory gracz wykonuje Ruch? (1 lub 2):");
        int gracz = sc.nextInt();
        MonteCarloSearch.MonteCarlo(tab, 500, gracz, opcja);
        System.out.println("Najlepszy ruch wg MonteCarloSearch: ");
        Funkcje.pokazPlansze(tab, true);
    }
}
