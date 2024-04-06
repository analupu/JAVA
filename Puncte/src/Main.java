import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Punct implements  Comparable<Punct> {
    private final String etichetaFigura;
    private final String etichetaPunct;
    private final double OX;
    private final double OY;

    public Punct(String etichetaFigura, String etichetaPunct, double OX, double OY) {
        this.etichetaFigura = etichetaFigura;
        this.etichetaPunct = etichetaPunct;
        this.OX = OX;
        this.OY = OY;
    }

    public Punct() {
        this.etichetaFigura = "N/A";
        this.etichetaPunct = "N/A";
        this.OX = 0;
        this.OY=0;
    }

    public String getEtichetaFigura() {
        return etichetaFigura;
    }

    public String getEtichetaPunct() {
        return etichetaPunct;
    }

    public double getOX() {
        return OX;
    }

    public double getOY() {
        return OY;
    }

    @Override
    public String toString() {
        return "Punct{" +
                "etichetaFigura='" + etichetaFigura + '\'' +
                ", etichetaPunct='" + etichetaPunct + '\'' +
                ", OX=" + OX +
                ", OY=" + OY +
                '}';
    }

    public double distanta() {
//        double OX = 0;
//        double OY = 0;
//        double distanta = Math.sqrt (OX*OX - OY*OY);
//        return distanta;
          return Math.sqrt(OX * OX + OY * OY);
    }


    @Override
    public int compareTo(Punct o) {
        Punct p = (Punct) o;
        return Double.compare(distanta(), p.distanta());
    }
}

public class Main {
    public static void main (String[] args) throws Exception{
        List<Punct> puncte;

        try (var fisier = new BufferedReader(new FileReader("puncte.csv"))) {
            puncte = fisier.lines()
                    .map (linie -> new Punct(
                           linie.split(",")[0],
                           linie.split(",")[1],
                           Double.parseDouble(linie.split(",")[2]),
                           Double.parseDouble(linie.split(",")[3])
                    ))
                    .collect(Collectors.toList());
        }
        puncte.stream()
                .forEach(System.out::println);
        System.out.println("**************************" + "\n");
        long numarPuncte = puncte.stream()
                .count();
        System.out.println("Numarul de puncte este: " + numarPuncte);
        System.out.println("**************************");
        Map<String, Long> numarPuncteFiecareFigura = puncte.stream()
                .collect(Collectors.groupingBy(Punct::getEtichetaFigura, Collectors.counting()));
        System.out.println("Numarul de puncte pentru fiecare figura este: " + numarPuncteFiecareFigura);

        try (var writer = new BufferedWriter(new FileWriter("distante.csv"))) {
            for (Punct punct : puncte) {
                writer.write(String.valueOf(punct.distanta()));
            }
        }
    }
}


