import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Examen implements Comparable <Examen> {
    private final LocalDate data;
    private final String profesor;
    private final String numeDisciplina;
    private final int numarStudentiInscrisi;
    private final int numarStudentiExaminati;

    public Examen(LocalDate data, String profesor, String numeDisciplina, int numarStudentiInscrisi, int numarStudentiExaminati) {
        this.data = data;
        this.profesor = profesor;
        this.numeDisciplina = numeDisciplina;
        this.numarStudentiInscrisi = numarStudentiInscrisi;
        this.numarStudentiExaminati = numarStudentiExaminati;
    }

    public Examen() {
        this.data = LocalDate.of(2020, 2, 9);
        this.profesor = "N/A";
        this.numeDisciplina = "N/A";
        this.numarStudentiInscrisi = 0;
        this.numarStudentiExaminati = 0;
    }

    public LocalDate getData() {
        return data;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public int getNumarStudentiInscrisi() {
        return numarStudentiInscrisi;
    }

    public int getNumarStudentiExaminati() {
        return numarStudentiExaminati;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "data=" + data +
                ", profesor='" + profesor + '\'' +
                ", numeDisciplina='" + numeDisciplina + '\'' +
                ", numarStudentiInscrisi=" + numarStudentiInscrisi +
                ", numarStudentiExaminati=" + numarStudentiExaminati +
                '}';
    }
    public double rataAbsenteism() {
        return ((double)numarStudentiExaminati  /  numarStudentiInscrisi) * 100;
    }

    @Override
    public int compareTo(Examen o) {
        Examen ex = (Examen) o;
        return Integer.compare(numarStudentiExaminati, ex.numarStudentiExaminati);
    }
}

public class Main {
    public static void main (String[] args) throws Exception {
        List<Examen> examene;
        try (var fisier = new BufferedReader(new FileReader("examene.csv"))) {
            examene = fisier.lines()
                    .map (linie -> {
                        String[] elemente = linie.split(",");
                        LocalDate data = LocalDate.parse(elemente[0], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                        String profesor = elemente[1];
                        String numeDisciplina = elemente[2];
                        int numarStudentiInscrisi = Integer.parseInt(elemente[3]);
                        int numarStudentiExaminati = Integer.parseInt(elemente[4]);
                        return new Examen (data, profesor, numeDisciplina, numarStudentiInscrisi, numarStudentiExaminati);
                    })
                    .collect(Collectors.toList());
        }
        examene.stream()
                .forEach(System.out::println);
        System.out.println("*********************");
        long numarExamene = examene.stream()
                .count();
        System.out.println("Numarul de examene este: " + numarExamene);
        System.out.println("*********************");
        Map<String, Double> absenteismMediuPerDisciplina = examene.stream()
                .collect(Collectors.groupingBy(Examen::getNumeDisciplina, Collectors.averagingDouble(Examen::rataAbsenteism)));
        System.out.println("Absenteismul mediu pentru fiecare disciplina este: " + absenteismMediuPerDisciplina);

        try (var writer = new BufferedWriter(new FileWriter("discipline.csv"))) {
            for (Examen examen : examene) {
                writer.write("Disciplina: " + " " + examen.getNumeDisciplina() + "data examen: " + " " + examen.getData() + " " + "profesor: " + " " + examen.getProfesor() + " " + " numar elevi examinati: " + " " + examen.getNumarStudentiExaminati() + "\n");
            }
        }
    }
}