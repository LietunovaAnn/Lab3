package org.example;

import org.example.entities.ViolationsStatisticList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long time = System.currentTimeMillis();
        List<Path> paths = getListAllFilePath();

        ExecutorService service = Executors.newFixedThreadPool(1);
        List<CompletableFuture<Map<String, Double>>> futureList = createTheadPool(paths, service);
        Map<String, Double> resultViolationStatistic = combineResultMap(futureList);
        service.shutdown();

        ViolationsStatisticList violationStatisticList = Parser.getSortedViolationStatisticList(resultViolationStatistic);
        Parser.parseListToXml(violationStatisticList);
        System.out.println(System.currentTimeMillis() - time);
    }

    private static List<CompletableFuture<Map<String, Double>>> createTheadPool(List<Path> paths, ExecutorService service) {
        List<CompletableFuture<Map<String, Double>>> futureList = new ArrayList<>();
        for (Path path : paths) {
            CompletableFuture<Map<String, Double>> callableStatusForOneFile = CompletableFuture.supplyAsync(() -> {
                Map<String, Double> violationMapForOneFile = new HashMap<>();
                Parser.parseAndMakeStatistic(path.toFile(), violationMapForOneFile);
                return violationMapForOneFile;
            }, service);
            futureList.add(callableStatusForOneFile);
        }
        return futureList;
    }

    private static Map<String, Double> combineResultMap(List<CompletableFuture<Map<String, Double>>> futureList) throws ExecutionException, InterruptedException {
        Map<String, Double> resultViolationStatistic = new HashMap<>();
        for (CompletableFuture<Map<String, Double>> callableStatusForOneFile : futureList) {
            sumMap(resultViolationStatistic, callableStatusForOneFile.get());
        }
        return resultViolationStatistic;
    }

    private static void sumMap(Map<String, Double> resultMap, Map<String, Double> mapFromOneFile) {
        for (String key : mapFromOneFile.keySet()) {
            if (resultMap.containsKey(key)) {
                resultMap.put(key, resultMap.get(key) + mapFromOneFile.get(key));
            } else {
                resultMap.put(key, mapFromOneFile.get(key));
            }
        }

    }

    private static List<Path> getListAllFilePath() {
        try (Stream<Path> paths = Files.walk(Paths.get("Task1/src/main/resources/input"))) {
            return paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
