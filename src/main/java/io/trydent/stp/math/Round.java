package io.trydent.stp.math;

import java.math.RoundingMode;
import java.util.function.Function;

import static java.lang.Math.*;

public interface Round extends Function<Double, Double> {
  static Round with(final RoundingMode rule) {
    return new RoundImpl(rule);
  }

  static Round nearest() {
    return value -> (ceil(value * 20) / 20.0);
  }
}
