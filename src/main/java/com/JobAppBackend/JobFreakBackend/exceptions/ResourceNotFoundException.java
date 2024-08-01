package com.JobAppBackend.JobFreakBackend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    String value;
    Long value1;

    public ResourceNotFoundException(String resourceName,String fieldName,String value){
        super(String.format("%s not found with %s : %s",resourceName,fieldName,value));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.value = value;
    }

    public ResourceNotFoundException(String resourceName,String fieldName,Long value1){
        super(String.format("%s not found with %s : %s",resourceName,fieldName,value1));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.value1 = value1;
    }

    public ResourceNotFoundException(String resourceName){
        super(String.format("No %s not found",resourceName));
        this.resourceName= resourceName;
    }
}
