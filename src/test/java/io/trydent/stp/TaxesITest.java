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

import static io.trydent.stp.item.ImportedItem.fromTaxed;
import static io.trydent.stp.item.TaxedItem.*;
import static io.trydent.stp.math.DecimalOperation.addWith;
import static java.math.RoundingMode.HALF_UP;

public class TaxesITest {
  private static final Logger log = LoggerFactory.getLogger(TaxesITest.class);

  @Test
  public void case1() {
    final ExemptedItem book = ExemptedItem.of(Price.of(12.49));
    final TaxedItem musicCD = of(Price.of(14.99));
    final ExemptedItem chocolate = ExemptedItem.of(Price.of(0.85));

    final Rate rate = Rate.of(10);
    final SaleTax saleTax = SaleTax.of(rate);

    log.info("Book: " + book.price());
    log.info("MusicCD: " + musicCD.price());
    log.info("Chocolate: " + chocolate.price());
    log.info("SaleTax: " + saleTax.rate());

    final Round nearest = Round.nearest();
    final double musicCDTax = nearest.apply(saleTax.apply(musicCD));

    log.info("MusicCD (tax): " + musicCDTax);

    final DecimalOperation add = addWith(Round.with(HALF_UP));
    final double taxedMusicCD = add.apply(musicCD.price(), musicCDTax);

    final double taxedTotal = add.apply(book.price(), taxedMusicCD, chocolate.price());
    final double total = add.apply(book.price(), musicCD.price(), chocolate.price());

    log.info("Total: " + total);
    log.info("Total (taxed): " + taxedTotal);

    Assert.assertEquals(29.83, taxedTotal, 0);
    Assert.assertEquals(1.5, musicCDTax, 0);
  }

  @Test
  public void case2() {
    final ImportedItem chocolate = ImportedItem.of(Price.of(10));
    final TaxedItem perfume = of(Price.of(47.50));

    final SaleTax saleTax = SaleTax.of(Rate.of(10));
    final ImportTax importTax = ImportTax.of(Rate.of(5));

    log.info("Chocolate: " + chocolate.price());
    log.info("Perfume: " + perfume.price());
    log.info("SaleTax: " + saleTax.rate());
    log.info("ImportTax: " + importTax.rate());

    final Round nearest = Round.nearest();
    final double perfumeTax = nearest.apply(saleTax.apply(perfume));
    final double chocolateDuty = nearest.apply(importTax.apply(chocolate));
    final double perfumeDuty = nearest.apply(importTax.apply(fromTaxed(perfume)));

    log.info("Chocolate (duty): " + chocolateDuty);
    log.info("Perfume (duty): " + perfumeDuty);
    log.info("Perfume (tax): " + perfumeTax);

    final DecimalOperation add = addWith(Round.with(HALF_UP));

    final double totalTaxes = add.apply(chocolateDuty, perfumeDuty, perfumeTax);

    log.info("Taxes (total): " + totalTaxes);

    final double taxedChocolate = add.apply(chocolate.price(), chocolateDuty);
    final double taxedPerfume = add.apply(perfume.price(), perfumeDuty, perfumeTax);

    final double taxedTotal = add.apply(taxedChocolate, taxedPerfume);

    log.info("Total (taxed): " + taxedTotal);

    Assert.assertEquals(65.15, taxedTotal, 0);
    Assert.assertEquals(7.65, totalTaxes, 0);
  }

  @Test
  public void case3() {
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

    Assert.assertEquals(32.19, taxedIPerfume, 0);
    Assert.assertEquals(20.89, taxedTPerfume, 0);
    Assert.assertEquals(9.75, pills.price(), 0);
    Assert.assertEquals(11.85, taxedChocolate, 0);
    Assert.assertEquals(74.68, total, 0);
  }
}
