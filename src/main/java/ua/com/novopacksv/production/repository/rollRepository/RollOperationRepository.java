package ua.com.novopacksv.production.repository.rollRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.rollModel.OperationType;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RollOperationRepository extends JpaRepository <RollOperation, Long> {

    List<RollOperation> findAllByRollManufactured_RollTypeAndRollManufactured_ManufacturedDateBetween
            (RollType rollType, LocalDate fromDate, LocalDate toDate);

    List<RollOperation> findAllByRollManufactured_RollType(RollType rollType);

    List<RollOperation> findAllByOperationTypeAndRollManufactured(OperationType operationType, RollManufactured rollManufactured);
}
