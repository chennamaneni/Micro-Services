package com.example.user.exceptions;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class WebServiceErrorResponse {


    private String message;
    private List<String> details;

    public WebServiceErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
