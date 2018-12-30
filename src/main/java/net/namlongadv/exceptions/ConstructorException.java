package net.namlongadv.exceptions;

/**
 *
 * @author ToanNgo
 */
public class ConstructorException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4853082059863031777L;

	public ConstructorException() {
        super("Suppress default constructor for noninstantiability");
    }
}
