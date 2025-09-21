package lk.ijse.gdse.springboot.back_end.exception;

public class AuthServiceException extends RuntimeException {

  public AuthServiceException() {
    super();
  }

  public AuthServiceException(String message) {
    super(message);
  }

  public AuthServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthServiceException(Throwable cause) {
    super(cause);
  }
}
