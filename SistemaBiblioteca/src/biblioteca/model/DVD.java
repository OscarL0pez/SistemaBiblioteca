package biblioteca.model;

/**
 * Clase que representa un DVD como recurso de la biblioteca.
 * Contiene la duracion en minutos como atributo adicional.
 *  
 */

public class DVD extends RecursoBiblioteca {
	private int duracionMinutos;
	
	/**
	 *  Constructor del DVD.
	 *  
	 * @param id              Indentificador unico del DVD.
	 * @param titulo          Titulo del DVD.
	 * @param duracionMinutos Duracion del DVD en minutos.
	 */
	public DVD(String id, String titulo, int duracionMinutos) {
		super(id, titulo);
		this.duracionMinutos = duracionMinutos;
	}
	
	public int getDuracionMinutos() {
		return duracionMinutos;
	}
	
	@Override
	public String descripcion() {
		  return "DVD - ID: " + id + ", Título: " + titulo + ", Duración: " + duracionMinutos +
	               " min, Estado: " + estado;
	}
}

