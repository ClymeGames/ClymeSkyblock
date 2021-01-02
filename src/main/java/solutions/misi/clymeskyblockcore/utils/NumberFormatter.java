package solutions.misi.clymeskyblockcore.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter {

    public String formatToCurrency(BigDecimal number) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
        return numberFormat.format(number);
    }
}
