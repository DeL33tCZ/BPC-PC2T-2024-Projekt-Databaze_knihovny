package cz.DBProjekt;

import cz.DBProjekt.db.DBConnection;

import java.util.Scanner;

public class Aplikace {
    public static int pouzeCelaCisla(Scanner sc)
    {
        int cislo;
        try
        {
            cislo = sc.nextInt();
        }
        catch(Exception e)
        {
            System.out.println("! Zadejte prosím celé číslo!");
            sc.nextLine();
            cislo = pouzeCelaCisla(sc);
        }
        return cislo;
    }

    public static void main(String[] args) {
        Databaze databaze = new Databaze();
        Scanner sc = new Scanner(System.in);
        int moznost;
        String nazev, autor, zanr;
        boolean run = true, subrun = true;
        databaze.loadDB();
        while(run) {
            System.out.println("====================================================================================");
            System.out.println("Vyberte požadovanou činnost:");
            System.out.println(" 1 .. přidání nové knihy");
            System.out.println(" 2 .. úprava knihy");
            System.out.println(" 3 .. smazání knihy");
            System.out.println(" 4 .. změna stavu dostupnosti knihy");
            System.out.println(" 5 .. výpis knih");
            System.out.println(" 6 .. vyhledání knihy");
            System.out.println(" 7 .. výpis knih autora");
            System.out.println(" 8 .. výpis knih stejného žánru");
            System.out.println(" 9 .. výpis vypůjčených knih");
            System.out.println(" 10 .. uloženi dat o knize");
            System.out.println(" 11 .. načtení uložených dat o knize");
            System.out.println(" 12 .. ukončení aplikace");
            System.out.print("Vybraná možnost: ");
            moznost = pouzeCelaCisla(sc);
            System.out.println("====================================================================================");
            switch(moznost) {
                case 1:
                    sc.nextLine();
                    while(subrun) {
                        System.out.println("------------------------------------------------------------------------------------");
                        System.out.println("Vyberte typ knihy, kterou chcete přidat:");
                        System.out.println(" 1 .. román");
                        System.out.println(" 2 .. učebnice");
                        System.out.print("Vybraná možnost: ");
                        moznost = pouzeCelaCisla(sc);
                        System.out.println("------------------------------------------------------------------------------------");
                        if(moznost == 1 || moznost == 2) {
                            subrun = false;
                        }
                        else {
                            System.out.println("! Zadali jste špatné označení možnosti!");
                        }
                    }
                    sc.nextLine();
                    databaze.setKniha(moznost);
                    break;
                case 2:
                    sc.nextLine();
                    System.out.println("Zadejte název knihy, kterou chcete upravit:");
                    nazev = sc.nextLine();
                    databaze.changeKniha(nazev);
                    break;
                case 3:
                    sc.nextLine();
                    System.out.println("Zadejte název knihy, kterou chcete smazat:");
                    nazev = sc.nextLine();
                   databaze.deleteKniha(nazev);
                    break;
                case 4:
                    sc.nextLine();
                    System.out.println("Zadejte název knihy, u které chcete změnit stav dostupnosti:");
                    nazev = sc.nextLine();
                    databaze.changeDostupnostKnihy(nazev);
                    break;
                case 5:
                    databaze.printKnihyAbecedne();
                    break;
                case 6:
                    sc.nextLine();
                    System.out.println("Zadejte název knihy, o které chcete vypsat všechny informace:");
                    nazev = sc.nextLine();
                    databaze.getKniha(nazev);
                    break;
                case 7:
                    sc.nextLine();
                    System.out.println("Zadejte autora, u kterého chcete ukázat knihy v databázi:");
                    autor = sc.nextLine();
                    databaze.printKnihyAutora(autor);
                    break;
                case 8:
                    sc.nextLine();
                    System.out.println("Zadejte žánr, u kterého chcete ukázat knihy v databázi:");
                    zanr = sc.nextLine();
                    databaze.printKnihyZanru(zanr);
                    break;
                case 9:
                    databaze.printKnihyVypujcene();
                    break;
                case 10:
                    sc.nextLine();
                    System.out.println("Zadejte název knížky, kterou chcete uložit do souboru:");
                    nazev = sc.nextLine();
                    databaze.saveKniha(nazev);
                    break;
                case 11:
                    sc.nextLine();
                    System.out.println("Zadejte název souboru ve složce 'knizky', který obsahuje informace o knížce:");
                    nazev = sc.nextLine();
                    databaze.loadKniha(nazev);
                    break;
                case 12:
                    run = false;
                    databaze.saveDB();
                    DBConnection.disconnect();
                    break;
                default:
                    System.out.println("! Zadali jste špatné označení možnosti! Chcete pokračovat? (0)");
                    moznost = pouzeCelaCisla(sc);
                    if(moznost != 0) {
                        run = false;
                        databaze.saveDB();
                        DBConnection.disconnect();
                    }
                    break;
            }
        }
    }
}