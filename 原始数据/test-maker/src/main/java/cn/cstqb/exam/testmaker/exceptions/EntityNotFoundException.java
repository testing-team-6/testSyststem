package cn.cstqb.exam.testmaker.exceptions;

import javassist.NotFoundException;

import javax.management.InstanceAlreadyExistsException;
import javax.persistence.NoResultException;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/2/11
 * Time: 21:00
 */
public class EntityNotFoundException extends EntityException {
    public EntityNotFoundException(String format, Object... args) {
        super(format, args);
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public EntityNotFoundException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
