package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.CameraBrand;
import uz.dynamic.techinventory.repository.CameraBrandRepository;
import uz.dynamic.techinventory.service.CameraBrandService;
import uz.dynamic.techinventory.service.dto.CameraBrandDTO;
import uz.dynamic.techinventory.service.mapper.CameraBrandMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.CameraBrand}.
 */
@Service
@Transactional
public class CameraBrandServiceImpl implements CameraBrandService {

    private final Logger log = LoggerFactory.getLogger(CameraBrandServiceImpl.class);

    private final CameraBrandRepository cameraBrandRepository;

    private final CameraBrandMapper cameraBrandMapper;

    public CameraBrandServiceImpl(CameraBrandRepository cameraBrandRepository, CameraBrandMapper cameraBrandMapper) {
        this.cameraBrandRepository = cameraBrandRepository;
        this.cameraBrandMapper = cameraBrandMapper;
    }

    @Override
    public CameraBrandDTO save(CameraBrandDTO cameraBrandDTO) {
        log.debug("Request to save CameraBrand : {}", cameraBrandDTO);
        CameraBrand cameraBrand = cameraBrandMapper.toEntity(cameraBrandDTO);
        cameraBrand = cameraBrandRepository.save(cameraBrand);
        return cameraBrandMapper.toDto(cameraBrand);
    }

    @Override
    public CameraBrandDTO update(CameraBrandDTO cameraBrandDTO) {
        log.debug("Request to update CameraBrand : {}", cameraBrandDTO);
        CameraBrand cameraBrand = cameraBrandMapper.toEntity(cameraBrandDTO);
        cameraBrand = cameraBrandRepository.save(cameraBrand);
        return cameraBrandMapper.toDto(cameraBrand);
    }

    @Override
    public Optional<CameraBrandDTO> partialUpdate(CameraBrandDTO cameraBrandDTO) {
        log.debug("Request to partially update CameraBrand : {}", cameraBrandDTO);

        return cameraBrandRepository
            .findById(cameraBrandDTO.getId())
            .map(existingCameraBrand -> {
                cameraBrandMapper.partialUpdate(existingCameraBrand, cameraBrandDTO);

                return existingCameraBrand;
            })
            .map(cameraBrandRepository::save)
            .map(cameraBrandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CameraBrandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CameraBrands");
        return cameraBrandRepository.findAll(pageable).map(cameraBrandMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CameraBrandDTO> findOne(Long id) {
        log.debug("Request to get CameraBrand : {}", id);
        return cameraBrandRepository.findById(id).map(cameraBrandMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CameraBrand : {}", id);
        cameraBrandRepository.deleteById(id);
    }
}
