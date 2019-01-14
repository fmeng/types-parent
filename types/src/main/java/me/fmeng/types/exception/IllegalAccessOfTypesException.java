package me.fmeng.types.exception;

/**
 * 不合法的实现
 *
 * @author fmeng
 * @since 2019/01/12
 */
public class IllegalAccessOfTypesException extends RuntimeException {

    private static final long serialVersionUID = -502871589841314796L;

    public IllegalAccessOfTypesException(String message) {
        super(message, null, false, false);
    }

    public IllegalAccessOfTypesException(Throwable throwable, String message) {
        super(message, throwable, false, false);
    }
}
