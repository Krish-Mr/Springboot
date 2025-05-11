package com.bookmaster.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController to demonstrate exception handling in Spring Boot.
 */
@RestController
@RequestMapping("error")
public class ErrorFromServer {

    // Custom Exception
    static class NotFoundException extends Exception {
        public NotFoundException(String msg) {
            super(msg);
        }
    }

    /**
     * Custom error class to structure error response sent to the client.
     */
    static class ErrorMessage {
        private String errorMessage;
        private HttpStatus statusCode;
        private String additionalInfo;

        public ErrorMessage(String errorMessage, HttpStatus statusCode, String additionalInfo) {
            this.errorMessage = errorMessage;
            this.statusCode = statusCode;
            this.additionalInfo = additionalInfo;
        }

        // Getters and setters
        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        public String getAdditionalInfo() {
            return additionalInfo;
        }

        public void setAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
        }
    }

    /**
     * Exception handler for exceptions thrown within this class.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        ErrorMessage errorResponse = new ErrorMessage(
            e.getMessage(),
            HttpStatus.NOT_FOUND,
            "error get constructed in the handler method"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Endpoint to trigger a NotFoundException and it'll handle by itself
     */
    @GetMapping("throw-exception")
    public ResponseEntity<?> triggerNotFoundException() {
        try {
        	if(true)
        		throw new NotFoundException("Data not found in the requested resource");
		} catch (Exception e) {
			System.out.println(e);
			ErrorMessage err = new ErrorMessage( 
					e.getMessage(),
		            HttpStatus.NOT_FOUND,
		            "error get constructed in the controller class");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		}
		return ResponseEntity.ok().build();
    }

    /**
     * Example endpoint calling a service/repository layer that may throw an exception and handle by @ExceptionHandler
     * @throws NotFoundException 
     */
    @GetMapping("response-entity-exception")
    public ResponseEntity<List<?>> getErr() throws NotFoundException {
    	List<?> result = someExceptionThrowFromServiceRepository();
        return ResponseEntity.ok(result);
    }

    /**
     * Simulates a service/repository layer that throws a NotFoundException.
     */
    public List<?> someExceptionThrowFromServiceRepository() throws NotFoundException {
        throw new NotFoundException("Data not found in previous results");
    }
}
