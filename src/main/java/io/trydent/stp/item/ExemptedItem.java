package io.trydent.stp.item;

public interface ExemptedItem extends Item {
  static ExemptedItem of(final Price price) {
    return new ExemptedItemImpl(new ItemImpl(price.value()));
  }

}
