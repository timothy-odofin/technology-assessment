package technology.assessment.app.exception;

public class RecordAccessException extends SecurityException{
    private String message;
    public RecordAccessException(String message){
        super(message);
        this.message=message;
    }
    public RecordAccessException(){

    }

}
