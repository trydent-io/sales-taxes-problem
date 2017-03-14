package io.trydent.stp.math;

import java.math.BigDecimal;

public interface DecimalOperation {
  static DecimalOperation addWith(final Round round) {
    return new DecimalOperationImpl(round, BigDecimal::add);
  }

  static DecimalOperation subtractWith(final Round round) {
    return new DecimalOperationImpl(round, BigDecimal::subtract);
  }

  double apply(double first, double... rest);
}
