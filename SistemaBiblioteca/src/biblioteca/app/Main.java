package biblioteca.app;

import java.util.Scanner;
import biblioteca.model.Libro;
import biblioteca.model.Revista;
import biblioteca.model.DVD;

import java.util.Collection;

import biblioteca.model.BibliotecaManager;
import biblioteca.model.RecursoBiblioteca;
import biblioteca.model.Usuario;

/**
 * Clase principal que implementa la interfaz de usuario del sistema de biblioteca.
 * Permite realizar operaciones de gestión de usuarios y recursos bibliográficos.
 */
public class Main {

    /**
     * Punto de entrada de la aplicación. Implementa un menú interactivo por consola
     * para gestionar la biblioteca.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Inicialización de componentes necesarios
        Scanner scanner = new Scanner(System.in);
        BibliotecaManager biblioteca = new BibliotecaManager();
        
        // Carga de datos almacenados previamente
        biblioteca.cargarUsuariosDesdeArchivo("usuarios.txt");
        biblioteca.cargarRecursosDesdeArchivo("recursos.txt");

        int opcion;

        // Bucle principal del programa
        do {
            // Muestra del menú principal
            System.out.println("\n=== Bienvenido al sistema de Biblioteca ===");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar usuario");
            System.out.println("6. Ver usuarios registrados");
            System.out.println("2. Agregar recurso");
            System.out.println("3. Prestar recurso");
            System.out.println("4. Devolver recurso");
            System.out.println("5. Lista de recursos");
            System.out.println("7. Ver historial de préstamos");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            // Validación de entrada: asegura que el usuario ingrese un número
            while (!scanner.hasNextInt()) {
                System.out.print("Ingrese un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpia el buffer después de leer un entero

            // Procesamiento de la opción seleccionada
            switch (opcion) {
                case 1:
                    // Agregar usuario: solicita datos y crea un nuevo usuario
                    System.out.print("ID del usuario: ");
                    String idUsuario = scanner.nextLine();
                    System.out.print("Nombre del usuario: ");
                    String nombreUsuario = scanner.nextLine();

                    Usuario nuevoUsuario = new Usuario(idUsuario, nombreUsuario);
                    if (biblioteca.agregarUsuario(nuevoUsuario)) {
                        System.out.println("✅ Usuario agregado correctamente.");
                        biblioteca.guardarUsuariosEnArchivo("usuarios.txt");
                    } else {
                        System.out.println("❌ Ya existe un usuario con ese ID.");
                    }
                    break;

                case 2:
                    // Agregar recurso: permite elegir entre libro, revista o DVD
                    System.out.println("Tipo de recurso a agregar:");
                    System.out.println("1. Libro");
                    System.out.println("2. Revista");
                    System.out.println("3. DVD");
                    System.out.print("Seleccione una opción: ");
                    int tipo = scanner.nextInt();
                    scanner.nextLine();

                    // Datos comunes para todos los tipos de recursos
                    System.out.print("ID del recurso: ");
                    String idRecurso = scanner.nextLine();
                    System.out.print("Título del recurso: ");
                    String tituloRecurso = scanner.nextLine();

                    RecursoBiblioteca nuevoRecurso = null;

                    // Creación del recurso según el tipo seleccionado
                    switch (tipo) {
                        case 1:
                            // Libro: requiere autor
                            System.out.print("Autor del libro: ");
                            String autor = scanner.nextLine();
                            nuevoRecurso = new Libro(idRecurso, tituloRecurso, autor);
                            break;
                        case 2:
                            // Revista: requiere número de edición
                            System.out.print("Número de edición: ");
                            int edicion = scanner.nextInt();
                            scanner.nextLine();
                            nuevoRecurso = new Revista(idRecurso, tituloRecurso, edicion);
                            break;
                        case 3:
                            // DVD: requiere duración
                            System.out.print("Duración en minutos: ");
                            int duracion = scanner.nextInt();
                            scanner.nextLine();
                            nuevoRecurso = new DVD(idRecurso, tituloRecurso, duracion);
                            break;
                        default:
                            System.out.println("❌ Opción no válida. No se creó el recurso.");
                            break;
                    }

                    // Si se creó correctamente el recurso, se agrega a la biblioteca
                    if (nuevoRecurso != null) {
                        if (biblioteca.agregarRecurso(nuevoRecurso)) {
                            System.out.println("✅ Recurso agregado correctamente.");
                            biblioteca.guardarRecursosEnArchivo("recursos.txt");
                        } else {
                            System.out.println("❌ Ya existe un recurso con ese ID.");
                        }
                    }
                    break;

                case 3:
                    // Prestar recurso: asocia un recurso con un usuario
                    System.out.print("ID del recurso a prestar: ");
                    String idRecursoPrestar = scanner.nextLine();
                    System.out.print("ID del usuario que presta: ");
                    String idUsuarioPrestar = scanner.nextLine();

                    // Obtiene las instancias necesarias y realiza el préstamo
                    RecursoBiblioteca recursoPrestar = biblioteca.getRecurso(idRecursoPrestar);
                    Usuario usuarioPrestar = biblioteca.getUsuario(idUsuarioPrestar);
                    biblioteca.prestar(recursoPrestar, usuarioPrestar);
                    break;

                case 4:
                    // Devolver recurso: cancela un préstamo existente
                    System.out.print("ID del recurso a devolver: ");
                    String idRecursoDevolver = scanner.nextLine();
                    RecursoBiblioteca recursoDevolver = biblioteca.getRecurso(idRecursoDevolver);
                    biblioteca.devolver(recursoDevolver);
                    break;

                case 5:
                    // Listar recursos: muestra todos los recursos disponibles con sus detalles
                    Collection<RecursoBiblioteca> recursos = biblioteca.getRecursos();
                    if (recursos.isEmpty()) {
                        System.out.println("📭 No hay recursos en la biblioteca.");
                    } else {
                        System.out.println("📚 Recursos en la biblioteca:");
                        System.out.printf("%-10s %-10s %-30s %-15s %-20s%n", "ID", "Tipo", "Título", "Estado", "Detalle");
                        System.out.println("------------------------------------------------------------------------------------------");

                        // Itera sobre cada recurso y muestra su información
                        for (RecursoBiblioteca r : recursos) {
                            String tipoRecurso = r.getClass().getSimpleName();
                            String estado = r.getEstado().toString();
                            String detalle = "";

                            // Obtiene detalles específicos según el tipo de recurso
                            if (r instanceof Libro) {
                                detalle = "Autor: " + ((Libro) r).getAutor();
                            } else if (r instanceof Revista) {
                                detalle = "Edición: " + ((Revista) r).getNumeroEdicion();
                            } else if (r instanceof DVD) {
                                detalle = "Duración: " + ((DVD) r).getDuracionMinutos() + " min";
                            }

                            // Imprime la información formateada
                            System.out.printf("%-10s %-10s %-30s %-15s %-20s%n",
                                    r.getId(), tipoRecurso, r.getTitulo(), estado, detalle);
                        }
                    }
                    break;

                case 6:
                    // Ver usuarios registrados
                    biblioteca.listarUsuarios();
                    break;

                case 0:
                    // Salir del programa
                    System.out.println("👋 Saliendo del sistema. ¡Hasta luego!");
                    break;
                    
                case 7:
                    // Ver historial de préstamos
                    biblioteca.verHistorialPrestamos();
                    break;

                default:
                    // Opción no reconocida
                    System.out.println("⚠️ Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0); // Continúa el bucle hasta que el usuario elija salir

        // Cierra el scanner para liberar recursos
        scanner.close();
    }
}
