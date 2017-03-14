package io.trydent.stp.math;

import io.trydent.stp.SalesTaxException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

final class RoundImpl implements Round {
  private static final int scale = 2;
  private final RoundingMode rule;

  RoundImpl(final RoundingMode rule) {
    this.rule = rule;
  }

  @Override
  public Double apply(final Double value) {
    return Optional.of(new BigDecimal(value)
      .setScale(scale, this.rule)
      .doubleValue())
      .orElseThrow(SalesTaxException::new);
  }
}
