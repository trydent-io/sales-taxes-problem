package io.trydent.stp.tax;

import io.trydent.stp.item.TaxedItem;

public interface SaleTax extends Tax<TaxedItem> {
  static SaleTax of(final Rate rate) {
    return new SaleTaxImpl(new TaxImpl(rate.value()));
  }
}
