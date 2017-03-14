package io.trydent.stp.tax;

final class ImportTaxImpl implements ImportTax {
  private final Tax<?> tax;

  ImportTaxImpl(final Tax<?> tax) {
    this.tax = tax;
  }

  @Override
  public double rate() {
    return this.tax.rate();
  }
}
