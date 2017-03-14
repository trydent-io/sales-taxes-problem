package io.trydent.stp.item;

final class ExemptedItemImpl implements ExemptedItem {
  private final Item item;

  ExemptedItemImpl(Item item) {
    this.item = item;
  }

  @Override
  public double price() {
    return item.price();
  }
}
