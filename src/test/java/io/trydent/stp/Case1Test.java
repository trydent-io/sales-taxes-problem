package io.trydent.stp;

import io.trydent.stp.item.ExemptedItem;
import io.trydent.stp.item.Price;
import io.trydent.stp.item.TaxedItem;
import io.trydent.stp.math.DecimalOperation;
import io.trydent.stp.math.Round;
import io.trydent.stp.tax.Rate;
import io.trydent.stp.tax.SaleTax;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static io.trydent.stp.math.DecimalOperation.addWith;
import static java.math.RoundingMode.HALF_UP;

public class Case1Test {
  private static final Logger log = LoggerFactory.getLogger(Case1Test.class);
  private static final String INPUT = "\nInput 1:\n" +
    "1 book at %1$,.2f\n" +
    "1 music CD at %2$,.2f\n" +
    "1 chocolate bar at %3$,.2f\n";
  private static final String OUTPUT = "\nOutput 1:\n" +
    "1 book: %1$,.2f\n" +
    "1 music CD: %2$,.2f\n" +
    "1 chocolate bar: %3$,.2f\n" +
    "Sales Taxes: %4$,.2f\n" +
    "Total: %5$,.2f";

  @Test
  public void apply() {
    final ExemptedItem book = ExemptedItem.of(Price.of(12.49));
    final TaxedItem musicCD = TaxedItem.of(Price.of(14.99));
    final ExemptedItem chocolate = ExemptedItem.of(Price.of(0.85));

    final Rate rate = Rate.of(10);
    final SaleTax saleTax = SaleTax.of(rate);

    final Round nearest = Round.nearest();
    final double musicCDTax = nearest.apply(saleTax.apply(musicCD));

    final DecimalOperation add = addWith(Round.with(HALF_UP));
    final double taxedMusicCD = add.apply(musicCD.price(), musicCDTax);

    final double total = add.apply(book.price(), taxedMusicCD, chocolate.price());

    log.info(String.format(Locale.ITALY, INPUT, book.price(), musicCD.price(), chocolate.price()));
    log.info(String.format(Locale.ITALY, OUTPUT, book.price(), taxedMusicCD, chocolate.price(), musicCDTax, total));

    Assert.assertEquals(29.83, total, 0);
    Assert.assertEquals(1.5, musicCDTax, 0);
  }
}
