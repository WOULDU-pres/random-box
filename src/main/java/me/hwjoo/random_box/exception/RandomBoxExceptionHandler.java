package me.hwjoo.random_box.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 랜덤 박스 관련 예외를 처리하는 글로벌 예외 핸들러
 */
@ControllerAdvice
public class RandomBoxExceptionHandler {

    /**
     * RandomBoxNotFoundException 예외 처리
     *
     * @param ex 발생한 예외
     * @return 에러 응답
     */
    @ExceptionHandler(RandomBoxNotFoundException.class)
    public ResponseEntity<Object> handleRandomBoxNotFoundException(RandomBoxNotFoundException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * RandomBoxEventNotFoundException 예외 처리
     *
     * @param ex 발생한 예외
     * @return 에러 응답
     */
    @ExceptionHandler(RandomBoxEventNotFoundException.class)
    public ResponseEntity<Object> handleRandomBoxEventNotFoundException(RandomBoxEventNotFoundException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * RandomBoxSoldOutException 예외 처리
     *
     * @param ex 발생한 예외
     * @return 에러 응답
     */
    @ExceptionHandler(RandomBoxSoldOutException.class)
    public ResponseEntity<Object> handleRandomBoxSoldOutException(RandomBoxSoldOutException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * RandomBoxPurchaseNotFoundException 예외 처리
     *
     * @param ex 발생한 예외
     * @return 에러 응답
     */
    @ExceptionHandler(RandomBoxPurchaseNotFoundException.class)
    public ResponseEntity<Object> handleRandomBoxPurchaseNotFoundException(RandomBoxPurchaseNotFoundException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * IllegalArgumentException 예외 처리
     *
     * @param ex 발생한 예외
     * @return 에러 응답
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return createErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 기타 예외 처리
     *
     * @param ex 발생한 예외
     * @return 에러 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return createErrorResponse("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 에러 응답 생성
     *
     * @param message 에러 메시지
     * @param status HTTP 상태
     * @return 에러 응답
     */
    private ResponseEntity<Object> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
} 