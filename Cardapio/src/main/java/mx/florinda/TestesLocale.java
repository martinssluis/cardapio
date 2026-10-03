package mx.florinda;

import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestesLocale {
    static void main() {
        Locale.availableLocales().forEach(System.out::println);

        System.out.println("Default Locale: " + Locale.getDefault());

        Locale localeUS = Locale.US;
        Locale localePtBR = Locale.of("pt", "BR");

        DateTimeFormatter formatterDataHora = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        System.out.println(formatterDataHora.format(ZonedDateTime.now()));
        System.out.println(formatterDataHora.withLocale(localeUS).format(ZonedDateTime.now()));
        System.out.println(formatterDataHora.withLocale(localePtBR).format(ZonedDateTime.now()));

        System.out.println("----------------------------------------------------------");

        DateTimeFormatter formatterMesAno = DateTimeFormatter.ofPattern("MMMM/yyyy");
        System.out.println(formatterMesAno.format(YearMonth.now()));
        System.out.println(formatterMesAno.withLocale(localeUS).format(YearMonth.now()));
        System.out.println(formatterMesAno.withLocale(localePtBR).format(YearMonth.now()));

        System.out.println(NumberFormat.getCurrencyInstance().format(2.99));
        System.out.println(NumberFormat.getCurrencyInstance(localeUS).format(2.99));
        System.out.println(NumberFormat.getCurrencyInstance(localePtBR).format(2.99));

        System.out.println("----------------------------------------------------------");

        ResourceBundle mensagens = ResourceBundle.getBundle("mensagens");
        ResourceBundle mensagensUS = ResourceBundle.getBundle("mensagens", localeUS);
        ResourceBundle mensagensPtBR = ResourceBundle.getBundle("mensagens", localePtBR);

        System.out.println(mensagens.getString("categoria.cardapio.pratos_principais"));
        System.out.println(mensagensUS.getString("categoria.cardapio.pratos_principais"));
        System.out.println(mensagensPtBR.getString("categoria.cardapio.pratos_principais"));

    }
}
