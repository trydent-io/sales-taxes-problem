package io.trydent.stp.item;

public interface TaxedItem extends Item {
  static TaxedItem of(final Price price) {
    return new TaxedItemImpl(new ItemImpl(price.value()));
  }

  static <I extends ImportedItem & Item> TaxedItem fromImported(final I item) {
    return new TaxedItemImpl(item);
  }
}
