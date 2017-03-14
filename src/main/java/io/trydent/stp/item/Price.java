package io.trydent.stp.item;

import io.trydent.stp.SalesTaxException;

import java.util.Optional;

public final class Price {
  public static final double MIN = 0.1d;
  public static final double MAX = 1_000_000_000d;

  private final double value;

  Price(final double value) {
    this.value = value;
  }

  public final double value() {
    return this.value;
  }

  public static Price of(final double price) {
    return Optional.of(price)
      .filter(p -> p >= MIN && p <= MAX)
      .map(Price::new)
      .orElseThrow(SalesTaxException::new);
  }
}
