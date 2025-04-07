package com.malex.controller;

import com.malex.exception.InvalidInputException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SalesRestController {

  @PostMapping("/calculate")
  public ResponseEntity<OperationResult> calculate(
      @Validated @RequestBody OperationRequest operationRequest) {

    OperationResult operationResult;
    Double discount = operationRequest.discount();
    if (discount == null) {
      operationResult =
          new OperationResult(operationRequest.basePrice(), null, operationRequest.basePrice());
    } else {
      if (discount.intValue() >= 100) {
        throw new InvalidInputException("Free sale is not allowed.");
      } else if (discount.intValue() > 30) {
        throw new IllegalArgumentException("Discount greater than 30% not allowed.");
      } else {
        operationResult =
            new OperationResult(
                operationRequest.basePrice(),
                discount,
                operationRequest.basePrice() * (100 - discount) / 100);
      }
    }
    return ResponseEntity.ok(operationResult);
  }

  public record OperationRequest(
      @NotNull(message = "Base price should be greater than zero.")
          @Positive(message = "Base price should be greater than zero.")
          Double basePrice,
      @Nullable @Positive(message = "Discount should be greater than zero when provided.")
          Double discount) {}

  public record OperationResult(
      @Positive(message = "Base price should be greater than zero.") Double basePrice,
      @Nullable @Positive(message = "Discount should be greater than zero when provided.")
          Double discount,
      @Nullable @Positive(message = "Selling price should be greater than zero.")
          Double sellingPrice) {}
}
