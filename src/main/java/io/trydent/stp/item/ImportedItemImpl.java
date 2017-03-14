package io.trydent.stp.item;

final class ImportedItemImpl implements ImportedItem {
  private final Item item;

  ImportedItemImpl(final Item item) {
    this.item = item;
  }

  @Override
  public double price() {
    return this.item.price();
  }
}
