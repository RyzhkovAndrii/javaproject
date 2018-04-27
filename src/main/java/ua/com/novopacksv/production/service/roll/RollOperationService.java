package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RollOperationService extends BaseEntityService<RollOperation> {

    List<RollOperation> getAllManufacturedOperationsByRollManufactured(RollManufactured rollManufactured);

    List<RollOperation> getAllUsedOperationsByRollManufactured(RollManufactured rollManufactured);

    List<RollOperation> findAllByRollTypeAndManufacturedDateBetween(RollType rollType, LocalDate fromDate, LocalDate toDate);

    Boolean isItManufactureOperation(RollOperation rollOperation);

}