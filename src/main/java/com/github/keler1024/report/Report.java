package com.github.keler1024.report;

import com.github.keler1024.data.CallDataRecord;
import com.github.keler1024.tariff.ITariff;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Report {
    private final String phone;
    private final ITariff tariff;
    private final List<ReportRecord> records;

    public Report(String phone, ITariff tariff) {
        this.phone = phone;
        this.tariff = tariff;
        this.records = new ArrayList<>();
    }

    public String getPhone() {
        return phone;
    }

    public String getTariffIndex() {
        return tariff.getIndex();
    }

    public List<ReportRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public void sortRecords() {
        records.sort(Comparator.comparing(ReportRecord::getStartDateTime));
    }

    public BigDecimal getTotalCost() {
        return getRecords().stream()
                .map(ReportRecord::getCost)
                .reduce(tariff.getMonthlyCost(), BigDecimal::add);
    }

    public void addRecord(CallDataRecord callDataRecord) {
        Duration callDuration = Duration.between(callDataRecord.getStartDateTime(), callDataRecord.getEndDateTime());
        BigDecimal callCost = tariff.calculateCallCost(callDuration, callDataRecord.getCallType());
        records.add(new ReportRecord(
                callDataRecord.getCallType(),
                callDataRecord.getStartDateTime(),
                callDataRecord.getEndDateTime(),
                callCost
        ));
    }
}
