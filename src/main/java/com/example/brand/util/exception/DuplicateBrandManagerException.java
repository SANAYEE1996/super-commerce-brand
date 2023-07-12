package com.example.brand.util.exception;

public class DuplicateBrandManagerException extends ManagerException{

    private final static String errorMessage = "이미 등록한 브랜드가 있으므로 브랜드 등록 불가합니다.";

    private final Long removeBrandId;

    public DuplicateBrandManagerException(Long removeBrandId) {
        this.removeBrandId = removeBrandId;
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }

    public Long getRemoveBrandId(){
        return this.removeBrandId;
    }
}
