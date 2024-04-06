import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class NotaContabila implements Comparable<NotaContabila> {
    private final LocalDate data;
    private final int condDebitor;
    private final int contCreditor;
    private final double suma;

    public NotaContabila(LocalDate data, int condDebitor, int contCreditor, double suma) {
        this.data = data;
        this.condDebitor = condDebitor;
        this.contCreditor = contCreditor;
        this.suma = suma;
    }

    public NotaContabila() {
        this.data = LocalDate.of(2000, 0, 0);
        this.condDebitor = 0;
        this.contCreditor = 0;
        this.suma = 0;
    }

    public LocalDate getData() {
        return data;
    }

    public int getCondDebitor() {
        return condDebitor;
    }

    public int getContCreditor() {
        return contCreditor;
    }

    public double getSuma() {
        return suma;
    }

    @Override
    public String toString() {
        return "NotaContabila{" +
                "data=" + data +
                ", condDebitor=" + condDebitor +
                ", contCreditor=" + contCreditor +
                ", suma=" + suma +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaContabila nota = (NotaContabila) o;
        return condDebitor == nota.getCondDebitor() && contCreditor == nota.getContCreditor() && suma == nota.getSuma();
    }

    @Override
    public int hashCode() {
        return Objects.hash(condDebitor, contCreditor, suma);
    }

    @Override
    public int compareTo(NotaContabila o) {
        NotaContabila nota = (NotaContabila) o;
        return this.data.compareTo(nota.data);
    }
}

public class Main {
    public static void main (String[] args) throws Exception {
        List<NotaContabila> note;
        try (var fisier = new BufferedReader(new FileReader("jurnal.csv"))) {
            note = fisier.lines()
                    .map (linie -> {
                        String[] elemente = linie.split(",");
                        LocalDate data = LocalDate.parse(elemente[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        int contDebitor = Integer.parseInt(elemente[1]);
                        int contCreditor = Integer.parseInt(elemente[2]);
                        double suma = Double.parseDouble(elemente[3]);
                        return new NotaContabila(data, contDebitor, contCreditor, suma);
                    })
                    .collect(Collectors.toList());
        }

        note.stream()
                .forEach(System.out::println);
        System.out.println("************************");
        double rulajTotal = note.stream()
                .mapToDouble(NotaContabila::getSuma)
                .sum();
        System.out.println("Rulajul total este: " + rulajTotal);
        System.out.println("*************************");
        Map<Integer, Double> rulajTotalFiecareCont = note.stream()
                .collect(Collectors.groupingBy(NotaContabila::getCondDebitor, Collectors.summingDouble(NotaContabila::getSuma)));
        System.out.println("Rulajul pentru fiecare cont este: " + rulajTotalFiecareCont);

        try (var writer = new BufferedWriter(new FileWriter("fisa.csv"))) {
            for (NotaContabila nota : note) {
                writer.write("Contul: " + nota.getContCreditor() + "," + nota.getSuma());
            }
        }
    }
}