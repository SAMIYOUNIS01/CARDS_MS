package com.microservices.cards.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Cards",
        description = "Schema to hold Cards Information"
)
public class CardsDto {


    @Schema(
            description = "Client mobile number",
            example = "1234567890"
    )
    @NotEmpty(message = "Mobile number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Client Card Number",
            example = "154632514875"
    )
    @NotEmpty(message = "Card Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})" , message = "Card number must be 12 digits")
    private String cardNumber;

    @Schema(
            description = "Client Card Type",
            example = "Debit Card"
    )
    @NotEmpty(message = "Card type can not be null or empty")
    @Size(min = 5 , max = 30 , message = "The length of card type must be between 5 to 30")
    private String cardType;

    @Schema(
            description = "Card Total Limit",
            example = "100000"
    )
    @NotEmpty(message = "Total limit can not be null or empty")
    @Positive(message = "Total Limit must be greater than zero")
    private int totalLimit;

    @Schema(
            description = "Used Amount",
            example = "0"
    )
    @NotEmpty(message = "Total Limit can not be null or empty")
    @PositiveOrZero(message = "Amount used can not be negative")
    private int amountUsed;

    @Schema(
            description = "Card Available Amount",
            example = "10000"
    )
    @NotEmpty(message = "Available amount can not be null or empty")
    @Positive(message = "Available amount must be greater that zero")
    private int availableAmount;

}
