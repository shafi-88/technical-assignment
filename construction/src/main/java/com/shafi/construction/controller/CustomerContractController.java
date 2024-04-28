package com.shafi.construction.controller;

import com.shafi.construction.repo.ReportRequest;
import com.shafi.construction.service.ConstructionService;
import com.shafi.construction.repo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomerContractController {
    @Autowired
    ConstructionService customerContractService;
    final String xApiKey ="test";

    @GetMapping("/report")
    ResponseEntity getReport(@RequestHeader Map<String,String> headers){
        String apiKey = headers.get("x-api-key");
        if(apiKey==null||!apiKey.equals(xApiKey)){
            return new ResponseEntity<>("x-api-key missing in header. set x-api-key = test",HttpStatus.UNAUTHORIZED);
        }
        Response report = customerContractService.getReport(null);
        return new ResponseEntity<>(report.toString(),HttpStatus.OK);
    }

    @PostMapping("/report")
    ResponseEntity getReport(@RequestHeader Map<String,String> headers, @RequestBody ReportRequest request){
        String apiKey = headers.get("x-api-key");
        if(apiKey==null||!apiKey.equals(xApiKey)){
            return new ResponseEntity<>("x-api-key missing in header. set x-api-key = test",HttpStatus.UNAUTHORIZED);
        }
        if(request.getData()==null ||request.getDelimiter()==null){
            return new ResponseEntity<>("missing mandatory field data or delimiter!!",HttpStatus.BAD_REQUEST);
        }
        Map<String,String> inputMap = new HashMap<>();
        inputMap.put("inputData",request.getData());
        inputMap.put("delimiter",request.getDelimiter());
        Response report = customerContractService.getReport(inputMap);
        return new ResponseEntity<>(report.toString(),HttpStatus.OK);
    }

}
