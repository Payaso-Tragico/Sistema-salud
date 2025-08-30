import java.util.Scanner;

public class Utilidad {
    public static int leerEntero(Scanner sc, String mensaje) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);
            String entrada = sc.nextLine();
            try {
                numero = Integer.parseInt(entrada);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: ingresa un número entero válido.");
            }
        }

        return numero;
    }
}
