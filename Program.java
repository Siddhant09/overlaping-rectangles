import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Overlaping rectangles program started");
            System.out.println();
            System.out.println("Please select a file to execute:");
            System.out.println("1: NoOverlapInput.txt");
            System.out.println("2: OverlapInput.txt");
            System.out.println("Default: SampleInput.txt");

            String fileName = "SampleInput.txt";
            String choise = scanner.nextLine().toString();
            
            if (choise.equals("1"))
                fileName = "NoOverlapInput.txt";
            else if (choise.equals("2"))
                fileName = "OverlapInput.txt";

            executeInputFile(fileName);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void executeInputFile(String fileName) throws IOException, FileNotFoundException {
        System.out.println();
        System.out.println("Executing file: " + fileName);
        System.out.println();
        try (BufferedReader br = new BufferedReader(new FileReader("InputFiles/" + fileName))) {
            String line = br.readLine();

            IntervalTree<Double> tree = new IntervalTree<Double>();
            Double[] rectangles = new Double[5];
            boolean isNoOverlap = false;

            while (line != null) {
                String[] item = line.split("\\s+");
                for (int i = 0; i < item.length; i++)
                    rectangles[i] = Double.parseDouble(item[i]);

                isNoOverlap = tree.checkOverlap(rectangles[0], rectangles[1], rectangles[2], rectangles[3], rectangles[4]);
                if (!isNoOverlap)
                    break;

                tree.insertNode(rectangles[0], rectangles[1], rectangles[2], rectangles[3], rectangles[4]);

                line = br.readLine();
            }
            if (isNoOverlap) {
                System.out.println("--------------------");
                System.out.println("| No overlap found |");
                System.out.println("--------------------");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}