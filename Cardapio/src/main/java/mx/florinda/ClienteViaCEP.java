package mx.florinda;

import java.net.URL;
import java.util.Scanner;

public class ClienteViaCEP {
    static void main() throws Exception {

        URL url = new URL("https://viacep.com.br/ws/01001000/json/");

        try (Scanner scanner = new Scanner(url.openStream())){
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        }
    }
}
