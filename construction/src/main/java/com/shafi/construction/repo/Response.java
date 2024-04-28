package com.shafi.construction.repo;

import java.util.ArrayList;
import java.util.List;

public class Response {
    public List<String> messageList;
    public Response(){
        messageList=new ArrayList<>();
    }
    public Response(String message){
        messageList=new ArrayList<>();
        messageList.add(message);
    }

    @Override
    public String toString() {

        return "{ \n"+
                messageList.toString()+
                "\n }";
    }
}
