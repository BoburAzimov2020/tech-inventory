package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Swich;
import uz.dynamic.techinventory.repository.SwichRepository;
import uz.dynamic.techinventory.service.SwichService;
import uz.dynamic.techinventory.service.dto.SwichDTO;
import uz.dynamic.techinventory.service.mapper.SwichMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Swich}.
 */
@Service
@Transactional
public class SwichServiceImpl implements SwichService {

    private final Logger log = LoggerFactory.getLogger(SwichServiceImpl.class);

    private final SwichRepository swichRepository;

    private final SwichMapper swichMapper;

    public SwichServiceImpl(SwichRepository swichRepository, SwichMapper swichMapper) {
        this.swichRepository = swichRepository;
        this.swichMapper = swichMapper;
    }

    @Override
    public SwichDTO save(SwichDTO swichDTO) {
        log.debug("Request to save Swich : {}", swichDTO);
        Swich swich = swichMapper.toEntity(swichDTO);
        swich = swichRepository.save(swich);
        return swichMapper.toDto(swich);
    }

    @Override
    public SwichDTO update(SwichDTO swichDTO) {
        log.debug("Request to update Swich : {}", swichDTO);
        Swich swich = swichMapper.toEntity(swichDTO);
        swich = swichRepository.save(swich);
        return swichMapper.toDto(swich);
    }

    @Override
    public Optional<SwichDTO> partialUpdate(SwichDTO swichDTO) {
        log.debug("Request to partially update Swich : {}", swichDTO);

        return swichRepository
            .findById(swichDTO.getId())
            .map(existingSwich -> {
                swichMapper.partialUpdate(existingSwich, swichDTO);

                return existingSwich;
            })
            .map(swichRepository::save)
            .map(swichMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SwichDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Swiches");
        return swichRepository.findAll(pageable).map(swichMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SwichDTO> findOne(Long id) {
        log.debug("Request to get Swich : {}", id);
        return swichRepository.findById(id).map(swichMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Swich : {}", id);
        swichRepository.deleteById(id);
    }
}
