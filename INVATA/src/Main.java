import java.io.*;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Element implements Comparable<Element> {
    private final int index_linie;
    private final int index_coloana;
    private final double valoare;

    public Element(int index_linie, int index_coloana, double valoare) {
        this.index_linie = index_linie;
        this.index_coloana = index_coloana;
        this.valoare = valoare;
    }

    public Element() {
        this.index_linie = 0;
        this.index_coloana = 0;
        this.valoare = 0;
    }

    public int getIndex_linie() {
        return index_linie;
    }

    public int getIndex_coloana() {
        return index_coloana;
    }

    public double getValoare() {
        return valoare;
    }

    @Override
    public String toString() {
        return "Element{" +
                "index_linie=" + index_linie +
                ", index_coloana=" + index_coloana +
                ", valoare=" + valoare +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element e = (Element) o;
        return index_linie == e.getIndex_linie() && index_coloana == e.getIndex_coloana() && valoare == e.getValoare();
    }

    @Override
    public int hashCode() {
        return Objects.hash(index_linie, index_coloana, valoare);
    }

    @Override
    public int compareTo(Element o) {
        Element e = (Element) o;
        return Double.compare(valoare, e.valoare);
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        List<Element> elemente;
        //citire fisier text / csv
        try (var fisier = new BufferedReader(new FileReader("matricerara.csv"))) {
            elemente = fisier.lines()
                    .map(linie -> new Element(
                            Integer.parseInt(linie.split(",")[0]),
                            Integer.parseInt(linie.split(",")[1]),
                            Double.parseDouble(linie.split(",")[2])
                    ))
                            .collect(Collectors.toList());
        }
    /*for (Element element : elemente) {
        System.out.println(element);
        }*/

        elemente.stream()
                .forEach(System.out::println);
        System.out.println("****************************");
        //asa se afiseaza elementele negative
        elemente.stream()
                .filter(element -> element.getValoare()<0)
                .forEach(System.out::println);
        //afisare numar de elemente negative
        long numarElementeNegative = elemente.stream()
                .filter(element -> element.getValoare()<0)
                .count();
        System.out.println("Numarul de elemente negative este: " + " " + numarElementeNegative);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        //salvare / salvare fisier binar
        try (var fileOutputStream = new FileOutputStream("diagonal.dat");
        var dataOutputStream = new DataOutputStream(fileOutputStream)) {
            for (Element element : elemente) {
                if (element.getIndex_linie()== element.getIndex_coloana()) {
                    dataOutputStream.writeInt(element.getIndex_linie());
                    dataOutputStream.writeInt(element.getIndex_coloana());
                    dataOutputStream.writeDouble(element.getValoare());
                }
            }
        }
        //citire fisier binar
        try (var fileInputStream = new FileInputStream("diagonal.dat");
             var dataInputStream = new DataInputStream(fileInputStream)) {
            while (dataInputStream.available()>0) {
                int index_linie = dataInputStream.readInt();
                int index_coloana = dataInputStream.readInt();
                double valoare = dataInputStream.readDouble();
                System.out.println("Element: linie: " + " " + index_linie + " " + "coloana: " + " " + index_coloana + " " + "valoare: " + " " + valoare + ".");
            }
        }
        //scriere / salvare fisier text / csv
        try (var writer = new BufferedWriter(new FileWriter("elemente.txt"))) {
            for (Element element : elemente) {
                writer.write(element.getIndex_linie() + "," + element.getIndex_coloana() + "," + element.getValoare() + "\n");
            }
        }

    }

}
