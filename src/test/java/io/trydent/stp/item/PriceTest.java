package io.trydent.stp.item;

import io.trydent.stp.SalesTaxException;
import org.junit.Assert;
import org.junit.Test;

public class PriceTest {
  @Test
  public void valid() {
    final double value = 12.50;
    final Price price = Price.of(value);

    Assert.assertEquals(value, price.value(), 0);
  }

  @Test(expected = SalesTaxException.class)
  public void negative() {
    final double value = -12.50;
    final Price price = Price.of(value);
  }

  @Test(expected = SalesTaxException.class)
  public void beyond() {
    final double value = 2_000_000_000;
    final Price price = Price.of(value);
  }
}
