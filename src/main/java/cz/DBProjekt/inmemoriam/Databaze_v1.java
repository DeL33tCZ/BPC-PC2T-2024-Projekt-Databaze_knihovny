package cz.DBProjekt.inmemoriam;

import cz.DBProjekt.Kniha;
import cz.DBProjekt.Romany;
import cz.DBProjekt.Ucebnice;

import java.util.Scanner;

public class Databaze_v1 {
    public static int pouzeCelaCisla(Scanner sc)
    {
        int cislo = 0;
        try
        {
            cislo = sc.nextInt();
        }
        catch(Exception e)
        {
            System.out.println("zadejte prosim cele cislo ");
            sc.nextLine();
            cislo = pouzeCelaCisla(sc);
        }
        return cislo;
    }

    public static void main(String[] args) {
        Kniha[] databaze = new Kniha[1];
        int velikost;
        int posledni = 0;
        Scanner sc = new Scanner(System.in);
        int moznost;
        String nazev;
        String autor;
        int rok_vydani;
        String zanr;
        int idx;
        int pro_rocnik;
        boolean run = true;
        boolean subrun;
        while(run) {
            subrun = true;
            idx = -1;
            velikost = databaze.length;
            System.out.println("Vyberte pozadovanou cinnost:");
            System.out.println(" 1 .. pridani nove knihy");
            System.out.println(" 2 .. uprava knihy");
            System.out.println(" 3 .. smazani knihy");
            System.out.println(" 4 .. zmena stavu dostupnosti knihy");
            System.out.println(" 5 .. vypis knih");
            System.out.println(" 6 .. vyhledani knihy");
            System.out.println(" 7 .. vypis knih autora");
            System.out.println(" 8 .. vypis knih stejneho zanru");
            System.out.println(" 9 .. vypis vypujcenych knih");
            System.out.println(" 10 .. ulozeni dat o knize");
            System.out.println(" 11 .. nacteni ulozenych dat o knize");
            System.out.println(" 12 .. ukonceni aplikace");
            System.out.print("VELIKOST: " + velikost + " OBSAH: "); //TESTIN
            for(int i = 0; i < velikost; i++) {                     //
                System.out.print(databaze[i] + " ");                //
            }                                                       //
            System.out.println();                                   //TESTIN
            moznost = pouzeCelaCisla(sc);
            switch(moznost) {
                case 1: //DONE
                    System.out.println("Vyberte typ knihy, kterou chcete pridat:");
                    System.out.println(" 1 .. roman");
                    System.out.println(" 2 .. ucebnice");
                    moznost = pouzeCelaCisla(sc);
                    System.out.println("Zadejte název knihy:");
                    nazev = sc.next();
                    System.out.println("Zadejte autora/y knihy:");
                    autor = sc.next();
                    System.out.println("Zadejte rok vydani knihy:");
                    rok_vydani = sc.nextInt();
                    switch(moznost) {
                        case 1:
                            System.out.println("Zadejte zanr romanu:");
                            zanr = sc.next();
                            if(databaze[0] == null) {
                                databaze[0] = new Romany(nazev, autor, rok_vydani, zanr);
                            }
                            else {
                                Kniha[] tmp = new Kniha[velikost+1];
                                for(int i = 0; i < velikost; i++) {
                                    tmp[i] = databaze[i];
                                }
                                posledni++;
                                databaze = tmp;
                                databaze[posledni] = new Romany(nazev, autor, rok_vydani, zanr);
                            }
                            break;
                        case 2:
                            System.out.println("Zadejte pro jaky rocnik je ucebnice vhodna:");
                            pro_rocnik = sc.nextInt();
                            if(databaze[0] == null) {
                                databaze[0] = new Ucebnice(nazev, autor, rok_vydani, pro_rocnik);
                            }
                            else {
                                Kniha[] tmp = new Kniha[velikost+1];
                                for(int i = 0; i < velikost; i++) {
                                    tmp[i] = databaze[i];
                                }
                                posledni++;
                                databaze = tmp;
                                databaze[posledni] = new Ucebnice(nazev, autor, rok_vydani, pro_rocnik);
                            }
                            break;
                    }
                    break;
                case 2: //UNTESTED!!!
                    System.out.println("Zadejte nazev knihy, kterou chcete upravit:");
                    nazev = sc.next();
                    for(int i = 0; i < velikost; i++) {
                        if(nazev == databaze[i].getNazev()) {
                            idx = i;
                            break;
                        }
                    }
                    if(idx != -1) {
                        System.out.println("Kniha nalezena!");
                        while(subrun) {
                            System.out.println("Vyberte pozadovanou upravu knihy:");
                            System.out.println(" 1 .. zmena autora");
                            System.out.println(" 2 .. zmena roku vydani");
                            System.out.println(" 3 .. zmena stavu dostupnosti");
                            System.out.println(" 4 .. vyskocit z uprav knihy");
                            moznost = pouzeCelaCisla(sc);
                            switch(moznost) {
                                case 1:
                                    System.out.println("Zadejte noveho autora:");
                                    autor = sc.next();
                                    if(databaze[idx] instanceof Romany) {
                                        databaze[idx] = new Romany(databaze[idx].getNazev(), autor, databaze[idx].getRokVydani(), ((Romany) databaze[idx]).getZanr());
                                    }
                                    else {
                                        databaze[idx] = new Ucebnice(databaze[idx].getNazev(), autor, databaze[idx].getRokVydani(), ((Ucebnice) databaze[idx]).getProRocnik());
                                    }
                                    break;
                                case 2:
                                    System.out.println("Zadejte novy rok vydani:");
                                    rok_vydani = sc.nextInt();
                                    if(databaze[idx] instanceof Romany) {
                                        databaze[idx] = new Romany(databaze[idx].getNazev(), databaze[idx].getAutor(), rok_vydani, ((Romany) databaze[idx]).getZanr());
                                    }
                                    else {
                                        databaze[idx] = new Ucebnice(databaze[idx].getNazev(), databaze[idx].getAutor(), rok_vydani, ((Ucebnice) databaze[idx]).getProRocnik());
                                    }
                                    break;
                                case 3:
                                    databaze[idx].changeStavDostupnosti();
                                    break;
                                case 4:
                                    subrun = false;
                                    break;
                            }
                        }
                    }
                    else {
                        System.out.println("Kniha nenalezena");
                    }
                    break;
                case 3: //UNTESTED!!!
                    System.out.println("Zadejte nazev knihy, kterou chcete smazat:");
                    nazev = sc.next();
                    for(int i = 0; i < velikost; i++) {
                        if(nazev == databaze[i].getNazev()) {
                            idx = i;
                            break;
                        }
                    }
                    if(idx != -1) {
                        System.out.println("Kniha nalezena!");
                        Kniha[] tmp = databaze;
                        databaze = new Kniha[velikost--];
                        for(int i = 0, k = 0; i < velikost; i++) {
                            if(nazev != tmp[i].getNazev()) {
                                databaze[k] = tmp[i];
                                k++;
                            }
                        }
                        posledni--;
                    }
                    else {
                        System.out.println("Kniha nenalezena");
                    }
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    run = false;
                    break;
            }
        }
    }
}
