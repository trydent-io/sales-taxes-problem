package io.trydent.stp.tax;

import io.trydent.stp.SalesTaxException;
import io.trydent.stp.item.Item;

import java.util.Optional;

public interface Tax<I extends Item> {
  double rate();

  default double apply(I item) {
    return Optional.ofNullable(item)
      .map(Item::price)
      .map(p -> (p / 100 * this.rate()))
      .orElseThrow(() -> new SalesTaxException("Can't compute tax on null-Item."));
  }
}
