package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.CabelType;
import uz.dynamic.techinventory.repository.CabelTypeRepository;
import uz.dynamic.techinventory.service.CabelTypeService;
import uz.dynamic.techinventory.service.dto.CabelTypeDTO;
import uz.dynamic.techinventory.service.mapper.CabelTypeMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.CabelType}.
 */
@Service
@Transactional
public class CabelTypeServiceImpl implements CabelTypeService {

    private final Logger log = LoggerFactory.getLogger(CabelTypeServiceImpl.class);

    private final CabelTypeRepository cabelTypeRepository;

    private final CabelTypeMapper cabelTypeMapper;

    public CabelTypeServiceImpl(CabelTypeRepository cabelTypeRepository, CabelTypeMapper cabelTypeMapper) {
        this.cabelTypeRepository = cabelTypeRepository;
        this.cabelTypeMapper = cabelTypeMapper;
    }

    @Override
    public CabelTypeDTO save(CabelTypeDTO cabelTypeDTO) {
        log.debug("Request to save CabelType : {}", cabelTypeDTO);
        CabelType cabelType = cabelTypeMapper.toEntity(cabelTypeDTO);
        cabelType = cabelTypeRepository.save(cabelType);
        return cabelTypeMapper.toDto(cabelType);
    }

    @Override
    public CabelTypeDTO update(CabelTypeDTO cabelTypeDTO) {
        log.debug("Request to update CabelType : {}", cabelTypeDTO);
        CabelType cabelType = cabelTypeMapper.toEntity(cabelTypeDTO);
        cabelType = cabelTypeRepository.save(cabelType);
        return cabelTypeMapper.toDto(cabelType);
    }

    @Override
    public Optional<CabelTypeDTO> partialUpdate(CabelTypeDTO cabelTypeDTO) {
        log.debug("Request to partially update CabelType : {}", cabelTypeDTO);

        return cabelTypeRepository
            .findById(cabelTypeDTO.getId())
            .map(existingCabelType -> {
                cabelTypeMapper.partialUpdate(existingCabelType, cabelTypeDTO);

                return existingCabelType;
            })
            .map(cabelTypeRepository::save)
            .map(cabelTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CabelTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CabelTypes");
        return cabelTypeRepository.findAll(pageable).map(cabelTypeMapper::toDto);
    }

    @Override
    public Page<CabelTypeDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all CabelTypes");
        return cabelTypeRepository.findAllByObyektId(pageable, obyektId).map(cabelTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CabelTypeDTO> findOne(Long id) {
        log.debug("Request to get CabelType : {}", id);
        return cabelTypeRepository.findById(id).map(cabelTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CabelType : {}", id);
        cabelTypeRepository.deleteById(id);
    }
}
