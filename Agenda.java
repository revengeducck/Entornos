import java.io.*;
import java.util.*;
import java.util.Scanner;

class Contacto implements Serializable {
    String nombre;
    String telefono;

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }
}

class Agenda {
    List<Contacto> contactos;

    public Agenda() {
        contactos = new ArrayList<>();
    }

    public void agregarContacto(String nombre, String telefono) {
        contactos.add(new Contacto(nombre, telefono));
    }

    public void eliminarContacto(String nombre) {
        contactos.removeIf(contacto -> contacto.nombre.equals(nombre));
    }

    public void mostrarContactos() {
        for (Contacto contacto : contactos) {
            System.out.println("Nombre: " + contacto.nombre + ", Teléfono: " + contacto.telefono);
        }
    }
}

class AgendaArchivo {
    public static void guardarAgenda(Agenda agenda, String archivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(agenda);
        }
    }

    public static Agenda cargarAgenda(String archivo) throws IOException, ClassNotFoundException {
        File file = new File(archivo);
    
        if (!file.exists()) {
            return new Agenda();  // Crea una nueva Agenda si el archivo no existe
        }
    
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (Agenda) ois.readObject();
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Agenda agenda = AgendaArchivo.cargarAgenda("agenda.dat");

        while (true) {
            System.out.println("1. Agregar contacto");
            System.out.println("2. Eliminar contacto");
            System.out.println("3. Mostrar contactos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (opcion == 1) {
                System.out.print("Ingrese el nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Ingrese el teléfono: ");
                String telefono = scanner.nextLine();
                agenda.agregarContacto(nombre, telefono);
            } else if (opcion == 2) {
                System.out.print("Ingrese el nombre del contacto a eliminar: ");
                String nombre = scanner.nextLine();
                agenda.eliminarContacto(nombre);
            } else if (opcion == 3) {
                agenda.mostrarContactos();
            } else if (opcion == 4) {
                break;
            }

            AgendaArchivo.guardarAgenda(agenda, "agenda.dat");
        }

        scanner.close();
    }
}