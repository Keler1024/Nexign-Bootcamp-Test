package com.github.keler1024.tariff;

import com.github.keler1024.data.CallType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class UnlimitedTariff implements ITariff {
    private static final Duration callTimeLimit = Duration.ofMinutes(300L);
    private static final BigDecimal monthlyCost = BigDecimal.valueOf(10000L, 2);
    private static final BigDecimal minuteCost = BigDecimal.valueOf(100L, 2);
    public static final String index = "06";

    private Duration spentTime = Duration.ZERO;

    @Override
    public BigDecimal calculateCallCost(Duration duration, CallType callType) {
        spentTime = spentTime.plus(duration);
        BigDecimal callCost = BigDecimal.valueOf(0L, 2);
        if (spentTime.compareTo(callTimeLimit) > 0) {
            Duration checkedDuration =
                    spentTime.minus(callTimeLimit).compareTo(duration) >= 0 ? duration : spentTime.minus(callTimeLimit);
            BigDecimal minutes =
                    BigDecimal.valueOf(checkedDuration.getSeconds()).divide(BigDecimal.valueOf(60L), RoundingMode.UP);
            callCost = minuteCost.multiply(minutes);
        }
        return callCost;
    }

    @Override
    public BigDecimal getMonthlyCost() {
        return monthlyCost;
    }

    @Override
    public String getIndex() {
        return index;
    }
}
