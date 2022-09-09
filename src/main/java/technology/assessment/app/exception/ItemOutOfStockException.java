package technology.assessment.app.exception;

public class ItemOutOfStockException extends RuntimeException{
    private String message;
    public ItemOutOfStockException(String message){
        super(message);
        this.message=message;
    }
    public ItemOutOfStockException(){

    }

}
