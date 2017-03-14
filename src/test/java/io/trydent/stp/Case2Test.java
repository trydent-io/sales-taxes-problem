package io.trydent.stp;

import io.trydent.stp.item.ImportedItem;
import io.trydent.stp.item.Price;
import io.trydent.stp.item.TaxedItem;
import io.trydent.stp.math.DecimalOperation;
import io.trydent.stp.math.Round;
import io.trydent.stp.tax.ImportTax;
import io.trydent.stp.tax.Rate;
import io.trydent.stp.tax.SaleTax;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static io.trydent.stp.item.ImportedItem.fromTaxed;
import static io.trydent.stp.item.TaxedItem.of;
import static io.trydent.stp.math.DecimalOperation.addWith;
import static java.math.RoundingMode.HALF_UP;

public class Case2Test {
  private static final Logger log = LoggerFactory.getLogger(Case2Test.class);
  private static final String INPUT = "\nInput 2:\n" +
    "1 imported box of chocolates at %1$,.2f\n" +
    "1 imported bottle of perfume at %2$,.2f\n";
  private static final String OUTPUT = "\nOutput 2:\n" +
    "1 imported box of chocolates: %1$,.2f\n" +
    "1 imported bottle of perfume: %2$,.2f\n" +
    "Sales Taxes: %3$,.2f\n" +
    "Total: %4$,.2f";

  @Test
  public void apply() {
    final ImportedItem chocolate = ImportedItem.of(Price.of(10));
    final TaxedItem perfume = of(Price.of(47.50));

    final SaleTax saleTax = SaleTax.of(Rate.of(10));
    final ImportTax importTax = ImportTax.of(Rate.of(5));

    final Round nearest = Round.nearest();
    final double perfumeTax = nearest.apply(saleTax.apply(perfume));
    final double chocolateDuty = nearest.apply(importTax.apply(chocolate));
    final double perfumeDuty = nearest.apply(importTax.apply(fromTaxed(perfume)));

    final DecimalOperation add = addWith(Round.with(HALF_UP));
    final double totalTaxes = add.apply(chocolateDuty, perfumeDuty, perfumeTax);

    final double taxedChocolate = add.apply(chocolate.price(), chocolateDuty);
    final double taxedPerfume = add.apply(perfume.price(), perfumeDuty, perfumeTax);

    final double total = add.apply(taxedChocolate, taxedPerfume);

    log.info(String.format(Locale.ITALY, INPUT, chocolate.price(), perfume.price()));
    log.info(String.format(Locale.ITALY, OUTPUT, taxedChocolate, taxedPerfume, totalTaxes, total));

    Assert.assertEquals(65.15, total, 0);
    Assert.assertEquals(7.65, totalTaxes, 0);
  }
}
