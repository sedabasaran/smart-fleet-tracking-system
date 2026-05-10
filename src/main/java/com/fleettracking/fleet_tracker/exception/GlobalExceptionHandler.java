package com.fleettracking.fleet_tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fleettracking.fleet_tracker.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(DeviceNotFoundException.class)
	    public ResponseEntity<ApiResponse<Void>> handleDeviceNotFound(DeviceNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(ApiResponse.error(ex.getMessage()));
	    }

	    @ExceptionHandler(LocationNotFoundException.class)
	    public ResponseEntity<ApiResponse<Void>> handleLocationNotFound(LocationNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(ApiResponse.error(ex.getMessage()));
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
	        String message = ex.getBindingResult().getFieldErrors()
	                .stream()
	                .map(e -> e.getField() + ": " + e.getDefaultMessage())
	                .findFirst()
	                .orElse("Validation hatası");
	        return ResponseEntity.badRequest().body(ApiResponse.error(message));
	    }

	    @ExceptionHandler(RuntimeException.class)
	    public ResponseEntity<ApiResponse<Void>> handleRuntime(RuntimeException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(ApiResponse.error(ex.getMessage()));
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(ApiResponse.error("Sunucu hatası: " + ex.getMessage()));
	    }

}
