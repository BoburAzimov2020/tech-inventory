package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.ShelfType;
import uz.dynamic.techinventory.repository.ShelfTypeRepository;
import uz.dynamic.techinventory.service.ShelfTypeService;
import uz.dynamic.techinventory.service.dto.ShelfTypeDTO;
import uz.dynamic.techinventory.service.mapper.ShelfTypeMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.ShelfType}.
 */
@Service
@Transactional
public class ShelfTypeServiceImpl implements ShelfTypeService {

    private final Logger log = LoggerFactory.getLogger(ShelfTypeServiceImpl.class);

    private final ShelfTypeRepository shelfTypeRepository;

    private final ShelfTypeMapper shelfTypeMapper;

    public ShelfTypeServiceImpl(ShelfTypeRepository shelfTypeRepository, ShelfTypeMapper shelfTypeMapper) {
        this.shelfTypeRepository = shelfTypeRepository;
        this.shelfTypeMapper = shelfTypeMapper;
    }

    @Override
    public ShelfTypeDTO save(ShelfTypeDTO shelfTypeDTO) {
        log.debug("Request to save ShelfType : {}", shelfTypeDTO);
        ShelfType shelfType = shelfTypeMapper.toEntity(shelfTypeDTO);
        shelfType = shelfTypeRepository.save(shelfType);
        return shelfTypeMapper.toDto(shelfType);
    }

    @Override
    public ShelfTypeDTO update(ShelfTypeDTO shelfTypeDTO) {
        log.debug("Request to update ShelfType : {}", shelfTypeDTO);
        ShelfType shelfType = shelfTypeMapper.toEntity(shelfTypeDTO);
        shelfType = shelfTypeRepository.save(shelfType);
        return shelfTypeMapper.toDto(shelfType);
    }

    @Override
    public Optional<ShelfTypeDTO> partialUpdate(ShelfTypeDTO shelfTypeDTO) {
        log.debug("Request to partially update ShelfType : {}", shelfTypeDTO);

        return shelfTypeRepository
            .findById(shelfTypeDTO.getId())
            .map(existingShelfType -> {
                shelfTypeMapper.partialUpdate(existingShelfType, shelfTypeDTO);

                return existingShelfType;
            })
            .map(shelfTypeRepository::save)
            .map(shelfTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShelfTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShelfTypes");
        return shelfTypeRepository.findAll(pageable).map(shelfTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShelfTypeDTO> findOne(Long id) {
        log.debug("Request to get ShelfType : {}", id);
        return shelfTypeRepository.findById(id).map(shelfTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShelfType : {}", id);
        shelfTypeRepository.deleteById(id);
    }
}
