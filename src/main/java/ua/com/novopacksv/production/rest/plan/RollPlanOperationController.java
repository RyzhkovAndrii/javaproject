package ua.com.novopacksv.production.rest.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.plan.RollPlanOperationRequest;
import ua.com.novopacksv.production.dto.plan.RollPlanOperationResponse;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;
import ua.com.novopacksv.production.service.plan.RollPlanOperationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "roll-plan-operations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class RollPlanOperationController {

    private final RollPlanOperationService rollPlanOperationService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"roll_id", "from", "to"})
    public ResponseEntity<List<RollPlanOperationResponse>> getAll(@RequestParam("roll_id") Long rollTypeId,
                                                                  @RequestParam("from") LocalDate fromDate,
                                                                  @RequestParam("to") LocalDate toDate) {
        List<RollPlanOperation> rollPlanOperations = rollPlanOperationService.findAll(rollTypeId, fromDate, toDate);
        List<RollPlanOperationResponse> responses =
                conversionService.convert(rollPlanOperations, RollPlanOperationResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RollPlanOperationResponse> getOne(@PathVariable Long id) {
        RollPlanOperation rollPlanOperation = rollPlanOperationService.findById(id);
        RollPlanOperationResponse response =
                conversionService.convert(rollPlanOperation, RollPlanOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RollPlanOperationResponse>> getAll() {
        List<RollPlanOperation> rollPlanOperations = rollPlanOperationService.findAll();
        List<RollPlanOperationResponse> responses =
                conversionService.convert(rollPlanOperations, RollPlanOperationResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RollPlanOperationResponse> save(@RequestBody RollPlanOperationRequest request) {
        RollPlanOperation rollPlanOperation = conversionService.convert(request, RollPlanOperation.class);
        rollPlanOperationService.save(rollPlanOperation);
        RollPlanOperationResponse response =
                conversionService.convert(rollPlanOperation, RollPlanOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RollPlanOperationResponse> update(@PathVariable Long id,
                                                            @RequestBody RollPlanOperationRequest request) {
        RollPlanOperation rollPlanOperation = conversionService.convert(request, RollPlanOperation.class);
        rollPlanOperation.setId(id);
        rollPlanOperationService.update(rollPlanOperation);
        RollPlanOperationResponse response =
                conversionService.convert(rollPlanOperation, RollPlanOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rollPlanOperationService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}