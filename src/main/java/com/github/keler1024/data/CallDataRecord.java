package com.github.keler1024.data;

import java.time.LocalDateTime;
import java.util.Objects;

public class CallDataRecord {
    private final CallType callType;
    private final String phone;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String tariff;

    public CallDataRecord(CallType callType,
                          String phone,
                          LocalDateTime startDateTime,
                          LocalDateTime endDateTime,
                          String tariff) {
        if (callType == null || phone == null || startDateTime == null || endDateTime == null || tariff == null) {
            throw new NullPointerException();
        }
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("Call's end time is before call's start time");
        }
        this.callType = callType;
        this.phone = phone;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.tariff = tariff;
    }

    public CallType getCallType() {
        return callType;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getTariff() {
        return tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallDataRecord that = (CallDataRecord) o;
        return getCallType() == that.getCallType()
                && getPhone().equals(that.getPhone())
                && getStartDateTime().equals(that.getStartDateTime())
                && getEndDateTime().equals(that.getEndDateTime())
                && getTariff().equals(that.getTariff());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCallType(), getPhone(), getStartDateTime(), getEndDateTime(), getTariff());
    }

    @Override
    public String toString() {
        return "CallDataRecord{" +
                "callType=" + callType +
                ", phone='" + phone + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", tariff='" + tariff + '\'' +
                '}';
    }
}
