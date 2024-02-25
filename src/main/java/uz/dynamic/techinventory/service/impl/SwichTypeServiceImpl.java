package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.SwichType;
import uz.dynamic.techinventory.repository.SwichTypeRepository;
import uz.dynamic.techinventory.service.SwichTypeService;
import uz.dynamic.techinventory.service.dto.SwichTypeDTO;
import uz.dynamic.techinventory.service.mapper.SwichTypeMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.SwichType}.
 */
@Service
@Transactional
public class SwichTypeServiceImpl implements SwichTypeService {

    private final Logger log = LoggerFactory.getLogger(SwichTypeServiceImpl.class);

    private final SwichTypeRepository swichTypeRepository;

    private final SwichTypeMapper swichTypeMapper;

    public SwichTypeServiceImpl(SwichTypeRepository swichTypeRepository, SwichTypeMapper swichTypeMapper) {
        this.swichTypeRepository = swichTypeRepository;
        this.swichTypeMapper = swichTypeMapper;
    }

    @Override
    public SwichTypeDTO save(SwichTypeDTO swichTypeDTO) {
        log.debug("Request to save SwichType : {}", swichTypeDTO);
        SwichType swichType = swichTypeMapper.toEntity(swichTypeDTO);
        swichType = swichTypeRepository.save(swichType);
        return swichTypeMapper.toDto(swichType);
    }

    @Override
    public SwichTypeDTO update(SwichTypeDTO swichTypeDTO) {
        log.debug("Request to update SwichType : {}", swichTypeDTO);
        SwichType swichType = swichTypeMapper.toEntity(swichTypeDTO);
        swichType = swichTypeRepository.save(swichType);
        return swichTypeMapper.toDto(swichType);
    }

    @Override
    public Optional<SwichTypeDTO> partialUpdate(SwichTypeDTO swichTypeDTO) {
        log.debug("Request to partially update SwichType : {}", swichTypeDTO);

        return swichTypeRepository
            .findById(swichTypeDTO.getId())
            .map(existingSwichType -> {
                swichTypeMapper.partialUpdate(existingSwichType, swichTypeDTO);

                return existingSwichType;
            })
            .map(swichTypeRepository::save)
            .map(swichTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SwichTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SwichTypes");
        return swichTypeRepository.findAll(pageable).map(swichTypeMapper::toDto);
    }

    @Override
    public Page<SwichTypeDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all SwichTypes");
        return swichTypeRepository.findAllByObyektId(pageable, obyektId).map(swichTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SwichTypeDTO> findOne(Long id) {
        log.debug("Request to get SwichType : {}", id);
        return swichTypeRepository.findById(id).map(swichTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SwichType : {}", id);
        swichTypeRepository.deleteById(id);
    }
}
