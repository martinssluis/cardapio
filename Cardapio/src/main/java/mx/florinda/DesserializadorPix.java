package mx.florinda;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DesserializadorPix {
    static void main() throws Exception {

        try(var fis = new FileInputStream("pix.ser");
        var ois = new ObjectInputStream(fis)){

            Pix pix = (Pix) ois.readObject();
            System.out.println(pix);
            System.out.println(pix.getChaveDestino());
        }

    }
}
