package com.github.keler1024.tariff;

import com.github.keler1024.data.CallType;

import java.math.BigDecimal;
import java.time.Duration;

public interface ITariff {

    BigDecimal calculateCallCost(Duration duration, CallType callType);

    BigDecimal getMonthlyCost();

    String getIndex();
}
