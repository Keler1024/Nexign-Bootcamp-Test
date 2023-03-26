package com.github.keler1024;

import com.github.keler1024.data.parser.CallDataRecordParser;
import com.github.keler1024.data.parser.exception.UnsupportedFormatException;
import com.github.keler1024.report.Report;
import com.github.keler1024.report.ReportRecord;
import com.github.keler1024.report.manager.ReportManager;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Stream;

public class ReportsGenerator {
    private static final int minCostColumnWidth = 5;
    private static final int maxTotalCostLength = 9;
    private static final int widthUntilCostColumn = 68;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println(args.length + " arguments were provided. Program supports 0 or 1 argument.");
            return;
        }
        BufferedReader inputReader;
        if (args.length == 1) {
            try {
                inputReader = new BufferedReader(new FileReader(args[0]));
            } catch (FileNotFoundException e) {
                System.out.println("Provided file not found.");
                return;
            }
        } else {
            InputStream resourceInputStream = ReportsGenerator.class.getResourceAsStream("/cdr.txt");
            if (resourceInputStream == null) {
                System.out.println("Example file not found");
                return;
            }
            inputReader = new BufferedReader(new InputStreamReader(resourceInputStream));
        }
        ReportManager reportManager = new ReportManager();
        try(Stream<String> linesStream = inputReader.lines()) {
            linesStream.map(CallDataRecordParser::parse).forEach(
                    callDataRecord -> reportManager.getReport(callDataRecord.getPhone(), callDataRecord.getTariff())
                            .addRecord(callDataRecord)
            );
        } catch (UncheckedIOException e) {
            System.out.println("IO exception occurred: " + e.getMessage());
        } catch (UnsupportedFormatException e) {
            System.out.println("Unsupported call data record format encountered");
        }
        Path currentDirectory = Paths.get("").toAbsolutePath();
        Path reportsDirectory = Paths.get(currentDirectory.toString(), "reports");
        try {
            if(Files.exists(reportsDirectory)) {
                try(Stream<Path> contents = Files.walk(reportsDirectory)) {
                    contents.sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                }
            }
            Files.createDirectory(reportsDirectory);
        } catch (IOException e) {
            System.out.println("Failed to create reports directory");
        }
        reportManager.getReports().forEach(report -> createReportFile(report, reportsDirectory));
    }

    private static void createReportFile(Report report, Path directory) {
        report.sortRecords();
        final int maxCostLength = report.getRecords().stream()
                .mapToInt(reportRecord -> reportRecord.getCost().toString().length())
                .max().orElse(0);
        final int costColumnWidth = Math.max(minCostColumnWidth, maxCostLength);

        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(
                        directory.toAbsolutePath()
                                + "/" + report.getPhone() + "_" + report.getTariffIndex() + ".txt"
                )
        )) {
            writer.write("Tariff index: " + report.getTariffIndex() + System.lineSeparator());
            writer.write(getHorizontalLine(costColumnWidth));
            writer.write("Report for phone number " + report.getPhone() + ":" + System.lineSeparator());
            writer.write(getHorizontalLine(costColumnWidth));
            writer.write("| Call Type |   Start Time        |     End Time        | Duration | Cost  "
                    + " ".repeat(Math.max(maxCostLength - minCostColumnWidth, 0)) + "|" + System.lineSeparator());
            writer.write(getHorizontalLine(costColumnWidth));

            for (ReportRecord record : report.getRecords()) {
                StringBuilder recordBuilder = new StringBuilder();
                recordBuilder.append("|     ").append(record.getCallType().getCode()).append("    | ");
                recordBuilder.append(record.getStartDateTime().format(dateTimeFormatter)).append(" | ");
                recordBuilder.append(record.getEndDateTime().format(dateTimeFormatter)).append(" | ");
                long hours = record.getDuration().toHours();
                long minutes = record.getDuration().toMinutesPart();
                long seconds = record.getDuration().toSecondsPart();
                String durationString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                recordBuilder.append(durationString).append(" | ");
                recordBuilder.append(
                        " ".repeat(Math.max(minCostColumnWidth, maxCostLength) - record.getCost().toString().length())
                );
                recordBuilder.append(record.getCost()).append(" |").append(System.lineSeparator());
                writer.write(recordBuilder.toString());
            }

            writer.write(getHorizontalLine(costColumnWidth));
            BigDecimal totalCost = report.getTotalCost();
            writer.write("|                                           Total Cost: | "
                    + " ".repeat(maxTotalCostLength - totalCost.toString().length()) + totalCost + " rubles |"
                    + System.lineSeparator());
            writer.write(getHorizontalLine(costColumnWidth));
        } catch (IOException e) {
            System.out.println(
                    "IO exception occurred while generating report for " + report.getPhone() + ": " + e.getMessage()
            );
        }
    }

    private static String getHorizontalLine(int costColumnWidth) {
        return "-".repeat(widthUntilCostColumn + 3 + costColumnWidth) + System.lineSeparator();
    }
}
