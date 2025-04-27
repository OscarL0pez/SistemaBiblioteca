package biblioteca.model;

import java.time.LocalDate;
/**
 * Clase que representa un préstamo de un recurso de la biblioteca a un usuario.
 * Almacena la información relacionada con el préstamo como el recurso, el usuario,
 * la fecha en que se realizó y si ya fue devuelto.
 */
public class Prestamo {
    private RecursoBiblioteca recurso;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private boolean devuelto;

    public Prestamo(RecursoBiblioteca recurso, Usuario usuario) {
        this.recurso = recurso;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.devuelto = false;
    }

    public RecursoBiblioteca getRecurso() {
        return recurso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void marcarComoDevuelto() {
        this.devuelto = true;
    }
    /**
     * Genera un resumen textual del préstamo.
     * Incluye información del recurso, usuario, fecha y estado del préstamo.
     *
     * @return Cadena con el resumen del préstamo formateado para mostrar
     */
    public String resumen() {
        return recurso.getTitulo() + " prestado a " + usuario.getNombre()
             + " el " + fechaPrestamo + (devuelto ? " ✅ (Devuelto)" : " ⏳ (Pendiente)");
    }
}
