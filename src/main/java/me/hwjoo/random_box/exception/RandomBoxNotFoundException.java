package me.hwjoo.random_box.exception;

/**
 * 랜덤 박스를 찾을 수 없는 경우 발생하는 예외
 */
public class RandomBoxNotFoundException extends RuntimeException {

    public RandomBoxNotFoundException(String message) {
        super(message);
    }

    public RandomBoxNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 