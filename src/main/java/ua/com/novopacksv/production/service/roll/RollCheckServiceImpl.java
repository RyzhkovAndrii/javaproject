package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollCheck;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollCheckRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RollCheckServiceImpl implements RollCheckService {

    private final RollCheckRepository rollCheckRepository;

    private final RollTypeService rollTypeService;

    @Override
    @Transactional(readOnly = true)
    public RollCheck findOneByRollTypeId(Long rollTypeId) {
        return rollCheckRepository.findByRollType_Id(rollTypeId).orElseThrow(() -> {
            String message = String.format("Roll check whit roll type id = %d is not found!", rollTypeId);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollCheck> findAll() {
        return rollCheckRepository.findAll();
    }

    @Override
    public RollCheck update(RollCheck rollCheck) {
        RollType rollType = rollTypeService.findById(rollCheck.getId());
        rollCheck.setRollType(rollType);
        return rollCheckRepository.save(rollCheck);
    }

}