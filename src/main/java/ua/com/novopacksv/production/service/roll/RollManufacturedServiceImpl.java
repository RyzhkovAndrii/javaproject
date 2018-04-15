package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollManufacturedRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RollManufacturedServiceImpl implements RollManufacturedService {

    private final static Integer PERIOD_FOR_CHECK_READY_TO_USE = 7;

    private final RollManufacturedRepository rollManufacturedRepository;

    private final RollOperationService rollOperationService;

    private final ConversionService conversionService;

    private boolean isReadyToUse(LocalDate manufacturedDate) {
        LocalDate now = LocalDate.now();
        return manufacturedDate
                .plusDays(RollType.READY_TO_USE_PERIOD - 1)
                .isBefore(now);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void rollsBecomeReadyToUseForNow() {
        findAllShouldBeReadyToUseNow().forEach(rollManufactured -> {
            rollManufactured.setReadyToUse(true);
            rollManufacturedRepository.save(rollManufactured);
        });
    }

    private List<RollManufactured> findAllShouldBeReadyToUseNow() {
        LocalDate manufacturedDate = LocalDate.now().minusDays(RollType.READY_TO_USE_PERIOD);
        return rollManufacturedRepository.findAllByManufacturedDateBetweenAndReadyToUseIsFalse(
                manufacturedDate.minusDays(PERIOD_FOR_CHECK_READY_TO_USE),
                manufacturedDate);
    }

    @Override
    public RollManufactured findOne(LocalDate manufacturedDate, RollType rollType) throws ResourceNotFoundException {
        return rollManufacturedRepository.findByManufacturedDateAndRollType(manufacturedDate, rollType)
                .orElseThrow(() -> {
                    String formatDate = conversionService.convert(manufacturedDate, String.class);
                    String message = String.format("Roll manufactured whit roll type id = %d" +
                            " and manufactured date = %s is not found!", rollType.getId(), formatDate);
                    return new ResourceNotFoundException(message);
                });
    }

    @Override
    public RollManufactured findOneOrCreate(LocalDate manufacturedDate, RollType rollType) {
        return rollManufacturedRepository.findByManufacturedDateAndRollType(manufacturedDate, rollType)
                .orElseGet(() -> {
                    RollManufactured rollManufactured = new RollManufactured();
                    rollManufactured.setManufacturedDate(manufacturedDate);
                    rollManufactured.setRollType(rollType);
                    rollManufactured.setReadyToUse(isReadyToUse(manufacturedDate));
                    rollManufacturedRepository.save(rollManufactured); // TODO need save??? or hibernate save it when save roll operation
                    return rollManufactured;
                });
    }

    @Override
    public List<RollManufactured> findAll(LocalDate manufacturedDate) {
        return rollManufacturedRepository.findAllByManufacturedDate(manufacturedDate);
    }

    @Override
    public List<RollManufactured> findAll(LocalDate fromManufacturedDate, LocalDate toManufacturedDate) {
        return rollManufacturedRepository.findAllByManufacturedDateBetween(fromManufacturedDate, toManufacturedDate);
    }

    @Override
    public List<RollManufactured> findAll(LocalDate fromManufacturedDate, LocalDate toManufacturedDate, RollType rollType) {
        return rollManufacturedRepository
                .findAllByManufacturedDateBetweenAndRollType(fromManufacturedDate, fromManufacturedDate, rollType);
    }

    @Override
    public Integer getManufacturedRollAmount(RollManufactured rollManufactured) {
        return rollOperationService.getAllManufacturedOperationsByRollManufactured(rollManufactured)
                .stream()
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

    @Override
    public Integer getUsedRollAmount(RollManufactured rollManufactured) {
        return rollOperationService.getAllUsedOperationsByRollManufactured(rollManufactured)
                .stream()
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

}