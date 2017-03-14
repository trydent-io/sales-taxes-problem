package io.trydent.stp;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;

public class TaxesTest {
  private static final Logger log = LoggerFactory.getLogger(TaxesTest.class);

  @Test
  public void basicSaleTax() {
    final double price = 14.99d;
    final double taxedPrice = price + (price / 100 * 10);
    final BigDecimal decimal = new BigDecimal(taxedPrice)
      .setScale(2, ROUND_HALF_UP);

    log.info("Music CD: " + price);
    log.info("Music CD (taxed): " + taxedPrice);
    log.info("Music CD (rounded): " + decimal.doubleValue());

    Assert.assertEquals(decimal.doubleValue(), 16.49d, 0d);
  }

  @Test
  public void salesTax() {
    final double book = 12.49d;
    final double musicCD = 14.99d;
    final double chocolate = 0.85d;

    final double musicCDtax = new BigDecimal(musicCD / 100 * 10)
      .setScale(2, ROUND_HALF_UP)
      .doubleValue();

    final double taxedMusicCD = new BigDecimal(musicCD + musicCDtax)
      .setScale(2, ROUND_HALF_UP)
      .doubleValue();

    final double total = book + taxedMusicCD + chocolate;

    log.info("Music CD (taxed): " + taxedMusicCD);
    log.info("Total: " + total);

    Assert.assertEquals(musicCDtax, 1.5d, 0d);
    Assert.assertEquals(total, 29.83d, 0d);
  }
}
