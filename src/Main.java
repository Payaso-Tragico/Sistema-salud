import java.util.Scanner;

class Inicializador {
    public static void cargarDatos(Hospital hospitalCentral) {
        Paciente p1 = new Paciente("12345678-9", "Juan Pérez", 30, "Hipertensión");
        Paciente p2 = new Paciente("98765432-1", "María González", 45, "Diabetes Tipo 2");
        Paciente p3 = new Paciente("11222333-4", "Pedro Ramírez", 60, "Asma crónica");
        Paciente p4 = new Paciente("55443322-5", "Ana Torres", 25, "Embarazo");

        Consulta c1 = new Consulta(1, "2025-09-01", "Control presión arterial","A1", p1);
        Consulta c2 = new Consulta(2, "2025-09-02", "Chequeo anual","B2", p2);
        Consulta c3 = new Consulta(3, "2025-09-03", "Dificultad respiratoria","C3", p3);
        Consulta c4 = new Consulta(4, "2025-09-05", "Control prenatal","D4", p4);

        Medico m1 = new Medico("22334455-6", "Ana López", "Cardiología");
        Medico m2 = new Medico("66778899-0", "Carlos Ruiz", "Medicina General");
        Medico m3 = new Medico("33445566-7", "Felipe Morales", "Neumología");

        m1.getConsultas().add(c1);
        m2.getConsultas().add(c2);
        m3.getConsultas().add(c3);
        m2.getConsultas().add(c4);

        hospitalCentral.agregarPaciente(p1);
        hospitalCentral.agregarPaciente(p2);
        hospitalCentral.agregarPaciente(p3);
        hospitalCentral.agregarPaciente(p4);

        hospitalCentral.agregarMedico(m1);
        hospitalCentral.agregarMedico(m2);
        hospitalCentral.agregarMedico(m3);
    }
}

class PrintMenu{
    public static void printMenuPrincipal(){
        System.out.println("\n=== MENÚ HOSPITAL ===");
        System.out.println("1.- Gestion de pacientes");
        System.out.println("2.- Gestion de medicos");
        System.out.println("3.- Gestion de consultas");
        System.out.println("0.- Salir");
    }

    public static void printMenuPacientes(){
        System.out.println("\n=== MENÚ PACIENTES ===");
        System.out.println("1.- Agregar paciente");
        System.out.println("2.- Eliminar paciente");
        System.out.println("3.- Listar pacientes");
        System.out.println("4.- Modificar paciente");
        System.out.println("5.- Buscar paciente");
        System.out.println("0.- Salir");
    }

    public static void printMenuMedicos(){
        System.out.println("\n=== MENÚ MÉDICOS ===");
        System.out.println("1.- Agregar médico");
        System.out.println("2.- Eliminar médico");
        System.out.println("3.- Listar medicos");
        System.out.println("4.- Modificar médico");
        System.out.println("5.- Buscar médico por RUT");
        System.out.println("6.- Buscar médico por nombre");
        System.out.println("0.- Salir");
    }

    public static void printMenuConsultas() {
        System.out.println("\n=== MENÚ CONSULTAS ===");
        System.out.println("1.- Agregar consulta");
        System.out.println("2.- Eliminar consulta");
        System.out.println("3.- Listar consultas");
        System.out.println("4.- Modificar consulta");
        System.out.println("5.- Buscar consulta");
        System.out.println("0.- Salir");
    }
}

class Menu {
    public static void menuPacientes(Hospital hospital) {
        int opcion;
        Scanner sc = new Scanner(System.in);
        Paciente p;

        do {
            PrintMenu.printMenuPacientes();
            opcion = Utilidad.leerEntero(sc, "");

            switch (opcion) {
                case 1:
                    hospital.agregarPaciente(sc);
                    break;
                case 2:
                    hospital.eliminarPaciente(sc);
                    break;
                case 3:
                    hospital.listarPacientes();
                    break;
                case 4:
                    p = hospital.buscarPaciente(sc);
                    if(p != null){
                        p.modificar(sc);
                        break;
                    }
                    break;
                case 5:
                    p = hospital.buscarPaciente(sc);
                    if(p != null){
                        p.printDatos();
                        break;
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    public static void menuMedicos(Hospital hospital) {
        int opcion;
        String nombre;
        Scanner sc = new Scanner(System.in);
        Medico m;

        do {
            PrintMenu.printMenuMedicos();
            opcion = Utilidad.leerEntero(sc, "");

            switch (opcion) {
                case 1:
                    hospital.agregarMedico(sc);
                    break;
                case 2:
                    hospital.eliminarMedico(sc);
                    break;
                case 3:
                    hospital.listarMedicos();
                    break;
                case 4:
                    m = hospital.buscarMedico(sc);
                    if(m != null){
                        m.modificar(sc);
                        break;
                    }
                    break;
                case 5:
                    System.out.print("Ingrese RUT del médico objetivo: ");
                    m = hospital.buscarMedico(sc);
                    if(m != null){
                        m.printDatos();
                        break;
                    }
                    break;
                case 6:
                    System.out.print("Ingrese el nombre del medico: ");
                    nombre = sc.nextLine();
                    m = hospital.buscarMedico(nombre);
                    if(m != null){
                        m.printDatos();
                        break;
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    public static void menuConsultas(Hospital hospital) {
        int opcion;
        Scanner sc = new Scanner(System.in);
        Consulta c;

        do {
            PrintMenu.printMenuConsultas();
            opcion = Utilidad.leerEntero(sc, "");

            switch (opcion) {
                case 1:
                    hospital.agregarConsulta(sc);
                    break;
                case 2:
                    hospital.eliminarConsulta(sc);
                    break;
                case 3:
                    hospital.listarConsultas();
                    break;
                case 4:
                    c = hospital.buscarConsulta(sc);
                    if(c != null) {
                        do {
                            System.out.println("1.- Modificar los datos de la consulta\n2.- Reagendar la Consulta");
                            opcion = Utilidad.leerEntero(sc, "");
                        } while (opcion != 1 && opcion != 2);
                        if (opcion == 1) {
                            c.modificarConsulta(hospital, sc);
                        } else {
                            c.modificarConsulta(sc);
                        }
                    }
                    break;
                case 5:
                    c = hospital.buscarConsulta(sc);
                    hospital.printConsulta(c);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }
}

public class Main {
    public static void main(String[] args) {
        int opcion;
        Hospital hospitalCentral = new Hospital("Hospital Central", "Av. Principal 123", "2222 3333");
        Inicializador.cargarDatos(hospitalCentral);

        do{
            PrintMenu.printMenuPrincipal();
            Scanner sc = new Scanner(System.in);
            opcion = Utilidad.leerEntero(sc, "");
            switch (opcion){
                case 1:
                    Menu.menuPacientes(hospitalCentral);
                    break;
                case 2:
                    Menu.menuMedicos(hospitalCentral);
                    break;
                case 3:
                    Menu.menuConsultas(hospitalCentral);
                    break;
                default:
            }
        }while(opcion != 0);
    }
}