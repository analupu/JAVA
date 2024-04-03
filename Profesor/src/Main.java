import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Profesor {
    private final int idProfesor;
    private final String prenume;
    private final String nume;
    private final String departament;


    public Profesor(int idProfesor, String prenume, String nume, String departament) {
        this.idProfesor = idProfesor;
        this.prenume = prenume;
        this.nume = nume;
        this.departament = departament;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getDepartament() {
        return departament;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "idProfesor=" + idProfesor +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", departament='" + departament + '\'' +
                '}';
    }

    //constructor cu linie din fisier
    public Profesor(String linie) {
        var componente = linie.split("\t");
        this.idProfesor = Integer.parseInt(componente[0]);
        this.prenume = componente[1];
        this.nume=componente[2];
        this.departament = componente[3];
    }

}

class Programare {
    private final String ziua;
    private final String interval;
    private final Profesor profesor;
    private final String disciplina;
    private final String sala;
    private final boolean esteCurs;
    private final String formatie;

    public Programare(String ziua, String interval, Profesor profesor, String disciplina, String sala, boolean esteCurs, String formatie) {
        this.ziua = ziua;
        this.interval = interval;
        this.profesor = profesor;
        this.disciplina = disciplina;
        this.sala = sala;
        this.esteCurs = esteCurs;
        this.formatie = formatie;
    }

    public String getZiua() {
        return ziua;
    }

    public String getInterval() {
        return interval;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getSala() {
        return sala;
    }

    public boolean isEsteCurs() {
        return esteCurs;
    }

    public String getFormatie() {
        return formatie;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        //cerinta 1
        Map<Integer, Profesor> profesori;
        try(var fisier = new BufferedReader(new FileReader("profesori.txt"))) {
            profesori = fisier.lines()
                    .map(Profesor::new)
                    .collect(Collectors.toMap(Profesor::getIdProfesor, p -> p)); //collect() creaza un Map din stream-ul de profesori creat de map() si il adaugam in variabila profesori facand id-ul sau cheie in Map
        }

        List<Programare> programari;
        try(var fisier = new BufferedReader(new FileReader("programari.txt"))) {
            programari = fisier.lines()
                    .map(linie -> new Programare(
                            linie.split("\t")[0],
                            linie.split("\t")[1],
                            profesori.get(Integer.parseInt(linie.split("\t")[2])),
                            linie.split("\t")[3],
                            linie.split("\t")[4],
                            Boolean.parseBoolean(linie.split("\t")[5]),
                            linie.split("\t")[6]
                    ))
                    .collect(Collectors.toList());
        }

        //cerinta 2
        programari.stream()
                .filter(Programare::isEsteCurs)
                .map(Programare::getDisciplina)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        //cerinta 3
        programari.stream()
                .collect(Collectors.groupingBy(Programare::getProfesor))
                .entrySet().stream().forEach(e -> {
                    var prof = e.getKey();
                    var lista = e.getValue();
                    System.out.printf("%-30s %2d cursuri, %2d seminarii%n",
                            prof.getNume() + " " + prof.getPrenume(),
                            lista.stream().filter(Programare::isEsteCurs).count(),
                            lista.stream().filter(p -> !p.isEsteCurs()).count());
                });
    }
}