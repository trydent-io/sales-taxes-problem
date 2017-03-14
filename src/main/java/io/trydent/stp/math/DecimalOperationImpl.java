package io.trydent.stp.math;

import io.trydent.stp.SalesTaxException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import static java.util.Collections.singleton;

final class DecimalOperationImpl implements DecimalOperation {
  private final Round round;
  private final BinaryOperator<BigDecimal> withOperation;

  DecimalOperationImpl(Round round, BinaryOperator<BigDecimal> withOperation) {
    this.round = round;
    this.withOperation = withOperation;
  }

  @Override
  public double apply(double first, double... rest) {
    final List<Double> doubles = new ArrayList<>(singleton(first));

    for (double r : rest) doubles.add(r);

    return doubles.stream()
      .map(BigDecimal::new)
      .reduce(withOperation)
      .map(BigDecimal::doubleValue)
      .map(round)
      .orElseThrow(SalesTaxException::new);
  }
}
