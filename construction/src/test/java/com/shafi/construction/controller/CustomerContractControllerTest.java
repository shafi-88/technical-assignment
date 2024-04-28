package com.shafi.construction.controller;

import com.shafi.construction.repo.ReportRequest;
import com.shafi.construction.repo.Response;
import com.shafi.construction.service.ConstructionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CustomerContractControllerTest {

    @InjectMocks
    CustomerContractController customerContractController;
    @Mock
    ConstructionService constructionService;

    @Test
    public void getReportOfExistingData_Success(){
        when(constructionService.getReport(any()))
                .thenReturn(new Response("The number of unique customerId for each contractId"));
        ResponseEntity response = customerContractController.getReport(getHeaders("valid"));
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getReportOfExistingData_NoHeader(){
        ResponseEntity response = customerContractController.getReport(getHeaders(""));
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void getReportOfExistingData_InValidHeader(){
        ResponseEntity response = customerContractController.getReport(getHeaders(""));
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void getReportForProvidedData_Success(){
        when(constructionService.getReport(any()))
                .thenReturn(new Response("The number of unique customerId for each contractId"));
        ReportRequest mockRequest = new ReportRequest();
        ResponseEntity response = customerContractController.getReport(getHeaders("valid"),getReportRequest("valid"));
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    public void getReportForProvidedData_NoData(){
       ResponseEntity response = customerContractController.getReport(getHeaders("valid"),getReportRequest("noData"));
        assertTrue(response.getStatusCode().is4xxClientError());
    }
    @Test
    public void getReportForProvidedData_NoDelimiter(){
        ResponseEntity response = customerContractController.getReport(getHeaders("valid"),getReportRequest("noDelimiter"));
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    private Map<String,String> getHeaders(String type){
        Map<String,String> headers = new HashMap<>();
        switch (type){
            case "valid":
                headers.put("x-api-key","test");
                return headers;
            case "invalid":
                headers.put("x-api-key","invalid");
                return headers;
            default:
                return headers;
        }
    }

    private ReportRequest getReportRequest(String type){
        ReportRequest request = new ReportRequest();
        switch (type){
            case "valid":
                request.setData("valid test data");
                request.setDelimiter(",");
                return request;
            case "noData":
                request.setDelimiter(",");
                return request;
            case "noDelimiter":
                request.setData("valid test data");
                return request;
            default:
                return request;
        }
    }
}
