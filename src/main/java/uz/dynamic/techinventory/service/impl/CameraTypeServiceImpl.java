package uz.dynamic.techinventory.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.CameraType;
import uz.dynamic.techinventory.repository.CameraTypeRepository;
import uz.dynamic.techinventory.service.CameraTypeService;
import uz.dynamic.techinventory.service.dto.CameraTypeDTO;
import uz.dynamic.techinventory.service.mapper.CameraTypeMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.CameraType}.
 */
@Service
@Transactional
public class CameraTypeServiceImpl implements CameraTypeService {

    private final Logger log = LoggerFactory.getLogger(CameraTypeServiceImpl.class);

    private final CameraTypeRepository cameraTypeRepository;

    private final CameraTypeMapper cameraTypeMapper;

    public CameraTypeServiceImpl(CameraTypeRepository cameraTypeRepository, CameraTypeMapper cameraTypeMapper) {
        this.cameraTypeRepository = cameraTypeRepository;
        this.cameraTypeMapper = cameraTypeMapper;
    }

    @Override
    public CameraTypeDTO save(CameraTypeDTO cameraTypeDTO) {
        log.debug("Request to save CameraType : {}", cameraTypeDTO);
        CameraType cameraType = cameraTypeMapper.toEntity(cameraTypeDTO);
        cameraType = cameraTypeRepository.save(cameraType);
        return cameraTypeMapper.toDto(cameraType);
    }

    @Override
    public CameraTypeDTO update(CameraTypeDTO cameraTypeDTO) {
        log.debug("Request to update CameraType : {}", cameraTypeDTO);
        CameraType cameraType = cameraTypeMapper.toEntity(cameraTypeDTO);
        cameraType = cameraTypeRepository.save(cameraType);
        return cameraTypeMapper.toDto(cameraType);
    }

    @Override
    public Optional<CameraTypeDTO> partialUpdate(CameraTypeDTO cameraTypeDTO) {
        log.debug("Request to partially update CameraType : {}", cameraTypeDTO);

        return cameraTypeRepository
                .findById(cameraTypeDTO.getId())
                .map(existingCameraType -> {
                    cameraTypeMapper.partialUpdate(existingCameraType, cameraTypeDTO);

                    return existingCameraType;
                })
                .map(cameraTypeRepository::save)
                .map(cameraTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CameraTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CameraTypes");
        return cameraTypeRepository.findAll(pageable).map(cameraTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CameraTypeDTO> findOne(Long id) {
        log.debug("Request to get CameraType : {}", id);
        return cameraTypeRepository.findById(id).map(cameraTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CameraType : {}", id);
        cameraTypeRepository.deleteById(id);
    }
}
