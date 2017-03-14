package io.trydent.stp.tax;

import io.trydent.stp.item.Item;

final class TaxImpl implements Tax<Item> {
  private final double rate;

  TaxImpl(final double rate) {
    this.rate = rate;
  }

  @Override
  public double rate() {
    return this.rate;
  }

  @Override
  public double apply(Item item) {
    return item.price();
  }
}
