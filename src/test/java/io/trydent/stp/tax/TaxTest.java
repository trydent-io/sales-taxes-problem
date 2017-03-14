package io.trydent.stp.tax;

import io.trydent.stp.item.ExemptedItem;
import io.trydent.stp.item.Item;
import io.trydent.stp.item.Price;
import org.junit.Assert;
import org.junit.Test;

public class TaxTest {
  @Test
  public void plain() {
    final double value = 10.0;
    final Tax<Item> tax = new TaxImpl(value);
    final ExemptedItem item = ExemptedItem.of(Price.of(12.50));

    Assert.assertEquals(value, tax.rate(), 0.0);
    Assert.assertEquals(12.50, tax.apply(item), 0.0);
  }

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
