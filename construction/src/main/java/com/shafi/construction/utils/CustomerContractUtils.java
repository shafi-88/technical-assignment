package com.shafi.construction.utils;

import com.shafi.construction.repo.CustomerContract;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.shafi.construction.utils.CommonUtils.extractFieldArray;

@Component
public class CustomerContractUtils {
    /**
     * calculate the average value of all the items belonging to specific key.
     * @param listOfValueKeyMap Map containing list of values for each key
     * @param unitSuffix any unit/suffix(e.g. s->seconds,h->hour etc.) to be considered for given values.
     *                   If null no suffix added or processed.
     * @return map of key and corresponding average
     */
    public static Map<String, String> getAverageOfListElements(Map<String, List<String>> listOfValueKeyMap, String unitSuffix) {
        Map<String, String> geoZoneAverageDurationMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : listOfValueKeyMap.entrySet()) {
            Double ave = entry.getValue().stream().map(s -> Integer
                    .parseInt(s.trim().substring(0, s.length() - unitSuffix.length()))
            ).collect(Collectors.averagingInt(i -> i));
            geoZoneAverageDurationMap.put(entry.getKey(), ave + unitSuffix);
        }
        return geoZoneAverageDurationMap;
    }

    /**
     * get count of items for corresponding key in map
     * @param contractIdCustomerMap map items belonging to specific keys
     * @return List of key value map, where value contains the count of items that belong to specified key.
     */
    public static List<Map<String, Integer>> getUniqueEntitiesCount(Map<String, Set<String>> contractIdCustomerMap) {
        return contractIdCustomerMap.entrySet().stream().map(e -> {
            Map<String, Integer> countMap = new HashMap<>();
            countMap.put(e.getKey(), e.getValue().size());
            return countMap;
        }).collect(Collectors.toList());
    }

    /**
     * The number of unique customerId for each specified classifier
     */
    public static Map<String, Set<String>> getUniqueCustomers(List<CustomerContract> contractList, Function<CustomerContract, String> classifier) {
        return contractList.stream().collect(Collectors.groupingBy(classifier,
                Collectors.mapping(CustomerContract::getCustomerId, Collectors.toSet())));
    }

    /**
     * get list of specified entities based on given classifier.
     * E.g. to get all customer for every geoZone,
     * - specifiedEntity is customer
     * - classifier is geozone
     *
     * @param contractList
     * @param specifiedEntity what should contain in grouped result
     * @param classifier      group by what?
     * @return map of classifier and list of specified entities
     */
    public static Map<String, List<String>> getAllSpecifiedEntities(List<CustomerContract> contractList, Function<CustomerContract, String> specifiedEntity, Function<CustomerContract, String> classifier) {
        return contractList.stream().collect(Collectors.groupingBy(classifier,
                Collectors.mapping(specifiedEntity, Collectors.toList())));
    }

    /**
     * convert lines to customerContract objects.
     *
     * @param arrayOfLines array of input lines.
     * @param fieldDelimiter delimiter used to separate fields
     * @return Map containing validList of mapped Object and list of errorResponseString
     */
    public static Map<String, List> mapToCustomerContracts(String[] arrayOfLines,String fieldDelimiter) {
        List<CustomerContract> cc = new ArrayList<>();
        List<String> errorList = new ArrayList<>();
        if(fieldDelimiter==null||fieldDelimiter.isEmpty()){
            fieldDelimiter = ",";
        }
        final int fieldCount = 6;
        for (String line : arrayOfLines) {
            try {
                String[] fieldArray = extractFieldArray(line, fieldDelimiter, fieldCount);
                cc.add(new CustomerContract(fieldArray));
            } catch (Exception e) {
                errorList.add("invalid line: " + line + " error: " + e.getMessage());
            }
        }
        Map<String, List> responseMap = new HashMap<>();
        responseMap.put("valid", cc);
        responseMap.put("error", errorList);
        return responseMap;
    }

}
