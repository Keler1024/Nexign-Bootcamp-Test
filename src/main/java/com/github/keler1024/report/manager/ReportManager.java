package com.github.keler1024.report.manager;

import com.github.keler1024.report.Report;
import com.github.keler1024.tariff.TariffFactory;

import java.util.*;

public class ReportManager {
    private final Map<String, Report> reportMap;

    public ReportManager() {
        this.reportMap = new HashMap<>();
    }

    public Report getReport(String phone, String tariff) {
        Report report = reportMap.get(phone);
        if (report == null) {
            report = new Report(phone, TariffFactory.build(tariff));
            reportMap.put(phone, report);
        }
        return report;
    }

    public List<Report> getReports() {
        return List.copyOf(reportMap.values());
    }
}
