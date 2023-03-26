package com.github.keler1024.tariff;

import com.github.keler1024.data.CallType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class CommonTariff implements ITariff {
    private static final BigDecimal periodCost = BigDecimal.valueOf(0L, 2);
    private static final BigDecimal minuteCost = BigDecimal.valueOf(50L, 2);
    private static final Duration callTimeLimit = Duration.ofMinutes(100L);
    private static final ITariff perMinuteTariff = new PerMinuteTariff();
    public static final String index = "11";

    private Duration spentTime = Duration.ZERO;

    @Override
    public BigDecimal calculateCallCost(Duration duration, CallType callType) {
        BigDecimal callCost = BigDecimal.valueOf(0L, 2);
        Duration oldSpentTime = spentTime;
        spentTime = spentTime.plus(duration);
        if (callType == CallType.OUTGOING) {
            Duration afterLimitDuration = duration;
            if (oldSpentTime.compareTo(callTimeLimit) < 0) {
                Duration beforeLimitDuration =
                        callTimeLimit.minus(oldSpentTime).compareTo(duration) >= 0 ? duration
                                : callTimeLimit.minus(oldSpentTime);
                afterLimitDuration = afterLimitDuration.minus(beforeLimitDuration);
                BigDecimal minutes =
                        BigDecimal.valueOf(beforeLimitDuration.getSeconds())
                                .divide(BigDecimal.valueOf(60L), RoundingMode.UP);
                callCost = callCost.add(minuteCost.multiply(minutes));
            }
            BigDecimal minutes =
                    BigDecimal.valueOf(afterLimitDuration.getSeconds())
                            .divide(BigDecimal.valueOf(60L), RoundingMode.UP);
            callCost = callCost.add(perMinuteTariff.calculateCallCost(afterLimitDuration, callType));
        }
        return callCost;
    }

    @Override
    public BigDecimal getMonthlyCost() {
        return periodCost;
    }

    @Override
    public String getIndex() {
        return index;
    }
}
