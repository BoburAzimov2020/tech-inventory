package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Ups;
import uz.dynamic.techinventory.repository.UpsRepository;
import uz.dynamic.techinventory.service.UpsService;
import uz.dynamic.techinventory.service.dto.UpsDTO;
import uz.dynamic.techinventory.service.mapper.UpsMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Ups}.
 */
@Service
@Transactional
public class UpsServiceImpl implements UpsService {

    private final Logger log = LoggerFactory.getLogger(UpsServiceImpl.class);

    private final UpsRepository upsRepository;

    private final UpsMapper upsMapper;

    public UpsServiceImpl(UpsRepository upsRepository, UpsMapper upsMapper) {
        this.upsRepository = upsRepository;
        this.upsMapper = upsMapper;
    }

    @Override
    public UpsDTO save(UpsDTO upsDTO) {
        log.debug("Request to save Ups : {}", upsDTO);
        Ups ups = upsMapper.toEntity(upsDTO);
        ups = upsRepository.save(ups);
        return upsMapper.toDto(ups);
    }

    @Override
    public UpsDTO update(UpsDTO upsDTO) {
        log.debug("Request to update Ups : {}", upsDTO);
        Ups ups = upsMapper.toEntity(upsDTO);
        ups = upsRepository.save(ups);
        return upsMapper.toDto(ups);
    }

    @Override
    public Optional<UpsDTO> partialUpdate(UpsDTO upsDTO) {
        log.debug("Request to partially update Ups : {}", upsDTO);

        return upsRepository
            .findById(upsDTO.getId())
            .map(existingUps -> {
                upsMapper.partialUpdate(existingUps, upsDTO);

                return existingUps;
            })
            .map(upsRepository::save)
            .map(upsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UpsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ups");
        return upsRepository.findAll(pageable).map(upsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UpsDTO> findOne(Long id) {
        log.debug("Request to get Ups : {}", id);
        return upsRepository.findById(id).map(upsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ups : {}", id);
        upsRepository.deleteById(id);
    }
}
