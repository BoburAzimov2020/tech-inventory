package uz.dynamic.techinventory.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Camera;
import uz.dynamic.techinventory.repository.CameraRepository;
import uz.dynamic.techinventory.service.CameraService;
import uz.dynamic.techinventory.service.dto.CameraDTO;
import uz.dynamic.techinventory.service.mapper.CameraMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Camera}.
 */
@Service
@Transactional
public class CameraServiceImpl implements CameraService {

    private final Logger log = LoggerFactory.getLogger(CameraServiceImpl.class);

    private final CameraRepository cameraRepository;

    private final CameraMapper cameraMapper;

    public CameraServiceImpl(CameraRepository cameraRepository, CameraMapper cameraMapper) {
        this.cameraRepository = cameraRepository;
        this.cameraMapper = cameraMapper;
    }

    @Override
    public CameraDTO save(CameraDTO cameraDTO) {
        log.debug("Request to save Camera : {}", cameraDTO);
        Camera camera = cameraMapper.toEntity(cameraDTO);
        camera = cameraRepository.save(camera);
        return cameraMapper.toDto(camera);
    }

    @Override
    public CameraDTO update(CameraDTO cameraDTO) {
        log.debug("Request to update Camera : {}", cameraDTO);
        Camera camera = cameraMapper.toEntity(cameraDTO);
        camera = cameraRepository.save(camera);
        return cameraMapper.toDto(camera);
    }

    @Override
    public Optional<CameraDTO> partialUpdate(CameraDTO cameraDTO) {
        log.debug("Request to partially update Camera : {}", cameraDTO);

        return cameraRepository
                .findById(cameraDTO.getId())
                .map(existingCamera -> {
                    cameraMapper.partialUpdate(existingCamera, cameraDTO);

                    return existingCamera;
                })
                .map(cameraRepository::save)
                .map(cameraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CameraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cameras");
        return cameraRepository.findAll(pageable).map(cameraMapper::toDto);
    }

    @Override
    public Page<CameraDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all CameraTypes");
        return cameraRepository.findAllByObyektId(pageable, obyektId).map(cameraMapper::toDto);
    }

    @Override
    public Page<CameraDTO> findAllByCameraType(Pageable pageable, Long cameraTypeId) {
        log.debug("Request to get all Cameras");
        return cameraRepository.findAllByCameraTypeId(pageable, cameraTypeId).map(cameraMapper::toDto);
    }

    @Override
    public Page<CameraDTO> findAllByCameraBrand(Pageable pageable, Long cameraBrandId) {
        log.debug("Request to get all Cameras");
        return cameraRepository.findAllByCameraBrandId(pageable, cameraBrandId).map(cameraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CameraDTO> findOne(Long id) {
        log.debug("Request to get Camera : {}", id);
        return cameraRepository.findById(id).map(cameraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Camera : {}", id);
        cameraRepository.deleteById(id);
    }
}
