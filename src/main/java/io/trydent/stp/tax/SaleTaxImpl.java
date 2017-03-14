package io.trydent.stp.tax;

final class SaleTaxImpl implements SaleTax {
  private final Tax<?> tax;

  SaleTaxImpl(final Tax<?> tax) {
    this.tax = tax;
  }

  @Override
  public double rate() {
    return this.tax.rate();
  }
}
