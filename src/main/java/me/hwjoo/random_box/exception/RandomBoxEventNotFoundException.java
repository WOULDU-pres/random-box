package me.hwjoo.random_box.exception;

/**
 * 랜덤 박스 이벤트를 찾을 수 없는 경우 발생하는 예외
 */
public class RandomBoxEventNotFoundException extends RuntimeException {

    public RandomBoxEventNotFoundException(String message) {
        super(message);
    }

    public RandomBoxEventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 