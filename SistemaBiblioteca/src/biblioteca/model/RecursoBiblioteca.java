package biblioteca.model;

import biblioteca.enums.EstadoRecurso;

/**
 * Clase Abstracta que representa un recurso genérico den la biblioteca.
 * Contiene atributos comunes como id, titulo y estado del recurso.
 **/
public abstract class RecursoBiblioteca {
	protected String id;
	protected String titulo;
	protected EstadoRecurso estado;
	
	
	/**
	 * Constructor base para inicializar un recurso.
	 * 
	 * @param id Identificador unico del recurso.
	 * @param titulo del recurso.
	 */
	public RecursoBiblioteca(String id, String titulo) {
		this.id = id;
		this.titulo = titulo;
		this.estado = EstadoRecurso.DISPONIBLE;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public EstadoRecurso getEstado() {
		return estado;
	}
	
	public void setEstado(EstadoRecurso estado) {
		this.estado = estado;
	}
	
	/**
	 * Metodo abstracto que deber ser implemetando por cada tipo de recurso.
	 * @return Una descripcion detallada del recurso
	 */
	public abstract String descripcion();
	
	
}
