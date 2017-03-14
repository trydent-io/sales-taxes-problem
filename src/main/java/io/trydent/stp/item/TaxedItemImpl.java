package io.trydent.stp.item;

final class TaxedItemImpl implements TaxedItem {
  private final Item item;

  TaxedItemImpl(final Item item) {
    this.item = item;
  }

  @Override
  public double price() {
    return this.item.price();
  }
}
