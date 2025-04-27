package biblioteca.model;

/**
*Clase que representa un libro comor ecurso de la biblioteca.
*contiene el autor como atributo adicional
*/

public class Libro extends RecursoBiblioteca {
	private String autor;
	
	/**
	 * Constructor del libro.
	 * 
	 * @param id    Identificado unico del libro.
	 * @param titulo Titulo del libro.
	 */
	public Libro(String id, String titulo, String autor) {
		super(id, titulo);
		this.autor = autor;
	}
	
	public String getAutor() {
		return autor;
	}
	
	@Override
	public String descripcion() {
		return "LIBRO - ID: " + id + ", Título: " + titulo + ", Autor: " + autor + ", Estado: " + estado;
	}
}
