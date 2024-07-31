package uz.devops.sovchilaruzv2.web.rest.errors;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String msg) {
        super(msg);
    }
}
