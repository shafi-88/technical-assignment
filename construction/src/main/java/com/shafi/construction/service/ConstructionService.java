package com.shafi.construction.service;

import com.shafi.construction.repo.Response;

import java.util.Map;

public interface ConstructionService {

    String initData = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n" +
            "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n" +
            "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n" +
            "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n" +
            "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";

    /**
     * get cumulative report for input data
     *
     * @param inputData
     * @return
     */
    public Response getReport(Map<String,String> inputMap);
}
