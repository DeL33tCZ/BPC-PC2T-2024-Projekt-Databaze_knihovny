package cz.DBProjekt;

import java.util.Comparator;

public interface KnihaCompare {
    public static Comparator<Kniha> compareAbecedne() {
        return Comparator.comparing(Kniha::getNazev);
    }

    public static Comparator<Kniha> compareRok() {
        return Comparator.comparing(Kniha::getRokVydani);
    }

}
