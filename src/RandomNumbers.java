import java.io.*;
import java.util.Random;

public class RandomNumbers {

    public static void main(String[] args) {

        System.out.println("Numero di elementi:");
        BufferedReader console = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        int size;
        try {
            size = Integer.parseInt(console.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int[] array = new int[size];
        Random random = new Random();
        for(int i = 0; i < size; i++)
            array[i] = random.nextInt(size);

        System.out.println("Nome del file:");
        BufferedReader console2 = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String filename;
        try {
            filename = console2.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String path = "C:\\_Federico\\" + filename + ".txt";

        String buffer = "";
        for(int i = 0; i < size - 1; i++)
            buffer = buffer + (array[i] + ";");
        buffer = buffer + array[size - 1];
        //System.out.println(buffer);

        try {
            PrintStream ps = new PrintStream(path);
            ps.println(buffer);
            ps.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
