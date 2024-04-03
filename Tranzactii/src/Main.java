import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.function.DoubleFunction;

public class Main {

    static Map <Produs, List<Tranzactie>> stocuri = new HashMap<>();


    static void AdaugaProdus (int cod, String denumire) {
        var produs = new Produs (cod, denumire);
        if (stocuri.containsKey(produs)) {
            throw new IllegalArgumentException("Codul deja exista pentru: " + produs);

        }
        stocuri.put(produs, new ArrayList<>());
    }

    static void AdaugaTranzactie (TipTranzactie tip, LocalDate data, int codProdus, int cantitate) {
        var produs = new Produs (codProdus);
        if (!stocuri .containsKey(produs)) {
            throw new IllegalArgumentException("Produsul nu exista - " + codProdus);
        }
        var lista = stocuri. get(produs);
        lista.add(new Tranzactie(tip, data, codProdus, cantitate));
    }

    static void AfisareStocuri() {
        for (var produs : stocuri.keySet()) {
            var lista = stocuri.get(produs);
            var cantitate = 0;
            LocalDate data = null;
            System.out.printf("%2d %-10s %3d buc (ultima tranzactie: %s)%n", produs.getCod(), produs.getDenumire(), cantitate, data);
        }
    }

    public static void main(String[] args) {
        //stocuri.put(new Produs (1, "Mere"), new ArrayList<>());

        //stocuri.get(new Produs (1)).add(new Tranzactie (TipTranzactie.INTRARE, LocalDate.of(2024, 3,1), 1, 7));

        //System.out.println(stocuri);



        AdaugaProdus(1, "Mere");
        AdaugaProdus(2, "Pere");
        AdaugaProdus(3, "Cirese");
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2024, 3, 11), 1, 10);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2024, 3, 11), 2, 5);
        AdaugaTranzactie(TipTranzactie.IESIRE, LocalDate.of(2024, 3, 11), 1, 7);
        AfisareStocuri();
    }
}