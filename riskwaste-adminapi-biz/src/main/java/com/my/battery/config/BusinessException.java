package com.my.battery.config;

import lombok.NoArgsConstructor;

/**
 * 业务异常
 * 
 * @author weibocy
 *
 */
@NoArgsConstructor
public class BusinessException extends Exception {
    private static final long serialVersionUID = 8504228682730875772L;

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BusinessException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
