package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Stabilizator;
import uz.dynamic.techinventory.repository.StabilizatorRepository;
import uz.dynamic.techinventory.service.StabilizatorService;
import uz.dynamic.techinventory.service.dto.StabilizatorDTO;
import uz.dynamic.techinventory.service.mapper.StabilizatorMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Stabilizator}.
 */
@Service
@Transactional
public class StabilizatorServiceImpl implements StabilizatorService {

    private final Logger log = LoggerFactory.getLogger(StabilizatorServiceImpl.class);

    private final StabilizatorRepository stabilizatorRepository;

    private final StabilizatorMapper stabilizatorMapper;

    public StabilizatorServiceImpl(StabilizatorRepository stabilizatorRepository, StabilizatorMapper stabilizatorMapper) {
        this.stabilizatorRepository = stabilizatorRepository;
        this.stabilizatorMapper = stabilizatorMapper;
    }

    @Override
    public StabilizatorDTO save(StabilizatorDTO stabilizatorDTO) {
        log.debug("Request to save Stabilizator : {}", stabilizatorDTO);
        Stabilizator stabilizator = stabilizatorMapper.toEntity(stabilizatorDTO);
        stabilizator = stabilizatorRepository.save(stabilizator);
        return stabilizatorMapper.toDto(stabilizator);
    }

    @Override
    public StabilizatorDTO update(StabilizatorDTO stabilizatorDTO) {
        log.debug("Request to update Stabilizator : {}", stabilizatorDTO);
        Stabilizator stabilizator = stabilizatorMapper.toEntity(stabilizatorDTO);
        stabilizator = stabilizatorRepository.save(stabilizator);
        return stabilizatorMapper.toDto(stabilizator);
    }

    @Override
    public Optional<StabilizatorDTO> partialUpdate(StabilizatorDTO stabilizatorDTO) {
        log.debug("Request to partially update Stabilizator : {}", stabilizatorDTO);

        return stabilizatorRepository
            .findById(stabilizatorDTO.getId())
            .map(existingStabilizator -> {
                stabilizatorMapper.partialUpdate(existingStabilizator, stabilizatorDTO);

                return existingStabilizator;
            })
            .map(stabilizatorRepository::save)
            .map(stabilizatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StabilizatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stabilizators");
        return stabilizatorRepository.findAll(pageable).map(stabilizatorMapper::toDto);
    }

    @Override
    public Page<StabilizatorDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all Stabilizators");
        return stabilizatorRepository.findAllByObyektId(pageable, obyektId).map(stabilizatorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StabilizatorDTO> findOne(Long id) {
        log.debug("Request to get Stabilizator : {}", id);
        return stabilizatorRepository.findById(id).map(stabilizatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stabilizator : {}", id);
        stabilizatorRepository.deleteById(id);
    }
}
