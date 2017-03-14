package io.trydent.stp.tax;

import org.junit.Assert;
import org.junit.Test;

public class TaxTest {
  @Test
  public void imported() {
    final double value = 5;
    final ImportTax tax = ImportTax.of(Rate.of(value));

    Assert.assertEquals(value, tax.rate(), 0);
  }

  @Test
  public void sale() {
    final double value = 10;
    final SaleTax tax = SaleTax.of(Rate.of(value));

    Assert.assertEquals(value, tax.rate(), 0);
  }
}
