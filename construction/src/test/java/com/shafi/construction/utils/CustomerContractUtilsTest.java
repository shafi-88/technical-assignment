package com.shafi.construction.utils;

import com.shafi.construction.repo.CustomerContract;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CustomerContractUtilsTest {

    @Test
    public void getAverageOfListElements_ValidInputWithSuffix_S(){
        Map<String, List<String>> mockMap = new HashMap<>();
        mockMap.put("a", Arrays.asList("1s","2s","3s"));
        mockMap.put("b", Arrays.asList("5s","10s","15s"));
        Map<String,String> response =CustomerContractUtils.getAverageOfListElements(mockMap,"s");
        assertTrue(response.get("a").equals("2.0s"));
        assertTrue(response.get("b").equals("10.0s"));
    }
    @Test
    public void getAverageOfListElements_validInputWithoutSuffix(){
        Map<String, List<String>> mockMap = new HashMap<>();
        mockMap.put("a", Arrays.asList("1","2","3"));
        mockMap.put("b", Arrays.asList("5","10","15"));
        Map<String,String> response =CustomerContractUtils.getAverageOfListElements(mockMap,"");
        assertTrue(response.get("a").equals("2.0"));
        assertTrue(response.get("b").equals("10.0"));
    }

    @Test
    public void getUniqueEntitiesCount_validEntitiesMap(){
        Map<String, Set<String>> mockMap = new HashMap<>();
        Set<String> s1 = new HashSet<>();
        s1.addAll(Arrays.asList("1","3","3","3"));
        mockMap.put("a", s1);
        Set<String> s2 = new HashSet<>();
        s2.addAll(Arrays.asList("5","10","15","5","10"));
        mockMap.put("b", s2);
        List<Map<String, Integer>> response  = CustomerContractUtils.getUniqueEntitiesCount(mockMap);
        assertTrue(response.get(0).get("a")==2);
        assertTrue(response.get(1).get("b")==3);
    }

    @Test
    public void getUniqueCustomers_forGeoZone(){
        List<CustomerContract> ccList = new ArrayList<>();
        ccList.add(new CustomerContract(
                        new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343625","2345","us_west","RedTeam","ProjectApple","3445s"}));
        Map<String,Set<String>> result = CustomerContractUtils.getUniqueCustomers(ccList,CustomerContract::getGeoZone);
        assertTrue(result.get("us_east").size()==1);
        assertTrue(result.get("us_west").size()==1);

    }
    @Test
    public void getUniqueCustomers_forContractId(){
        List<CustomerContract> ccList = new ArrayList<>();
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343625","2346","us_west","RedTeam","ProjectApple","3445s"}));
        Map<String,Set<String>> result = CustomerContractUtils.getUniqueCustomers(ccList,CustomerContract::getContractId);
        assertTrue(result.get("2345").size()==1);
        assertTrue(result.get("2346").size()==1);

    }

    @Test
    public void getAllSpecifiedEntities_customersPerGeoZone(){
        List<CustomerContract> ccList = new ArrayList<>();
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"}));
        ccList.add(new CustomerContract(
                new String[]{"2343625","2346","us_west","RedTeam","ProjectApple","3445s"}));
        Map<String, List<String>> result = CustomerContractUtils
                                            .getAllSpecifiedEntities(ccList,
                                                CustomerContract::getCustomerId,
                                                CustomerContract::getContractId);
        assertTrue(result.get("2345").size()==3);
        assertTrue(result.get("2346").size()==1);
    }

    @Test
    public void mapToCustomerContracts_validMultiLineData(){
        String multiLineInput ="2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
                "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
                "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
                "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
                "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";
        Map<String, List> result =CustomerContractUtils.mapToCustomerContracts(multiLineInput.split("\\n"),",");
        assertTrue(result.get("valid").size()==5);
        assertTrue(result.get("error").size()==0);
    }
    @Test
    public void mapToCustomerContracts_partialValidMultiLineData(){
        String multiLineInput ="2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
                "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
                "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
                "1233456,us_west,BlueTeam,ProjectDate,2221s\n" +
                "3244132,eu_west,YellowTeam3,ProjectEgg,4122s";
        Map<String, List> result =CustomerContractUtils.mapToCustomerContracts(multiLineInput.split("\\n"),",");
        assertTrue(result.get("valid").size()==3);
        assertTrue(result.get("error").size()==2);
    }
}
