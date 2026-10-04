package mx.florinda;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.Instant;

public class SerializadorPix {
    static void main() throws Exception {

        var pix = new Pix(1L, new BigDecimal("10.99"), "luis@email.com", Instant.now(), "pix fake kkk");

        try(var fos = new FileOutputStream("pix.ser");
        var oos = new ObjectOutputStream(fos)){
            oos.writeObject(pix);
        }
    }
}
