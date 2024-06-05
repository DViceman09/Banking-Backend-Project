
package com.sandesh.banking_app.DTO;
/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String AccHolderName;
    private double balance;
}
*/

public record AccountDTO(Long id, String AccHolderName, double balance)
{

}
