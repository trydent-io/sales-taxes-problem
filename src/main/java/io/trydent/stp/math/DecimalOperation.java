package io.trydent.stp.math;

import java.math.BigDecimal;

public interface DecimalOperation {
  static DecimalOperation addWith(final Round round) {
    return new DecimalOperationImpl(round, BigDecimal::add);
  }

  double apply(double first, double... rest);
}
