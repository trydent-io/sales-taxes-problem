package io.trydent.stp.math;

import org.junit.Assert;
import org.junit.Test;

import static java.math.RoundingMode.HALF_UP;

public class RoundImplTest {
  @Test
  public void apply1() throws Exception {
    final double value = 12.337d;
    final double rouned = Round.with(HALF_UP).apply(value);

    Assert.assertEquals(12.34d, rouned, 0d);
  }

  @Test
  public void apply2() throws Exception {
    final double value = 12.333d;
    final double rouned = Round.with(HALF_UP).apply(value);

    Assert.assertEquals(12.33d, rouned, 0d);
  }
}