package cz.DBProjekt;

import com.google.gson.Gson;
import cz.DBProjekt.db.DBConnection;

import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static cz.DBProjekt.Aplikace.pouzeCelaCisla;

public class Databaze {
    public Databaze() {
        prvky = new HashMap<>();
        sc = new Scanner(System.in);
    }

    public void setKniha(int moznost) {
        String nazev, autor;
        int rok_vydani;
        System.out.println("Zadejte název knihy:");
        nazev = sc.nextLine();
        System.out.println("Zadejte autora/y knihy (jméno přijmení, ...):");
        autor = sc.nextLine();
        System.out.println("Zadejte rok vydání knihy:");
        rok_vydani = pouzeCelaCisla(sc);
        switch(moznost) {
            case 1:
                String zanr;
                sc.nextLine();
                System.out.println("Zadejte žánr románu:");
                zanr = sc.nextLine();
                prvky.put(nazev, new Romany(nazev, autor, rok_vydani, zanr));
                break;
            case 2:
                int pro_rocnik;
                System.out.println("Zadejte pro jaký ročník je učebnice vhodná:");
                pro_rocnik = pouzeCelaCisla(sc);
                prvky.put(nazev, new Ucebnice(nazev, autor, rok_vydani, pro_rocnik));
                break;
        }
    }

    public void changeKniha(String nazev) {
        String vratit = easySearch(nazev, "nazev");
        if(!vratit.equals("!")) {
            boolean subrun = true;
            String autor;
            int rok_vydani, moznost;
            while (subrun) {
                System.out.println("------------------------------------------------------------------------------------");
                System.out.println("Vyberte požadovanou úpravu knihy:");
                System.out.println(" 1 .. změna autora(u)");
                System.out.println(" 2 .. změna roku vydání");
                System.out.println(" 3 .. změna stavu dostupnosti");
                System.out.println(" 4 .. vyskočit z úprav knihy");
                System.out.print("Vybraná možnost: ");
                moznost = pouzeCelaCisla(sc);
                System.out.println("------------------------------------------------------------------------------------");
                switch (moznost) {
                    case 1:
                        sc.nextLine();
                        System.out.println("Zadejte nového autora(y) (jméno přijmení, ...):");
                        autor = sc.nextLine();
                        if (prvky.get(vratit) instanceof Romany) {
                            prvky.put(vratit, new Romany(vratit, autor, prvky.get(vratit).getRokVydani(), ((Romany) prvky.get(vratit)).getZanr()));
                        } else {
                            prvky.put(vratit, new Ucebnice(vratit, autor, prvky.get(vratit).getRokVydani(), ((Ucebnice) prvky.get(vratit)).getProRocnik()));
                        }
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.println("Zadejte nový rok vydání:");
                        rok_vydani = pouzeCelaCisla(sc);
                        if (prvky.get(vratit) instanceof Romany) {
                            prvky.put(vratit, new Romany(vratit, prvky.get(vratit).getAutor(), rok_vydani, ((Romany) prvky.get(vratit)).getZanr()));
                        } else {
                            prvky.put(vratit, new Ucebnice(vratit, prvky.get(vratit).getAutor(), rok_vydani, ((Ucebnice) prvky.get(vratit)).getProRocnik()));
                        }
                        break;
                    case 3:
                        prvky.get(vratit).changeStavDostupnosti();
                        break;
                    default:
                        subrun = false;
                        break;
                }
            }
        }
    }

    public void deleteKniha(String nazev) {
        String vratit = easySearch(nazev, "nazev");
        if(!vratit.equals("!")) {
            prvky.remove(vratit);
            System.out.println("Kniha '" + vratit + "' byla odstraněna z databáze!");
        }
    }

    public void changeDostupnostKnihy(String nazev) {
        String vratit = easySearch(nazev, "nazev");
        if(!vratit.equals("!")) {
            int moznost;
            System.out.println("Zadejte aktuální stav knihy:");
            System.out.println(" 1 .. vypůjčená");
            System.out.println(" 2 .. vrácená");
            System.out.print("Vybraná možnost: ");
            moznost = pouzeCelaCisla(sc);
            switch (moznost) {
                case 1:
                    prvky.get(vratit).setStavDostupnosti("vypůjčeno");
                    break;
                case 2:
                    prvky.get(vratit).setStavDostupnosti("k dispozici");
                    break;
            }
        }
    }

    public void printKnihyAbecedne() {
        if (getNumberOfEntries() != 0) {
            System.out.println("Výpis všech knih v databázi:");
            Collection knihy = prvky.values();
            ArrayList<Kniha> seznam = new ArrayList<>(knihy);
            Collections.sort(seznam, KnihaCompare.compareAbecedne());
            for (Kniha kniha : seznam) {
                printTypKnihy(kniha);
            }
        }
        else {
            System.out.println("! Databáze je prázdná!");
        }
    }

    public void getKniha(String nazev) {
        printEasySearchOutput(easySearch(nazev, "nazev"), "nazev");
    }

    public void printKnihyAutora(String autor) {
        printEasySearchOutput(easySearch(autor, "autor"), "autor");
    }

    public void printKnihyZanru(String zanr) {
        printEasySearchOutput(easySearch(zanr, "zanr"), "zanr");
    }

    public void printKnihyVypujcene() {
        Collection knihy = prvky.values();
        ArrayList<Kniha> seznam = new ArrayList<>(knihy);
        Collections.sort(seznam, KnihaCompare.compareAbecedne());
        ArrayList<Kniha> vysledek = new ArrayList<>();
        for (Kniha kniha : seznam) {
            if (kniha.getStavDostupnosti().equals("vypůjčeno")) {
                vysledek.add(kniha);
            }
        }
        if(vysledek.isEmpty()) {
            System.out.println("! Nejsou vypůjčeny žádné knihy!");
        }
        else {
            System.out.println("Vypujcene knihy:");
            for(Kniha kniha : vysledek) {
                printTypKnihy(kniha);
            }
        }
    }

    public void saveKniha(String nazev) {
        String vratit = easySearch(nazev, "nazev");
        Kniha kniha = prvky.get(vratit);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json_string = gson.toJson(kniha);
        try (FileWriter file = new FileWriter("knizky/" + vratit + ".json")) {
            file.write(json_string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadKniha(String nazev_souboru) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Kniha.class, new KnihaDeserializer()).create();
        try(FileReader file = new FileReader("knizky/" + nazev_souboru + ".json")) {
            Kniha kniha = gson.fromJson(file, Kniha.class);
            prvky.put(kniha.getNazev(), kniha);
        }
        catch (IOException e) {
            System.out.println("! Zadaný název souboru '" + nazev_souboru + "' nebyl nalezen!");
        }
    }

    public void saveDB() {
        Collection knihy = prvky.values();
        ArrayList<Kniha> seznam = new ArrayList<>(knihy);
        Connection conn = DBConnection.connect();
        try(PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM knihy")) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String insert_everything = "INSERT INTO knihy (nazev,autor,rok_vydani,stav,zanr,pro_rocnik) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = conn.prepareStatement(insert_everything)) {
            for(Kniha kniha : seznam) {
                String zanr = "";
                String pro_rocnik = "";
                if(kniha instanceof Romany) {
                    zanr = ((Romany) kniha).getZanr();
                }
                else {
                    pro_rocnik = Integer.toString(((Ucebnice) kniha).getProRocnik());
                }
                preparedStatement.setString(1, kniha.getNazev());
                preparedStatement.setString(2, kniha.getAutor());
                preparedStatement.setString(3, Integer.toString(kniha.getRokVydani()));
                preparedStatement.setString(4, kniha.getStavDostupnosti());
                preparedStatement.setString(5, zanr);
                preparedStatement.setString(6, pro_rocnik);
                preparedStatement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadDB() {
        Connection conn = DBConnection.connect();
        String select_everything = "SELECT * FROM knihy";
        try(PreparedStatement preparedStatement = conn.prepareStatement(select_everything)) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                if(Objects.equals(rs.getString("pro_rocnik"), "")) {
                    prvky.put(rs.getString("nazev"), new Romany(rs.getString("nazev"), rs.getString("autor"), rs.getInt("rok_vydani"), rs.getString("zanr")));
                }
                else {
                    prvky.put(rs.getString("nazev"), new Ucebnice(rs.getString("nazev"), rs.getString("autor"), rs.getInt("rok_vydani"), rs.getInt("pro_rocnik")));
                }
                prvky.get(rs.getString("nazev")).setStavDostupnosti(rs.getString("stav"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printTypKnihy(Kniha kniha) {
        if (kniha instanceof Romany) {
            System.out.println("Román:\t název: " + kniha.getNazev() + ", autor(i): " + kniha.getAutor() + ", rok vydání: " + kniha.getRokVydani() + ", žánr: " + ((Romany) kniha).getZanr() + ", stav: " + kniha.getStavDostupnosti());
        }
        else {
            System.out.println("Učebnice:\t název: " + kniha.getNazev() + ", autor(i): " + kniha.getAutor() + ", rok vydání: " + kniha.getRokVydani() + ", pro ročník: " + ((Ucebnice) kniha).getProRocnik() + ", stav: " + kniha.getStavDostupnosti());
        }
    }

    private String easySearch(String findme, String kriterium_hledani) {
        Collection knihy = prvky.values();
        ArrayList<Kniha> seznam = new ArrayList<>(knihy);
        if(kriterium_hledani.equals("autor")) {
            Collections.sort(seznam, KnihaCompare.compareRok());
        }
        else if(kriterium_hledani.equals("nazev") || kriterium_hledani.equals("zanr")) {
            Collections.sort(seznam, KnihaCompare.compareAbecedne());
        }
        findme = findme.toLowerCase();
        ArrayList<String> vysledek = new ArrayList<>();
        for(Kniha kniha : seznam) {
            if(vysledek.contains(kniha.getAutor()) || (kniha instanceof Romany && vysledek.contains(((Romany) kniha).getZanr()))) {
                continue;
            }
            if(findme.equals(kniha.getAutor().toLowerCase()) && kriterium_hledani.equals("autor")) {
                vysledek.add(kniha.getAutor());
                continue;
            }
            else if(findme.equals(kniha.getNazev().toLowerCase()) && kriterium_hledani.equals("nazev")) {
                vysledek.add(kniha.getNazev());
                break;
            }
            else if((kniha instanceof Romany && findme.equals(((Romany) kniha).getZanr().toLowerCase())) && kriterium_hledani.equals("zanr")) {
                vysledek.add(((Romany) kniha).getZanr());
                break;
            }
            String[] rozdeleno = new String[0];
            if(kriterium_hledani.equals("autor")) {
                rozdeleno = kniha.getAutor().toLowerCase().split(", ");
            }
            else if(kriterium_hledani.equals("nazev")) {
                rozdeleno = kniha.getNazev().toLowerCase().split(", ");
            }
            else if(kniha instanceof Romany && kriterium_hledani.equals("zanr")) {
                rozdeleno = ((Romany) kniha).getZanr().toLowerCase().split(", ");
            }
            boolean breaker = false;
            for(String rozdil : rozdeleno) {
                if(breaker) {
                    break;
                }
                if(findme.equals(rozdil) && kriterium_hledani.equals("autor")) {
                    vysledek.add(kniha.getAutor());
                    break;
                }
                else if(findme.equals(rozdil) && kriterium_hledani.equals("nazev")) {
                    vysledek.add(kniha.getNazev());
                    break;
                }
                else if(findme.equals(rozdil) && kriterium_hledani.equals("zanr")) {
                    vysledek.add(((Romany) kniha).getZanr());
                    break;
                }
                String[] nadeleno = rozdil.split(" ");
                for (String nadil : nadeleno) {
                    if(breaker) {
                        break;
                    }
                    if(findme.equals(nadil) && kriterium_hledani.equals("autor")) {
                        vysledek.add(kniha.getAutor());
                        break;
                    }
                    else if(findme.equals(nadil) && kriterium_hledani.equals("nazev")) {
                        vysledek.add(kniha.getNazev());
                        break;
                    }
                    else if(findme.equals(nadil) && kriterium_hledani.equals("zanr")) {
                        vysledek.add(((Romany) kniha).getZanr());
                        break;
                    }
                    else {
                        int hledany = findme.length();
                        for(int i = 0; i < nadil.length()-hledany+1; i++) {
                            String part = "";
                            for(int j = i; j < i+hledany; j++) {
                                part += nadil.charAt(j);
                            }
                            if(findme.equals(part) && kriterium_hledani.equals("autor")) {
                                vysledek.add(kniha.getAutor());
                                breaker = true;
                                break;
                            }
                            else if(findme.equals(part) && kriterium_hledani.equals("nazev")) {
                                vysledek.add(kniha.getNazev());
                                breaker = true;
                                break;
                            }
                            else if(findme.equals(part) && kriterium_hledani.equals("zanr")) {
                                vysledek.add(((Romany) kniha).getZanr());
                                breaker = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if(vysledek.isEmpty()) {
            System.out.println("! Pro zadaný výraz '" + findme + "' nebyl nalezen žádný výstup!");
            return "!";
        }
        else {
            if(vysledek.size() > 1) {
                return choose(findme, vysledek);
            }
            return vysledek.get(0);
        }
    }

    private void printEasySearchOutput(String vratit, String kriterium_hledani) {
        Collection knihy = prvky.values();
        ArrayList<Kniha> seznam = new ArrayList<>(knihy);
        if(kriterium_hledani.equals("autor")) {
            Collections.sort(seznam, KnihaCompare.compareRok());
        }
        else if(kriterium_hledani.equals("nazev") || kriterium_hledani.equals("zanr")) {
            Collections.sort(seznam, KnihaCompare.compareAbecedne());
        }
        for (Kniha kniha : seznam) {
            if (kriterium_hledani.equals("autor") && vratit.equals(kniha.getAutor())) {
                printTypKnihy(kniha);
            } else if (kriterium_hledani.equals("nazev") && vratit.equals(kniha.getNazev())) {
                printTypKnihy(kniha);
            } else if (kriterium_hledani.equals("zanr") && (kniha instanceof Romany && vratit.equals(((Romany) kniha).getZanr()))) {
                System.out.println("Román:\t název: " + kniha.getNazev() + ", autor(i): " + kniha.getAutor() + ", rok vydání: " + kniha.getRokVydani() + ", žánr: " + ((Romany) kniha).getZanr() + ", stav: " + kniha.getStavDostupnosti());
            }
        }
    }

    private String choose(String findme, ArrayList<String> seznam) {
        HashMap<Integer, String> vybrano= new HashMap<>();
        System.out.println("Zadaný vyraz '" + findme + "' se shoduje s:");
        for(int i = 1; i <= seznam.size(); i++) {
            String vyber = seznam.get(i-1);
            vybrano.put(i, vyber);
            System.out.println(" " + i + " .. " + vyber);
        }
        System.out.print("Vybraná možnost: ");
        int moznost = pouzeCelaCisla(sc);
        return vybrano.get(moznost);
    }

    private int getNumberOfEntries() {
        return prvky.size();
    }

    private Scanner sc;
    private HashMap<String, Kniha> prvky;
}