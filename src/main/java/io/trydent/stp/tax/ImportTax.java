package io.trydent.stp.tax;

import io.trydent.stp.item.ImportedItem;

public interface ImportTax extends Tax<ImportedItem> {
  static ImportTax of(final Rate rate) {
    return new ImportTaxImpl(new TaxImpl(rate.value()));
  }
}
