package raisetech.Student.management.exception;

public class TestException extends Exception {

  public TestException() {
    super();
  }

  public TestException(String message) {
    super(message);
  }

  public TestException(String message, Throwable cause) {
    super(message, cause);
  }

  public TestException(Throwable cause) {
    super(cause);
  }
}
