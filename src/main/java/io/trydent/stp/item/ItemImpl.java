package io.trydent.stp.item;

final class ItemImpl implements Item {
  private final double price;

  ItemImpl(double price) {
    this.price = price;
  }

  @Override
  public final double price() {
    return this.price;
  }
}
