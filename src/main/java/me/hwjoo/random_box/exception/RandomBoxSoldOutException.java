package me.hwjoo.random_box.exception;

/**
 * 랜덤 박스가 품절된 경우 발생하는 예외
 */
public class RandomBoxSoldOutException extends RuntimeException {

    public RandomBoxSoldOutException(String message) {
        super(message);
    }

    public RandomBoxSoldOutException(String message, Throwable cause) {
        super(message, cause);
    }
} 