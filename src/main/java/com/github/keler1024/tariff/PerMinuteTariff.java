package com.github.keler1024.tariff;

import com.github.keler1024.data.CallType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class PerMinuteTariff implements ITariff {
    private static final BigDecimal monthlyCost = BigDecimal.valueOf(0L, 2);
    private static final BigDecimal minuteCost = BigDecimal.valueOf(150L, 2);
    public static final String index = "03";

    @Override
    public BigDecimal calculateCallCost(Duration duration, CallType callType) {
        BigDecimal minutes =
                BigDecimal.valueOf(duration.getSeconds()).divide(BigDecimal.valueOf(60L), RoundingMode.UP);
        return minuteCost.multiply(minutes);
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
