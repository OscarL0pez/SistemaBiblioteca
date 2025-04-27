package biblioteca.interfaces;

import biblioteca.model.RecursoBiblioteca;
import biblioteca.model.Usuario;

/**
*Esta interfaz define el comportamiento que deber tener cualquier sistema
*que permita prestar y devolver recursos bibliotecarios.
*/

public interface Prestamista {
	
	/**
	 * intenta prestar un recurso a un usuario.
	 * 
	 * @param recurso El recurso a prestar.
	 * @param usuario El usuario que solicita el préstamo.
	 */
	boolean prestar(RecursoBiblioteca recurso, Usuario usuario);
	
	/**
	 * Intenta devolver un recurso prestado.
	 * 
	 * @param recurso El recurso a devolver.
	 * @return ture si la devolción se realiza con éxito, false si hubo un problema
	 */
	boolean devolver(RecursoBiblioteca recurso);
}
