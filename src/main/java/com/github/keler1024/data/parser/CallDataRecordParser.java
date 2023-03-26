package com.github.keler1024.data.parser;

import com.github.keler1024.data.CallDataRecord;
import com.github.keler1024.data.CallType;
import com.github.keler1024.data.parser.exception.UnsupportedFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CallDataRecordParser {
    private final BufferedReader reader;

    public CallDataRecordParser(BufferedReader reader) {
        this.reader = reader;
    }

    public CallDataRecord read() throws IOException, UnsupportedFormatException {
        String line;
        line = reader.readLine();
        if (line == null || line.isBlank()) {
            return null;
        }
        return parse(line);
    }

    public static CallDataRecord parse(String cdrString) /*throws UnsupportedFormatException*/ {
        if (cdrString == null || cdrString.isBlank()) {
            throw new IllegalArgumentException("Call data record string is null or blank");
        }
        String[] values = cdrString.split(", ");
        if (values.length != 5) {
            throw new UnsupportedFormatException("Unsupported call data record format");
        }
        return new CallDataRecord(
                CallType.of(values[0]),
                values[1],
                LocalDateTime.parse(values[2], DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                LocalDateTime.parse(values[3], DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                values[4]
        );
    }
}