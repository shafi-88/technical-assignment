package com.shafi.construction;

import com.shafi.construction.repo.CustomerContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.shafi.construction.utils.CustomerContractUtils.*;

public class Main {

    public static final String newLine = "\n";

    public static void main(String[] args) {
        String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
                "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
                "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
                "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
                "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";

        Map<String, String> responseMap = new HashMap<>();
        Map<String, List> mapperResponse = mapToCustomerContracts(input.split("\\n"),null);
        if (mapperResponse.get("valid").isEmpty()) {
            System.out.println("no valid line found");
        }

        List<CustomerContract> contractList = mapperResponse.get("valid");

        // The number of unique customerId for each contractId
        Function<CustomerContract, String> contractIdClassifier = CustomerContract::getContractId;
        Map<String, Set<String>> contractIdCustomerMap = getUniqueCustomers(contractList, contractIdClassifier);
        responseMap.put("The number of unique customerId for each contractId: ",
                        getUniqueEntitiesCount(contractIdCustomerMap).toString()+ newLine);

        // The number of unique customerId for each geozone
        Function<CustomerContract, String> geoZoneClassifier = CustomerContract::getGeoZone;
        Map<String, Set<String>> zoneCustomerIdMap = getUniqueCustomers(contractList, geoZoneClassifier);
        responseMap.put("The number of unique customerId for each geozone",
                        getUniqueEntitiesCount(zoneCustomerIdMap).toString()+ newLine);

        // The average buildduration for each geozone
        Map<String, List<String>> buildDurationGeoZoneMap = getAllSpecifiedEntities(contractList,
                                                                    CustomerContract::getBuildDuration,
                                                                    CustomerContract::getGeoZone);
        responseMap.put("The average buildduration for each geozone",
                        getAverageOfListElements(buildDurationGeoZoneMap, "s").toString()+ newLine);

        responseMap.put("The list of unique customerId for each geozone", zoneCustomerIdMap.toString()+ newLine);
        System.out.println(responseMap);
    }

}
