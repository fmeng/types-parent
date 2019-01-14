package me.fmeng.types.exception;

/**
 * 参数不合法
 *
 * @author fmeng
 * @since 2018/07/19
 */
public class IllegalParamOfTypesException extends RuntimeException {

    private static final long serialVersionUID = 2441937782040203006L;

    public IllegalParamOfTypesException(String message) {
        super(message, null, false, false);
    }

    public IllegalParamOfTypesException(Throwable throwable, String message) {
        super(message, throwable, false, false);
    }
}
