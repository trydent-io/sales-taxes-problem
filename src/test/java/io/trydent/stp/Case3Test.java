package io.trydent.stp;

import io.trydent.stp.item.ExemptedItem;
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

import static io.trydent.stp.item.TaxedItem.fromImported;
import static io.trydent.stp.item.TaxedItem.of;
import static io.trydent.stp.math.DecimalOperation.addWith;
import static java.math.RoundingMode.HALF_UP;

public class Case3Test {
  private static final Logger log = LoggerFactory.getLogger(Case3Test.class);
  private static final String INPUT = "\nInput 3:\n" +
    "1 imported bottle of perfume at %1$,.2f\n" +
    "1 bottle of perfume at %2$,.2f\n" +
    "1 packet of headache pills at %3$,.2f\n" +
    "1 box of imported chocolates at %4$,.2f\n";
  private static final String OUTPUT = "\nOutput 3:\n" +
    "1 imported bottle of perfume: %1$,.2f\n" +
    "1 bottle of perfume: %2$,.2f\n" +
    "1 packet of headache pills: %3$,.2f\n" +
    "1 imported box of chocolates: %4$,.2f\n" +
    "Sales Taxes: %5$,.2f\n" +
    "Total: %6$,.2f";

  @Test
  public void apply() {
    final ImportedItem iperfume = ImportedItem.of(Price.of(27.99));
    final TaxedItem  tperfume = of(Price.of(18.99));
    final ExemptedItem pills = ExemptedItem.of(Price.of(9.75));
    final ImportedItem chocolate = ImportedItem.of(Price.of(11.25));

    final SaleTax saleTax = SaleTax.of(Rate.of(10));
    final ImportTax importTax = ImportTax.of(Rate.of(5));

    final Round nearest = Round.nearest();
    final DecimalOperation add = addWith(Round.with(HALF_UP));

    final double iperfumeDuty = nearest.apply(importTax.apply(iperfume));
    final double iperfumeTax = nearest.apply(saleTax.apply(fromImported(iperfume)));
    final double tperfumeTax = nearest.apply(saleTax.apply(tperfume));
    final double chocolateDuty = nearest.apply(importTax.apply(chocolate));

    final double totalTaxes = add.apply(iperfumeDuty, iperfumeTax, tperfumeTax, chocolateDuty);

    final double taxedIPerfume = add.apply(iperfume.price(), iperfumeDuty, iperfumeTax);
    final double taxedTPerfume = add.apply(tperfume.price(), tperfumeTax);
    final double taxedChocolate = add.apply(chocolate.price(), chocolateDuty);

    final double total = add.apply(taxedIPerfume, taxedTPerfume, pills.price(), taxedChocolate);

    log.info(String.format(Locale.ITALY, INPUT, iperfume.price(), tperfume.price(), pills.price(), chocolate.price()));
    log.info(String.format(Locale.ITALY, OUTPUT, taxedIPerfume, taxedTPerfume, pills.price(), taxedChocolate, totalTaxes, total));

    Assert.assertEquals(32.19, taxedIPerfume, 0);
    Assert.assertEquals(20.89, taxedTPerfume, 0);
    Assert.assertEquals(9.75, pills.price(), 0);
    Assert.assertEquals(11.85, taxedChocolate, 0);
    Assert.assertEquals(6.70, totalTaxes, 0);
    Assert.assertEquals(74.68, total, 0);
  }
}
