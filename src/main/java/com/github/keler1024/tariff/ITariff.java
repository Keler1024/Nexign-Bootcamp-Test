package com.github.keler1024.tariff;

import java.math.BigDecimal;
import java.time.Duration;

public interface ITariff {

    BigDecimal calculateCallCost(Duration duration);

    BigDecimal getMonthlyPrice();
}
