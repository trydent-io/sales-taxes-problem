package io.trydent.stp;

public final class SalesTaxException extends RuntimeException {
  public SalesTaxException() {
  }

  public SalesTaxException(String message) {
    super(message);
  }

  public SalesTaxException(String message, Throwable cause) {
    super(message, cause);
  }

  public SalesTaxException(Throwable cause) {
    super(cause);
  }

  public SalesTaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
