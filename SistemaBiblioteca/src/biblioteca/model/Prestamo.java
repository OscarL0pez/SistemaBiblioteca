package biblioteca.model;

import java.time.LocalDate;

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

    public String resumen() {
        return recurso.getTitulo() + " prestado a " + usuario.getNombre()
             + " el " + fechaPrestamo + (devuelto ? " ✅ (Devuelto)" : " ⏳ (Pendiente)");
    }
}
