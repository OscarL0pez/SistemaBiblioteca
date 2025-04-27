package biblioteca.model;

/**
 *  Clase que representa un usuario de la biblioteca.
 *  Cada usuario tiene un id y un nombre.
 */
public class Usuario {
	private String id;
	private String nombre;
	
	/**
	 *  Constructor del usuario.
	 *  
	 *  @param id     Identificador unico del usuario.
	 *  @param nombre Nombre del usuario.
	 */
	public Usuario(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	
	
	

}
