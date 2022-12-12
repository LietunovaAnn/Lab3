package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.entities.Violation;
import org.example.entities.ViolationsStatisticList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Parser {
    private static final File resultFile = new File("Task1/src/main/resources/violationStatisticFile.xml");

    private static void saveViolationStatistic(Violation violation, Map<String, Double> violationStatisticMap) {
        String type = violation.getType();
        Double fineAmount = violation.getFineAmount();
        if (violationStatisticMap.containsKey(type)) {
            violationStatisticMap.put(type, fineAmount + violationStatisticMap.get(type));
        } else {
            violationStatisticMap.put(type, fineAmount);
        }
    }

    public static ViolationsStatisticList getSortedViolationStatisticList(Map<String, Double> violationStatisticMap) {
        ViolationsStatisticList violations = new ViolationsStatisticList();
        List<Violation> violationStatisticList = new ArrayList<>();
        violationStatisticMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> violationStatisticList.add(new Violation(x.getKey(), x.getValue())));
        violations.setViolationStatisticList(violationStatisticList);
        return violations;
    }

    public static void parseListToXml(ViolationsStatisticList violationStatisticList) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(resultFile, violationStatisticList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parseAndMakeStatistic(File inputFile, Map<String, Double> violationStatisticMap) {
        JsonFactory jsonFactory = new JsonFactory();
        try {
            Violation violation = new Violation();
            JsonParser jParser = jsonFactory.createParser(inputFile);
            while (jParser.nextToken() != null) {
                String field_name = jParser.getCurrentName();
                if ("type".equals(field_name)) {
                    jParser.nextToken();
                    violation.setType(jParser.getText());
                }
                if ("fine_amount".equals(field_name)) {
                    jParser.nextToken();
                    Violation.class.getMethod("setFineAmount").invoke(violation, jParser.getDoubleValue());
                    violation.setFineAmount(jParser.getDoubleValue());
                }
                if (violation.getType() != null && violation.getFineAmount() != null) {
                    saveViolationStatistic(violation, violationStatisticMap);
                    violation.setFineAmount(null);
                    violation.setType(null);
                }

            }
            jParser.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
