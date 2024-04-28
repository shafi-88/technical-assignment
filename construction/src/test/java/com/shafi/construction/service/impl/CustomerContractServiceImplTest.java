package com.shafi.construction.service.impl;

import com.shafi.construction.repo.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CustomerContractServiceImplTest {
    @InjectMocks
    CustomerContractServiceImpl customerContractService;

    @Test
    public void getReportEmptyInputMap_Success(){
        Response response = customerContractService.getReport(null);
        assertTrue(response.messageList.size()>1);
    }
    @Test
    public void getReportValidInputMap_Success(){
        Response response = customerContractService.getReport(getInputMap("valid"));
        assertTrue(response.messageList.size()>1);
    }
    @Test
    public void getReportInValidInputMap_Error(){
        Response response = customerContractService.getReport(getInputMap("invalidInputData"));
        assertTrue(response.messageList.size()==1);
    }

    private Map<String,String> getInputMap(String type){
        Map<String,String> inputMap = new HashMap<>();
        switch (type){
            case "valid":
                String validData="2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
                        "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
                        "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
                        "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
                        "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";
                inputMap.put("inputData",validData);
                inputMap.put("delimiter",",");
                return inputMap;
            case "invalidInputData":
                inputMap.put("inputData","2343225,2345,us_east,RedTeam,ProjectApple");
                inputMap.put("delimiter",",");
                return inputMap;
            default:
                inputMap.put("inputData","");
                inputMap.put("delimiter","");
                return inputMap;
        }
    }
}
