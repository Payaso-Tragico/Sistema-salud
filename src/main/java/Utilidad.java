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
    public static void validarRut(String rut) throws FormatoInvalidoException {
        String regex = "\\d{7,8}-[\\dkK]";       
        if (!rut.matches(regex)) {
            throw new FormatoInvalidoException("RUT inválido: " + rut + ". Debe tener formato 21316835-5");
        }
    }   
}