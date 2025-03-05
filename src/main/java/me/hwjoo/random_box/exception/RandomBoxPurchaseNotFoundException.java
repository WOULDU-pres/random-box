package me.hwjoo.random_box.exception;

/**
 * 랜덤 박스 구매 내역을 찾을 수 없는 경우 발생하는 예외
 */
public class RandomBoxPurchaseNotFoundException extends RuntimeException {

    public RandomBoxPurchaseNotFoundException(String message) {
        super(message);
    }

    public RandomBoxPurchaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 