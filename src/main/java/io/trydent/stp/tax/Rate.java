package io.trydent.stp.tax;

import io.trydent.stp.SalesTaxException;

import java.util.Optional;

public final class Rate {
  public static final double MIN = 1d;
  public static final double MAX = 100d;

  private final double value;

  private Rate(double value) {
    this.value = value;
  }

  public final double value() { return this.value; }

  public static Rate of(final double rate) {
    return Optional.of(rate)
      .filter(r -> r >= MIN && r <= MAX)
      .map(Rate::new)
      .orElseThrow(SalesTaxException::new);
  }
}
