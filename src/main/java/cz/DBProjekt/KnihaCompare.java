package cz.DBProjekt;

import java.util.Comparator;

public interface KnihaCompare {
    static Comparator<Kniha> compareAbecedne() {
        return Comparator.comparing(Kniha::getNazev);
    }

    static Comparator<Kniha> compareRok() {
        return Comparator.comparing(Kniha::getRokVydani);
    }

}
