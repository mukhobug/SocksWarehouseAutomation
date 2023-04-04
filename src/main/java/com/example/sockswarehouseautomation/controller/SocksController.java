package com.example.sockswarehouseautomation.controller;

import com.example.sockswarehouseautomation.dto.UpdateQuantityDTO;
import com.example.sockswarehouseautomation.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@Slf4j
@Validated
public class SocksController {
    private final SocksService service;

    @Operation(summary = "getSocksCount", tags = "Socks",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = Integer.class))),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
            }
    )
    @GetMapping()
    public ResponseEntity<Integer> getSocksCount(@RequestParam String color,
                                                 @RequestParam String operation,
                                                 @RequestParam @Min(0) @Max(100) int cottonPart) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCount(color, operation, cottonPart));
    }

    @Operation(summary = "getSocksCount", tags = "Socks",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = Integer.class))),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
            }
    )

    @PostMapping(path = "/income", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postSocksIncome(@RequestBody @Valid UpdateQuantityDTO update) {
        service.incomeSocks(update);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "getSocksCount", tags = "Socks",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = Integer.class))),
                    @ApiResponse(responseCode = "400", description = "BAD REQUEST")
            }
    )

    @PostMapping(path = "/outcome", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postSocksOutcome(@RequestBody @Valid UpdateQuantityDTO update) {
        service.outcomeSocks(update);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
