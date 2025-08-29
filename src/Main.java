import java.util.Scanner;

class Inicializador {
    public static void cargarDatos(Hospital hospitalCentral) {
        /*bloque gordo con datos que le pedí a chatGPT. Porque no me da flojera escribir código, pero sí el ponerme a crear datos
        falsos para un sistema falso y que no sea "rut:1111111111111" "nombre:persona1/persona2/persona3", etc.de esta forma,
         al menos queda más "realista"*/
        Paciente p1 = new Paciente("12345678-9", "Juan Pérez", 30, "Hipertensión");
        Paciente p2 = new Paciente("98765432-1", "María González", 45, "Diabetes Tipo 2");
        Paciente p3 = new Paciente("11222333-4", "Pedro Ramírez", 60, "Asma crónica");
        Paciente p4 = new Paciente("55443322-5", "Ana Torres", 25, "Embarazo");

        Medico m1 = new Medico("22334455-6", "Dra. Ana López", "Cardiología");
        Medico m2 = new Medico("66778899-0", "Dr. Carlos Ruiz", "Medicina General");
        Medico m3 = new Medico("33445566-7", "Dr. Felipe Morales", "Neumología");

        Consulta c1 = new Consulta(1, "2025-09-01", "Control presión arterial","A1", p1, m1);
        Consulta c2 = new Consulta(2, "2025-09-02", "Chequeo anual","B2", p2, m2);
        Consulta c3 = new Consulta(3, "2025-09-03", "Dificultad respiratoria","C3", p3, m3);
        Consulta c4 = new Consulta(4, "2025-09-05", "Control prenatal","D4", p4, m2);

        hospitalCentral.agregarPaciente(p1);
        hospitalCentral.agregarPaciente(p2);
        hospitalCentral.agregarPaciente(p3);
        hospitalCentral.agregarPaciente(p4);

        hospitalCentral.agregarMedico(m1);
        hospitalCentral.agregarMedico(m2);
        hospitalCentral.agregarMedico(m3);

        hospitalCentral.agregarConsulta(c1);
        hospitalCentral.agregarConsulta(c2);
        hospitalCentral.agregarConsulta(c3);
        hospitalCentral.agregarConsulta(c4);
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
        System.out.println("4.- Buscar médico");
        System.out.println("5.- Modificar médico");
        System.out.println("6.- Mostrar consultas de medico");
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

class OperacionesPacientes{
    public static void agregarPaciente(Hospital hospital, Scanner sc){
        String rut, nombre, diagnostico;
        int edad;

        System.out.print("Ingrese RUT del paciente: ");
        rut = sc.nextLine();
        System.out.print("Ingrese nombre: ");
        nombre = sc.nextLine();
        System.out.print("Ingrese edad: ");
        edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese diagnóstico: ");
        diagnostico = sc.nextLine();

        Paciente paciente = new Paciente(rut, nombre, edad, diagnostico);
        hospital.agregarPaciente(paciente);
    }

    public static Paciente buscarPaciente(Hospital hospital, Scanner sc){
        System.out.print("Ingrese RUT del paciente objetivo: ");
        String rut = sc.nextLine();

        for (Paciente p : hospital.getPacientes()) {
            if (p.getRut().equals(rut)) {
                return p;
            }
        }
        return null;
    }

    public static void printPaciente(Hospital hospital, Scanner sc){
        Paciente p = buscarPaciente(hospital, sc);

        if (p != null) {
            System.out.println("Paciente encontrado:");
            System.out.println("RUT: " + p.getRut());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Edad: " + p.getEdad());
            System.out.println("Diagnóstico: " + p.getDiagnostico());
        } else {
            System.out.println("El paciente no existe.");
        }
    }

    public static void eliminarPaciente(Hospital hospital, Scanner sc){
        Paciente p = buscarPaciente(hospital, sc);

        if(p != null){
            hospital.removePaciente(p.getRut());
        } else {
            System.out.println("El paciente no existe.");
        }
    }

    public static void listarPacientes(Hospital hospital){
        System.out.println("\n--- LISTA DE PACIENTES ---");
        for(Paciente p : hospital.getPacientes()) {
            System.out.println("RUT: " + p.getRut() + " | Nombre: " + p.getNombre() + " | Edad: " + p.getEdad() + " | Diagnóstico: " + p.getDiagnostico());
        }
    }

    public static void modificarPaciente(Hospital hospital, Scanner sc){
        Paciente p = buscarPaciente(hospital, sc);

        if (p != null) {
            System.out.print("Ingrese nuevo nombre: ");
            String nombre = sc.nextLine();
            p.setNombre(nombre);

            System.out.print("Ingrese nueva edad: ");
            int edad = sc.nextInt();
            sc.nextLine();
            p.setEdad(edad);

            System.out.print("Ingrese nuevo diagnóstico: ");
            String diag = sc.nextLine();
            p.setDiagnostico(diag);

        } else {
            System.out.println("El paciente no existe.");
        }
    }
}

class OperacionesMedicos{

    public static void agregarMedico(Hospital hospital, Scanner sc){
        String rut, nombre, especialidad;

        System.out.print("Ingrese RUT del médico: ");
        rut = sc.nextLine();
        System.out.print("Ingrese nombre: ");
        nombre = sc.nextLine();
        System.out.print("Ingrese especialidad: ");
        especialidad = sc.nextLine();

        Medico medico = new Medico(rut, nombre, especialidad);
        hospital.agregarMedico(medico);
    }

    public static Medico buscarMedico(Hospital hospital, Scanner sc){
        String rut;

        System.out.print("Ingrese RUT del médico objetivo: ");
        rut = sc.nextLine();

        for(Medico m : hospital.getMedicos()){
            if(m.getRut().equals(rut)){
                return m;
            }
        }
        return null;
    }

    public static void printMedico(Hospital hospital, Scanner sc){
        Medico m = buscarMedico(hospital, sc);

        if(m != null){
            System.out.println("Médico encontrado:");
            System.out.println("RUT: " +m.getRut());
            System.out.println("Nombre: " +m.getNombre());
            System.out.println("Especialidad: " +m.getEspecialidad());
        }else{
            System.out.println("Médico no encontrado.");
        }
    }

    public static void printMedico(Medico m){
        if(m != null){
            System.out.println("\n--- DATOS DEL MÉDICO ---");
            System.out.println("RUT: " +m.getRut());
            System.out.println("Nombre: " +m.getNombre());
            System.out.println("Especialidad: " +m.getEspecialidad());
        }else{
            System.out.println("Médico no encontrado.");
        }
    }

    //El otro metodo con sobrecarga. este imprime todas las consultas asociadas al medico buscado.
    public static void printMedico(Scanner sc, Hospital hospital){
        Medico medico;

        medico = OperacionesMedicos.buscarMedico(hospital, sc);
        if (medico == null) {
            return;
        }

        OperacionesMedicos.printMedico(medico);
        System.out.println("\n--- CONSULTAS DEL MÉDICO ---");
        boolean tieneConsultas = false;
        for (Consulta c : hospital.getConsultas()) {
            if (c.getMedico().equals(medico)) {
                OperacionesConsultas.printConsulta(c);
                System.out.println("----------------------------------------");
                tieneConsultas = true;
            }
        }

        if (!tieneConsultas) {
            System.out.println("Este medico no tiene consultas registradas.");
        }
    }

    public static void listarMedicos(Hospital hospital){
        System.out.println("\n--- LISTA DE MÉDICOS ---");
        for (Medico m : hospital.getMedicos()){
            System.out.println("RUT: " +m.getRut() +" | Nombre: " +m.getNombre() +" | Especialidad: " +m.getEspecialidad());
        }
    }

    public static void modificarMedico(Hospital hospital, Scanner sc){
        String nombre, especialidad;
        Medico m = buscarMedico(hospital, sc);

        if(m != null){
            System.out.print("Ingrese nuevo nombre: ");
            nombre = sc.nextLine();
            m.setNombre(nombre);

            System.out.print("Ingrese nueva especialidad: ");
            especialidad = sc.nextLine();
            m.setEspecialidad(especialidad);

        }else{
            System.out.println("El medico no existe.");
        }
    }

    public static void eliminarMedico(Hospital hospital, Scanner sc){
        Medico m = buscarMedico(hospital, sc);

        if(m != null){
            hospital.getMedicos().remove(m);
        } else {
            System.out.println("El medico no existe.");
        }
    }
}

class OperacionesConsultas {

    public static void agregarConsulta(Hospital hospital, Scanner sc){
        int id;
        String fecha, motivo, sala;

        System.out.print("Ingrese ID de la consulta: ");
        id = sc.nextInt();
        sc.nextLine();

        System.out.print("Ingrese fecha de la consulta (DD-MM-YYYY): ");
        fecha = sc.nextLine();

        System.out.print("Ingrese motivo de la consulta: ");
        motivo = sc.nextLine();

        System.out.print("Ingrese sala: ");
        sala = sc.nextLine();

        Paciente paciente = OperacionesPacientes.buscarPaciente(hospital, sc);
        if(paciente == null){
            System.out.println("El paciente no existe.");
            return;
        }

        Medico medico = OperacionesMedicos.buscarMedico(hospital, sc);
        if(medico == null){
            System.out.println("El medico no existe.");
            return;
        }

        Consulta consulta = new Consulta(id, fecha, motivo, sala,paciente, medico);
        hospital.agregarConsulta(consulta);
    }

    public static Consulta buscarConsulta(Hospital hospital, Scanner sc){
        int id;

        System.out.print("Ingrese ID de la consulta: ");
        id = sc.nextInt();
        sc.nextLine();

        for(Consulta c : hospital.getConsultas()){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public static void printConsulta(Consulta c){
        if(c != null){
            System.out.println("CONSULTA:" +c.getId());
            System.out.println("Fecha: " +c.getFecha());
            System.out.println("Motivo: " +c.getMotivo());
            System.out.println("Sala: " +c.getSala());
            System.out.println("Paciente: " +c.getPaciente().getNombre() + " (RUT: " +c.getPaciente().getRut() + ")");
            System.out.println("Médico: " +c.getMedico().getNombre() + " (RUT: " +c.getMedico().getRut() + ")");
        }else{
            System.out.println("Consulta no encontrada.");
        }
    }

    public static void listarConsultas(Hospital hospital) {
        System.out.println("\n--- LISTA DE CONSULTAS ---");
        for(Consulta c : hospital.getConsultas()){
            System.out.println("ID: " +c.getId() +" | Paciente: " +c.getPaciente().getNombre() +" | Médico: " +c.getMedico().getNombre());
        }
    }

    public static void modificarConsulta(Hospital hospital, Scanner sc) {
        Consulta c = buscarConsulta(hospital, sc);

        if(c != null){
            System.out.print("Nueva fecha: ");
            String fecha = sc.nextLine();
            c.setFecha(fecha);

            System.out.print("Nuevo motivo: ");
            String motivo = sc.nextLine();
            c.setMotivo(motivo);

            System.out.print("Nueva sala: ");
            String sala = sc.nextLine();
            c.setSala(sala);

            System.out.println("Seleccionar nuevo paciente:");
            Paciente paciente = OperacionesPacientes.buscarPaciente(hospital, sc);
            if(paciente != null) {
                c.setPaciente(paciente);
            }

            System.out.println("Seleccionar nuevo médico:");
            Medico medico = OperacionesMedicos.buscarMedico(hospital, sc);
            if(medico != null) {
                c.setMedico(medico);
            }

        } else {
            System.out.println("La consulta no existe.");
        }
    }

    //La función de sobrecarga. la idea es que funciona como una especie de "reagendación"
    public static void modificarConsulta(Hospital hospital, int id, String fecha){
        for(Consulta c : hospital.getConsultas()){
            if(c.getId() == id){
                c.setFecha(fecha);
                System.out.println("Fecha de la consulta ID " +id +" actualizada a " +fecha);
                return;
            }
        }
        System.out.println("Consulta no encontrada.");
    }

    public static void opcionModificarConsulta(Hospital hospital, Scanner sc){
        int opcion, id;
        String fecha;
        System.out.print("¿Desea solo reagendar la fecha de la consulta? (1 = Sí, 0 = No): ");
        opcion = sc.nextInt();
        sc.nextLine();

        if(opcion == 1){
            System.out.print("Ingrese ID de la consulta: ");
            id = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese nueva fecha (DD-MM-YYYY): ");
            fecha = sc.nextLine();
            OperacionesConsultas.modificarConsulta(hospital, id, fecha);
        }else{
            OperacionesConsultas.modificarConsulta(hospital, sc);
        }
    }

    public static void eliminarConsulta(Hospital hospital, Scanner sc){
        Consulta c = buscarConsulta(hospital, sc);

        if(c != null){
            hospital.getConsultas().remove(c);
        } else {
            System.out.println("La consulta no existe.");
        }
    }
}

class Menu {
    public static void menuPacientes(Hospital hospital) {
        int opcion;
        Scanner sc = new Scanner(System.in);

        do {
            PrintMenu.printMenuPacientes();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    OperacionesPacientes.agregarPaciente(hospital, sc);
                    break;
                case 2:
                    OperacionesPacientes.eliminarPaciente(hospital, sc);
                    break;
                case 3:
                    OperacionesPacientes.listarPacientes(hospital);
                    break;
                case 4:
                    OperacionesPacientes.modificarPaciente(hospital, sc);
                    break;
                case 5:
                    OperacionesPacientes.printPaciente(hospital, sc);
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
        Scanner sc = new Scanner(System.in);

        do {
            PrintMenu.printMenuMedicos();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    OperacionesMedicos.agregarMedico(hospital, sc);
                    break;
                case 2:
                    OperacionesMedicos.eliminarMedico(hospital, sc);
                    break;
                case 3:
                    OperacionesMedicos.listarMedicos(hospital);
                    break;
                case 4:
                    OperacionesMedicos.modificarMedico(hospital, sc);
                    break;
                case 5:
                    OperacionesMedicos.printMedico(hospital, sc);
                    break;
                case 6:
                    OperacionesMedicos.printMedico(sc, hospital);
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

        do {
            PrintMenu.printMenuConsultas();
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    OperacionesConsultas.agregarConsulta(hospital, sc);
                    break;
                case 2:
                    OperacionesConsultas.eliminarConsulta(hospital, sc);
                    break;
                case 3:
                    OperacionesConsultas.listarConsultas(hospital);
                    break;
                case 4:
                    OperacionesConsultas.opcionModificarConsulta(hospital, sc);
                    break;
                case 5:
                    Consulta c = OperacionesConsultas.buscarConsulta(hospital, sc);
                    OperacionesConsultas.printConsulta(c);
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
            opcion = sc.nextInt();
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
