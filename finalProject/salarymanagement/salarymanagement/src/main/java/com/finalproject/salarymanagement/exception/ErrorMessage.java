package com.finalproject.salarymanagement.exception;

public class ErrorMessage {

    public static String COMPONENT_TYPE_NOT_FOUND = "Component Type not found";
    public static String COMPONENT_TYPE_SUBTYPE_NOT_FOUND = "Component Type Subtype not found";
    public static String SALARY_COMPONENT_NOT_FOUND = "Salary Component not found";
    public static String SALARY_NOT_FOUND = "Salary not found";
    public static String SALARY_IS_ALREADY_APPROVED = "Salary already approved";
    public static String SALARY_IS_ALREADY_ACTIVE = "Salary already active";
    public static String SALARY_CANNOT_BE_APPROVED_ANYMORE = "Salary cannot be approved anymore";
    public static String SALARY_CANNOT_BE_UPDATED_ANYMORE = "Salary cannot be updated anymore";
    public static String DUPLICATED_SALARY_FOR_IMPLEMENTATION_DATE = "There is already a salary for this user on this " +
            "implementation date";
    public static final String OPTIMISTIC_LOCKING_FAILURE = "This entity has been updated by other operation";

}
