package technology.assessment.app.model.dto.request;

import lombok.Data;
@Data
public class TransactionRequest {
    String buyerToken;
   String itemCode;
   Integer quantity;
}
