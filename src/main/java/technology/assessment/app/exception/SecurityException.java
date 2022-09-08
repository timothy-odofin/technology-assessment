package technology.assessment.app.exception;

public class SecurityException extends RuntimeException{
    private String message;
    public SecurityException(String message){
        super(message);
        this.message=message;
    }
    public SecurityException(){

    }

}
