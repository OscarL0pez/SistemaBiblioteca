package biblioteca.model;

/**
*Clase que representa una revista como recurso de la biblioteca.
* Contiene el numero de edicion como atributo adicional.
*/

public class Revista extends RecursoBiblioteca {
	private int numeroEdicion;
	
	/**
	 * Constructor de la revista.
	 * 
	 * @param id         Identificador unico de la revista.
	 * @param titulo     Titulo de la revista.
	 * @param numeroEdicion Número de edición de la revista
	 */
	public Revista(String id, String titulo, int numeroEdicion) {
		super(id, titulo);
		this.numeroEdicion = numeroEdicion;
	}
	
	public int getNumeroEdicion() {
		return numeroEdicion;
	}
	
	@Override
	public String descripcion() {
		 return "REVISTA - ID: " + id + ", Título: " + titulo + ", Nº Edición: " + numeroEdicion +
	               ", Estado: " + estado;
	}
}
