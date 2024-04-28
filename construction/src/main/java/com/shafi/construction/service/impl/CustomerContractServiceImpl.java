package com.shafi.construction.service.impl;

import com.shafi.construction.repo.CustomerContract;
import com.shafi.construction.repo.Response;
import com.shafi.construction.service.ConstructionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.shafi.construction.utils.CustomerContractUtils.*;

@Service
public class CustomerContractServiceImpl implements ConstructionService {

    @Override
    public Response getReport(Map<String,String> inputMap) {
        String inputData =initData;
        String fieldDelimiter = null;
        if(inputMap!=null){
            inputData=inputMap.get("inputData");
            fieldDelimiter = inputMap.get("delimiter");
        }
        Response reportResponse = new Response();
        Map<String, List> mapperResponse = mapToCustomerContracts(inputData.split("\\n"),fieldDelimiter);
        String newLine="\n";
        if (mapperResponse.get("valid").isEmpty()) {
           reportResponse.messageList.add("no valid line found");
           return reportResponse;
        }
        List<CustomerContract> contractList = mapperResponse.get("valid");

        // The number of unique customerId for each contractId
        Function<CustomerContract, String> contractIdClassifier = CustomerContract::getContractId;
        Map<String, Set<String>> contractIdCustomerMap = getUniqueCustomers(contractList, contractIdClassifier);
        reportResponse.messageList.add("The number of unique customerId for each contractId: "+
                        getUniqueEntitiesCount(contractIdCustomerMap).toString().concat(newLine));

        // The number of unique customerId for each geozone
        Function<CustomerContract, String> geoZoneClassifier = CustomerContract::getGeoZone;
        Map<String, Set<String>> zoneCustomerIdMap = getUniqueCustomers(contractList, geoZoneClassifier);
        reportResponse.messageList.add("The number of unique customerId for each geo-zone: "+
                        getUniqueEntitiesCount(zoneCustomerIdMap).toString().concat(newLine));

        // The average build duration for each geo-zone
        Map<String, List<String>> buildDurationGeoZoneMap = getAllSpecifiedEntities(contractList,
                                                                    CustomerContract::getBuildDuration,
                                                                    CustomerContract::getGeoZone);
        reportResponse.messageList.add("The average build duration for each geo-zone: "+
                        getAverageOfListElements(buildDurationGeoZoneMap,"s").toString().concat(newLine));

        reportResponse.messageList.add("The list of unique customerId for each geo-zone: "+
                        zoneCustomerIdMap.toString().concat(newLine));

        // add failed/invalid input lines
        reportResponse.messageList.add("invalid input data/lines: "+
                        mapperResponse.get("error").toString());

        return reportResponse;
    }
}
