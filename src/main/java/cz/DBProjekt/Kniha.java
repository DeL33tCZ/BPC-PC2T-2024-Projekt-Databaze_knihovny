package cz.DBProjekt;

public abstract class Kniha implements KnihaCompare {
    private String nazev;
    private String autor;
    private int rok_vydani;
    private String stav_dostupnosti = "k dispozici";

    public Kniha(String nazev, String autor, int rok_vydani) {
        this.nazev = nazev;
        this.autor = autor;
        this.rok_vydani = rok_vydani;
    }

    public String getNazev() {
        return nazev;
    }

    public String getAutor() {
        return autor;
    }

    public int getRokVydani() {
        return rok_vydani;
    }

    public String getStavDostupnosti() {
        return stav_dostupnosti;
    }

    public void setStavDostupnosti(String stav) {
        this.stav_dostupnosti = stav;
    }

    public void changeStavDostupnosti() {
        if(this.stav_dostupnosti.equals("k dispozici")) {
            this.stav_dostupnosti = "vypůjčeno";
        }
        else {
            this.stav_dostupnosti = "k dispozici";
        }

    }
}
