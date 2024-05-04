package cz.DBProjekt;

public class Ucebnice extends Kniha {
    private int pro_rocnik;
    public Ucebnice(String nazev, String autor, int rok_vydani, int pro_rocnik) {
        super(nazev, autor, rok_vydani);
        this.pro_rocnik = pro_rocnik;
    }

    public int getProRocnik() {
        return pro_rocnik;
    }

    public void setProRocnik(int pro_rocnik) {
        this.pro_rocnik = pro_rocnik;
    }
}
