package com.microservices.cards.controller;

import com.microservices.cards.constants.CardsConstants;
import com.microservices.cards.model.dto.CardsDto;
import com.microservices.cards.model.dto.ErrorResponseDto;
import com.microservices.cards.model.dto.CardsContactDetailsDto;
import com.microservices.cards.model.dto.ResponseDto;
import com.microservices.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(
        name = "CRUD REST APIs for Cards in bank",
        description = "Create , Update , Read , Delete (Operations)"
)
public class CardsController {

    private final ICardsService iCardsService;

    public CardsController(ICardsService iCardsService){
        this.iCardsService = iCardsService;
    }

    @Autowired
    private Environment environment;
    @Autowired
    CardsContactDetailsDto loansContactDetailsDto;

    @Value("${build.version}")
    private String buildVersion;
    @Operation(
            summary = "Create Cards REST API",
            description = "Create new Cards inside bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Http Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @RequestParam
            String mobileNumber
    ){
        iCardsService.createCardByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto(
                        CardsConstants.STATUS_201,
                        CardsConstants.MESSAGE_201
                )
        );
    }

    @Operation(
            summary = "Fetch Cards REST API",
            description = "Fetch cards depending on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCard(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @RequestParam
            String mobileNumber
    ){

        CardsDto cardsDto = iCardsService.fetchCardsByMobileNumber(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(
            summary = "Update cards REST API",
            description = "Update Card"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Http Status EXPECTATION FAILED"
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(
            @Valid
            @RequestBody
            CardsDto cardsDto
    ){

        boolean isUpdated = iCardsService.updateCardsByMobileNumber(cardsDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(
                            CardsConstants.STATUS_200,
                            CardsConstants.MESSAGE_200
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                new ResponseDto(
                        CardsConstants.STATUS_417,
                        CardsConstants.MESSAGE_417_UPDATE
                )
        );

    }

    @Operation(
            summary = "Delete Card REST API",
            description = "Delete Card depending on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status INTERNAL SERVER ERROR",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Http Status EXPECTATION FAILED"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard (
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
            @RequestParam 
            String mobileNumber
    ){
        boolean isDeleted = iCardsService.deleteCardsByMobileNumber(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(
                            CardsConstants.STATUS_200,
                            CardsConstants.MESSAGE_200
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                new ResponseDto(
                        CardsConstants.STATUS_417,
                        CardsConstants.MESSAGE_417_DELETE
                )
        );

    }
    @Operation(
            summary = "Get Build Version REST API",
            description = "REST API to get build version for cards microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity
                .status(
                        HttpStatus.OK
                ).body(
                        buildVersion
                );
    }



    @Operation(
            summary = "Get Java Version REST API",
            description = "REST API to get java version for cards microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(
                        HttpStatus.OK
                ).body(
                        environment.getProperty("JAVA_HOME")
                );
    }
    @Operation(
            summary = "Get Contact Information REST API",
            description = "REST API to get contact information for cards microservice"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP STATUS OK"
    )
    @ApiResponse(
            responseCode = "500",
            description = "HTTP STATUS INTERNAL SERVER ERROR",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            )
    )
    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactDetailsDto> getContactDetails(){
        return ResponseEntity.status(
                HttpStatus.OK
        ).body(
                loansContactDetailsDto
        );
    }

}
