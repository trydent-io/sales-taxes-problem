package io.trydent.stp.tax;

import io.trydent.stp.SalesTaxException;
import org.junit.Assert;
import org.junit.Test;

public class RateTest {
  @Test(expected = SalesTaxException.class)
  public void below() {
    final double value = -101;
    final Rate rate = Rate.of(value);
  }

  @Test(expected = SalesTaxException.class)
  public void beyond() {
    final double value = 101;
    final Rate rate = Rate.of(value);
  }

  @Test
  public void of() {
    final double value = 55;
    final Rate rate = Rate.of(value);

    Assert.assertEquals(value, rate.value(), 0);
  }
}