package biblioteca.model;

import biblioteca.enums.EstadoRecurso;
import biblioteca.interfaces.Prestamista;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Clase gestora de la biblioteca.
 * Implementa la lógica de préstamo y devolución de recursos,
 * así como el almacenamiento de recursos y usuarios.
 * 
 * Esta clase centraliza todas las operaciones principales de la biblioteca:
 * - Gestión de usuarios (agregar, listar, guardar, cargar)
 * - Gestión de recursos (agregar, obtener, guardar, cargar)
 * - Préstamo y devolución de recursos
 * - Gestión del historial de préstamos
 */ 
public class BibliotecaManager implements Prestamista {
	
	// Colecciones para almacenar los datos principales
	/** Mapa de recursos indexados por su ID */
	private Map<String, RecursoBiblioteca> recursos;
	
	/** Mapa de usuarios indexados por su ID */
	private Map<String, Usuario> usuarios;
	
	/** Historial cronológico de préstamos realizados */
	private List<Prestamo> historial;
	
	/**
	 * Constructor por defecto.
	 * Inicializa las estructuras de datos vacías para recursos, usuarios e historial.
	 */
	public BibliotecaManager() {
		this.recursos = new HashMap<>();
		this.usuarios = new HashMap<>();
		this.historial = new ArrayList<>();
	}
	
	/**
	 * Agrega un nuevo usuario al sistema.
	 * 
	 * @param usuario El usuario a agregar
	 * @return true si se agregó correctamente, false si ya existía un usuario con el mismo ID
	 */
	public boolean agregarUsuario(Usuario usuario) {
		if (usuarios.containsKey(usuario.getId())) return false;
		usuarios.put(usuario.getId(), usuario);
		return true;
	}
	
	/**
	 * Agrega un nuevo recurso a la biblioteca.
	 * 
	 * @param recurso El recurso a agregar
	 * @return true si se agregó correctamente, false si ya existía un recurso con el mismo ID
	 */
	public boolean agregarRecurso(RecursoBiblioteca recurso) {
		if (recursos.containsKey(recurso.getId())) return false;
		recursos.put(recurso.getId(), recurso);
		return true;
	}
	
	/**
	 * Obtiene un recurso por su ID.
	 * 
	 * @param id El identificador del recurso
	 * @return El recurso encontrado o null si no existe
	 */
	public RecursoBiblioteca getRecurso(String id) {
		return recursos.get(id);
	}

	/**
	 * Obtiene un usuario por su ID.
	 * 
	 * @param id El identificador del usuario
	 * @return El usuario encontrado o null si no existe
	 */
	public Usuario getUsuario(String id) {
		return usuarios.get(id);
	}

	/**
	 * Obtiene todos los recursos de la biblioteca.
	 * 
	 * @return Colección con todos los recursos disponibles
	 */
	public Collection<RecursoBiblioteca> getRecursos() {
		return recursos.values();
	}
	
	/**
	 * Implementación del método de préstamo de recursos.
	 * Verifica disponibilidad y registra el préstamo en el historial.
	 * 
	 * @param recurso El recurso a prestar
	 * @param usuario El usuario que solicita el préstamo
	 * @return true si el préstamo fue exitoso, false en caso contrario
	 */
	@Override
	public boolean prestar(RecursoBiblioteca recurso, Usuario usuario) {
	    // Validación de parámetros
	    if (recurso == null || usuario == null) {
	        System.out.println("ERROR: Recurso o usuario no encontrado.");
	        return false;
	    }

	    // Verificamos disponibilidad del recurso
	    if (recurso.getEstado() == EstadoRecurso.DISPONIBLE) {
	        // Actualizamos estado y registramos el préstamo
	        recurso.setEstado(EstadoRecurso.PRESTADO);
	        historial.add(new Prestamo(recurso, usuario));  
	        System.out.println("✅ Recurso prestado correctamente a " + usuario.getNombre());
	        return true;
	    } else {
	        System.out.println("❌ El recurso no está disponible (estado: " + recurso.getEstado() + ").");
	        return false;
	    }
	}

	/**
	 * Implementación del método de devolución de recursos.
	 * Actualiza el estado del recurso a DISPONIBLE.
	 * 
	 * @param recurso El recurso a devolver
	 * @return true si la devolución fue exitosa, false en caso contrario
	 */
	@Override
	public boolean devolver(RecursoBiblioteca recurso) {
		// Validación de parámetros
		if (recurso == null) {
			System.out.println("ERROR: Recurso no encontrado.");
			return false;
		}
		
		// Verificamos que el recurso esté efectivamente prestado
		if (recurso.getEstado() == EstadoRecurso.PRESTADO) {
			recurso.setEstado(EstadoRecurso.DISPONIBLE);
			System.out.println("Recurso devuelto correctamente.");
			return true;
		} else {
			System.out.println("El recurso no estaba prestado (estado: " + recurso.getEstado() + ").");
			return false;
		}
	}
	
	/**
	 * Guarda la información de usuarios en un archivo de texto.
	 * Formato: ID,Nombre
	 * 
	 * @param nombreArchivo Ruta del archivo donde guardar los datos
	 */
	public void guardarUsuariosEnArchivo(String nombreArchivo) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
	        // Iteramos sobre todos los usuarios y los escribimos línea a línea
	        for (Usuario u : usuarios.values()) {
	            writer.println(u.getId() + "," + u.getNombre());
	        }
	        System.out.println("💾 Usuarios guardados en " + nombreArchivo);
	    } catch (IOException e) {
	        System.out.println("❌ Error al guardar usuarios: " + e.getMessage());
	    }
	}

	/**
	 * Carga los datos de usuarios desde un archivo de texto.
	 * Espera formato: ID,Nombre
	 * 
	 * @param nombreArchivo Ruta del archivo de donde cargar los datos
	 */
	public void cargarUsuariosDesdeArchivo(String nombreArchivo) {
	    File archivo = new File(nombreArchivo);
	    if (!archivo.exists()) return; // El archivo no existe, no hacemos nada

	    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
	        String linea;
	        // Leemos línea por línea y creamos usuarios
	        while ((linea = reader.readLine()) != null) {
	            String[] partes = linea.split(",", 2);
	            if (partes.length == 2) {
	                String id = partes[0];
	                String nombre = partes[1];
	                usuarios.put(id, new Usuario(id, nombre));
	            }
	        }
	        System.out.println("📂 Usuarios cargados desde " + nombreArchivo);
	    } catch (IOException e) {
	        System.out.println("❌ Error al cargar usuarios: " + e.getMessage());
	    }
	}
	
	/**
	 * Muestra en consola la lista de usuarios registrados.
	 */
	public void listarUsuarios() {
	    if (usuarios.isEmpty()) {
	        System.out.println("📭 No hay usuarios registrados.");
	        return;
	    }

	    System.out.println("👥 Lista de usuarios registrados:");
	    for (Usuario u : usuarios.values()) {
	        System.out.println("- ID: " + u.getId() + " | Nombre: " + u.getNombre());
	    }
	}

	/**
	 * Carga los recursos desde un archivo de texto.
	 * Formato esperado: TIPO,ID,TITULO,EXTRA
	 * donde TIPO puede ser LIBRO, REVISTA o DVD
	 * 
	 * @param nombreArchivo Ruta del archivo de donde cargar los datos
	 */
	public void cargarRecursosDesdeArchivo(String nombreArchivo) {
	    File archivo = new File(nombreArchivo);
	    if (!archivo.exists()) return; // El archivo no existe, no hacemos nada

	    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
	        String linea;
	        while ((linea = reader.readLine()) != null) {
	            String[] partes = linea.split(",", 4);
	            if (partes.length < 4) continue; // Línea inválida, saltamos

	            // Extraemos los componentes comunes
	            String tipo = partes[0];
	            String id = partes[1];
	            String titulo = partes[2];
	            String extra = partes[3];

	            RecursoBiblioteca recurso = null;

	            // Creamos el tipo de recurso según lo especificado
	            switch (tipo.toUpperCase()) {
	                case "LIBRO":
	                    recurso = new Libro(id, titulo, extra); // extra es el autor
	                    break;
	                case "REVISTA":
	                    int numeroEdicion = Integer.parseInt(extra);
	                    recurso = new Revista(id, titulo, numeroEdicion);
	                    break;
	                case "DVD":
	                    int duracion = Integer.parseInt(extra);
	                    recurso = new DVD(id, titulo, duracion);
	                    break;
	            }

	            // Si se creó correctamente, lo agregamos a la colección
	            if (recurso != null) {
	                recursos.put(id, recurso);
	            }
	        }

	        System.out.println("📂 Recursos cargados desde " + nombreArchivo);
	    } catch (IOException e) {
	        System.out.println("❌ Error al cargar recursos: " + e.getMessage());
	    } catch (NumberFormatException e) {
	        System.out.println("❌ Error al interpretar número en revista o DVD: " + e.getMessage());
	    }
	}
	
	/**
	 * Guarda los recursos en un archivo de texto.
	 * Formato: TIPO,ID,TITULO,EXTRA
	 * 
	 * @param nombreArchivo Ruta del archivo donde guardar los datos
	 */
	public void guardarRecursosEnArchivo(String nombreArchivo) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
	        // Iteramos por todos los recursos
	        for (RecursoBiblioteca r : recursos.values()) {
	            // Dependiendo del tipo, guardamos diferente información
	            if (r instanceof Libro) {
	                Libro libro = (Libro) r;
	                writer.println("LIBRO," + libro.getId() + "," + libro.getTitulo() + "," + libro.getAutor());
	            } else if (r instanceof Revista) {
	                Revista revista = (Revista) r;
	                writer.println("REVISTA," + revista.getId() + "," + revista.getTitulo() + "," + revista.getNumeroEdicion());
	            } else if (r instanceof DVD) {
	                DVD dvd = (DVD) r;
	                writer.println("DVD," + dvd.getId() + "," + dvd.getTitulo() + "," + dvd.getDuracionMinutos());
	            }
	        }
	        System.out.println("💾 Recursos guardados en " + nombreArchivo);
	    } catch (IOException e) {
	        System.out.println("❌ Error al guardar recursos: " + e.getMessage());
	    }
	}
	
	/**
	 * Muestra en consola el historial de préstamos realizados.
	 */
	public void verHistorialPrestamos() {
	    if (historial.isEmpty()) {
	        System.out.println("📭 No hay préstamos registrados.");
	    } else {
	        System.out.println("📚 Historial de préstamos:");
	        for (Prestamo p : historial) {
	            System.out.println("- " + p.resumen());
	        }
	    }
	}
}
