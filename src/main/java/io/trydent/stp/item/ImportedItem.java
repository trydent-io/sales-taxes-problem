package io.trydent.stp.item;

public interface ImportedItem extends Item {
  static ImportedItem of(final Price price) {
    return new ImportedItemImpl(new ItemImpl(price.value()));
  }

  static <I extends TaxedItem & Item> ImportedItem fromTaxed(final I item) {
    return new ImportedItemImpl(item);
  }
}
