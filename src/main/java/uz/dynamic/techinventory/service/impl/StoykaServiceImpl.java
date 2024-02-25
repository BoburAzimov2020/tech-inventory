package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Stoyka;
import uz.dynamic.techinventory.repository.StoykaRepository;
import uz.dynamic.techinventory.service.StoykaService;
import uz.dynamic.techinventory.service.dto.StoykaDTO;
import uz.dynamic.techinventory.service.mapper.StoykaMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Stoyka}.
 */
@Service
@Transactional
public class StoykaServiceImpl implements StoykaService {

    private final Logger log = LoggerFactory.getLogger(StoykaServiceImpl.class);

    private final StoykaRepository stoykaRepository;

    private final StoykaMapper stoykaMapper;

    public StoykaServiceImpl(StoykaRepository stoykaRepository, StoykaMapper stoykaMapper) {
        this.stoykaRepository = stoykaRepository;
        this.stoykaMapper = stoykaMapper;
    }

    @Override
    public StoykaDTO save(StoykaDTO stoykaDTO) {
        log.debug("Request to save Stoyka : {}", stoykaDTO);
        Stoyka stoyka = stoykaMapper.toEntity(stoykaDTO);
        stoyka = stoykaRepository.save(stoyka);
        return stoykaMapper.toDto(stoyka);
    }

    @Override
    public StoykaDTO update(StoykaDTO stoykaDTO) {
        log.debug("Request to update Stoyka : {}", stoykaDTO);
        Stoyka stoyka = stoykaMapper.toEntity(stoykaDTO);
        stoyka = stoykaRepository.save(stoyka);
        return stoykaMapper.toDto(stoyka);
    }

    @Override
    public Optional<StoykaDTO> partialUpdate(StoykaDTO stoykaDTO) {
        log.debug("Request to partially update Stoyka : {}", stoykaDTO);

        return stoykaRepository
            .findById(stoykaDTO.getId())
            .map(existingStoyka -> {
                stoykaMapper.partialUpdate(existingStoyka, stoykaDTO);

                return existingStoyka;
            })
            .map(stoykaRepository::save)
            .map(stoykaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoykaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Stoykas");
        return stoykaRepository.findAll(pageable).map(stoykaMapper::toDto);
    }

    @Override
    public Page<StoykaDTO> findAllByStoykaType(Pageable pageable, Long stoykaTypeId) {
        log.debug("Request to get all Stoykas");
        return stoykaRepository.findAllByStoykaTypeId(pageable, stoykaTypeId).map(stoykaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoykaDTO> findOne(Long id) {
        log.debug("Request to get Stoyka : {}", id);
        return stoykaRepository.findById(id).map(stoykaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stoyka : {}", id);
        stoykaRepository.deleteById(id);
    }
}
