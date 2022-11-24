package com.dut.team92.userservice.util;

import com.dut.team92.userservice.domain.dto.request.CreateMemberDto;
import com.dut.team92.userservice.exception.FailedReadDataFileCSV;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CSVHelper {
    private static final String TYPE = "text/csv";
    private static final String[] HEADERS = { "Username", "Password", "Email", "DisplayName", "FirstName", "LastName" };

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<CreateMemberDto> csvToMembers(InputStream inputStream, UUID organizationId) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.builder().setDelimiter(",").setHeader(HEADERS).setSkipHeaderRecord(true).build());) {

            List<CreateMemberDto> members = csvParser.getRecords().stream()
                    .map(csvRecord -> {
                        CreateMemberDto memberDto = new CreateMemberDto(
                                csvRecord.get("Username").trim(),
                                csvRecord.get("Password").trim(),
                                csvRecord.get("Email").trim(),
                                csvRecord.get("FirstName"),
                                csvRecord.get("LastName"),
                                csvRecord.get("DisplayName")
                        );
                        memberDto.setOrganizationId(organizationId);
                        return memberDto;
                    }).collect(Collectors.toList());

            return members;
        } catch (IOException e) {
            throw new FailedReadDataFileCSV("Fail to parse CSV file: " + e.getMessage());
        }
    }
}
