package uz.devops.sovchilaruzv2.web.rest.errors;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(String msg) {
        super(msg);
    }
}
