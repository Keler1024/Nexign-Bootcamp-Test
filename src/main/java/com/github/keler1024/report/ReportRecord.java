package com.github.keler1024.report;

import com.github.keler1024.data.CallType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class ReportRecord {
    private final CallType callType;
//    private final String phone;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final Duration duration;
//    private final String tariff;
    private final BigDecimal cost;

    public ReportRecord(CallType callType,
//                        String phone,
                        LocalDateTime startDateTime,
                        LocalDateTime endDateTime,
//                        String tariff,
                        BigDecimal cost) {
        if (callType == null || /*phone == nul ||*/ startDateTime == null || endDateTime == null
                || /*tariff == null ||*/ cost == null) {
            throw new NullPointerException();
        }
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("Call's end time is before call's start time");
        }
        this.callType = callType;
//        this.phone = phone;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.duration = Duration.between(startDateTime, endDateTime);
//        this.tariff = tariff;
        this.cost = cost;
    }

    public CallType getCallType() {
        return callType;
    }

//    public String getPhone() {
//        return phone;
//    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Duration getDuration() {
        return duration;
    }

//    public String getTariff() {
//        return tariff;
//    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportRecord that = (ReportRecord) o;
        return getCallType() == that.getCallType()
//                && getPhone().equals(that.getPhone())
                && getStartDateTime().equals(that.getStartDateTime())
                && getEndDateTime().equals(that.getEndDateTime())
                && getDuration().equals(that.getDuration())
//                && getTariff().equals(that.getTariff())
                && getCost().equals(that.getCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getCallType(),
//                getPhone(),
                getStartDateTime(),
                getEndDateTime(),
                getDuration(),
//                getTariff(),
                getCost()
        );
    }
}
