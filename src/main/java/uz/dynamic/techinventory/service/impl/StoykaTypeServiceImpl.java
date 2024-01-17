package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.StoykaType;
import uz.dynamic.techinventory.repository.StoykaTypeRepository;
import uz.dynamic.techinventory.service.StoykaTypeService;
import uz.dynamic.techinventory.service.dto.StoykaTypeDTO;
import uz.dynamic.techinventory.service.mapper.StoykaTypeMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.StoykaType}.
 */
@Service
@Transactional
public class StoykaTypeServiceImpl implements StoykaTypeService {

    private final Logger log = LoggerFactory.getLogger(StoykaTypeServiceImpl.class);

    private final StoykaTypeRepository stoykaTypeRepository;

    private final StoykaTypeMapper stoykaTypeMapper;

    public StoykaTypeServiceImpl(StoykaTypeRepository stoykaTypeRepository, StoykaTypeMapper stoykaTypeMapper) {
        this.stoykaTypeRepository = stoykaTypeRepository;
        this.stoykaTypeMapper = stoykaTypeMapper;
    }

    @Override
    public StoykaTypeDTO save(StoykaTypeDTO stoykaTypeDTO) {
        log.debug("Request to save StoykaType : {}", stoykaTypeDTO);
        StoykaType stoykaType = stoykaTypeMapper.toEntity(stoykaTypeDTO);
        stoykaType = stoykaTypeRepository.save(stoykaType);
        return stoykaTypeMapper.toDto(stoykaType);
    }

    @Override
    public StoykaTypeDTO update(StoykaTypeDTO stoykaTypeDTO) {
        log.debug("Request to update StoykaType : {}", stoykaTypeDTO);
        StoykaType stoykaType = stoykaTypeMapper.toEntity(stoykaTypeDTO);
        stoykaType = stoykaTypeRepository.save(stoykaType);
        return stoykaTypeMapper.toDto(stoykaType);
    }

    @Override
    public Optional<StoykaTypeDTO> partialUpdate(StoykaTypeDTO stoykaTypeDTO) {
        log.debug("Request to partially update StoykaType : {}", stoykaTypeDTO);

        return stoykaTypeRepository
            .findById(stoykaTypeDTO.getId())
            .map(existingStoykaType -> {
                stoykaTypeMapper.partialUpdate(existingStoykaType, stoykaTypeDTO);

                return existingStoykaType;
            })
            .map(stoykaTypeRepository::save)
            .map(stoykaTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StoykaTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StoykaTypes");
        return stoykaTypeRepository.findAll(pageable).map(stoykaTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StoykaTypeDTO> findOne(Long id) {
        log.debug("Request to get StoykaType : {}", id);
        return stoykaTypeRepository.findById(id).map(stoykaTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StoykaType : {}", id);
        stoykaTypeRepository.deleteById(id);
    }
}
