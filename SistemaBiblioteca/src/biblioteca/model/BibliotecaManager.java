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
*/ 
public class BibliotecaManager implements Prestamista {
	
	private Map<String, RecursoBiblioteca> recursos;
	private Map<String, Usuario> usuarios;
	private List<Prestamo> historial;
	
	

	


	public BibliotecaManager() {
		this.recursos = new HashMap<>();
		this.usuarios = new HashMap<>();
		this.historial = new ArrayList<>();
	}
	
	public boolean agregarUsuario(Usuario usuario) {
		if (usuarios.containsKey(usuario.getId())) return false;
		usuarios.put(usuario.getId(), usuario);
		return true;
	}
	
	public boolean agregarRecurso(RecursoBiblioteca recurso) {
		if (recursos.containsKey(recurso.getId())) return false;
		recursos.put(recurso.getId(), recurso);
		return true;
	}
	
	public RecursoBiblioteca getRecurso(String id) {
		return recursos.get(id);
	}

	public Usuario getUsuario(String id) {
		return usuarios.get(id);
	}

	public Collection<RecursoBiblioteca> getRecursos() {
		return recursos.values();
	}
	
	@Override
	public boolean prestar(RecursoBiblioteca recurso, Usuario usuario) {
	    if (recurso == null || usuario == null) {
	        System.out.println("ERROR: Recurso o usuario no encontrado.");
	        return false;
	    }

	    if (recurso.getEstado() == EstadoRecurso.DISPONIBLE) {
	        recurso.setEstado(EstadoRecurso.PRESTADO);
	        historial.add(new Prestamo(recurso, usuario));  // <-- ESTA LÍNEA ES CLAVE
	        System.out.println("✅ Recurso prestado correctamente a " + usuario.getNombre());
	        return true;
	    } else {
	        System.out.println("❌ El recurso no está disponible (estado: " + recurso.getEstado() + ").");
	        return false;
	    }
	}

	
	@Override
	public boolean devolver(RecursoBiblioteca recurso) {
		if (recurso == null) {
			System.out.println("ERROR: Recurso no encontrado.");
			return false;
		}
		
		if (recurso.getEstado() == EstadoRecurso.PRESTADO) {
			recurso.setEstado(EstadoRecurso.DISPONIBLE);
			System.out.println("Recurso devuelto correctamente.");
			return true;
		} else {
			System.out.println("El recurso no estaba prestado (estado: " + recurso.getEstado() + ").");
			return false;
		}
	}
	public void guardarUsuariosEnArchivo(String nombreArchivo) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
	        for (Usuario u : usuarios.values()) {
	            writer.println(u.getId() + "," + u.getNombre());
	        }
	        System.out.println("💾 Usuarios guardados en " + nombreArchivo);
	    } catch (IOException e) {
	        System.out.println("❌ Error al guardar usuarios: " + e.getMessage());
	    }
	}

	public void cargarUsuariosDesdeArchivo(String nombreArchivo) {
	    File archivo = new File(nombreArchivo);
	    if (!archivo.exists()) return;

	    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
	        String linea;
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

	public void cargarRecursosDesdeArchivo(String nombreArchivo) {
	    File archivo = new File(nombreArchivo);
	    if (!archivo.exists()) return;

	    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
	        String linea;
	        while ((linea = reader.readLine()) != null) {
	            String[] partes = linea.split(",", 4);
	            if (partes.length < 4) continue;

	            String tipo = partes[0];
	            String id = partes[1];
	            String titulo = partes[2];
	            String extra = partes[3];

	            RecursoBiblioteca recurso = null;

	            switch (tipo.toUpperCase()) {
	                case "LIBRO":
	                    recurso = new Libro(id, titulo, extra);
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
	public void guardarRecursosEnArchivo(String nombreArchivo) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
	        for (RecursoBiblioteca r : recursos.values()) {
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
