package technology.assessment.app.exception;

public class BadRequestException extends RuntimeException{
    private String message;
    public BadRequestException(String message){
        super(message);
        this.message=message;
    }
    public BadRequestException(){

    }

}
