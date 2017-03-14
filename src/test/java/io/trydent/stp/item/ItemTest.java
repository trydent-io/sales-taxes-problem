package io.trydent.stp.item;

import org.junit.Assert;
import org.junit.Test;

public class ItemTest {
  @Test
  public void exempted() throws Exception {
    final double price = 12.50;
    final Item item = ExemptedItem.of(Price.of(price));

    Assert.assertEquals(price, item.price(), 0);
  }

  @Test
  public void imported() throws Exception {
    final double price = 12.50;
    final Item item = ImportedItem.of(Price.of(price));

    Assert.assertEquals(price, item.price(), 0);
  }

  @Test
  public void taxed() throws Exception {
    final double price = 12.50;
    final Item item = TaxedItem.of(Price.of(price));

    Assert.assertEquals(price, item.price(), 0);
  }
}