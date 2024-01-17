package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.SvitaforDetector;
import uz.dynamic.techinventory.repository.SvitaforDetectorRepository;
import uz.dynamic.techinventory.service.SvitaforDetectorService;
import uz.dynamic.techinventory.service.dto.SvitaforDetectorDTO;
import uz.dynamic.techinventory.service.mapper.SvitaforDetectorMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.SvitaforDetector}.
 */
@Service
@Transactional
public class SvitaforDetectorServiceImpl implements SvitaforDetectorService {

    private final Logger log = LoggerFactory.getLogger(SvitaforDetectorServiceImpl.class);

    private final SvitaforDetectorRepository svitaforDetectorRepository;

    private final SvitaforDetectorMapper svitaforDetectorMapper;

    public SvitaforDetectorServiceImpl(
        SvitaforDetectorRepository svitaforDetectorRepository,
        SvitaforDetectorMapper svitaforDetectorMapper
    ) {
        this.svitaforDetectorRepository = svitaforDetectorRepository;
        this.svitaforDetectorMapper = svitaforDetectorMapper;
    }

    @Override
    public SvitaforDetectorDTO save(SvitaforDetectorDTO svitaforDetectorDTO) {
        log.debug("Request to save SvitaforDetector : {}", svitaforDetectorDTO);
        SvitaforDetector svitaforDetector = svitaforDetectorMapper.toEntity(svitaforDetectorDTO);
        svitaforDetector = svitaforDetectorRepository.save(svitaforDetector);
        return svitaforDetectorMapper.toDto(svitaforDetector);
    }

    @Override
    public SvitaforDetectorDTO update(SvitaforDetectorDTO svitaforDetectorDTO) {
        log.debug("Request to update SvitaforDetector : {}", svitaforDetectorDTO);
        SvitaforDetector svitaforDetector = svitaforDetectorMapper.toEntity(svitaforDetectorDTO);
        svitaforDetector = svitaforDetectorRepository.save(svitaforDetector);
        return svitaforDetectorMapper.toDto(svitaforDetector);
    }

    @Override
    public Optional<SvitaforDetectorDTO> partialUpdate(SvitaforDetectorDTO svitaforDetectorDTO) {
        log.debug("Request to partially update SvitaforDetector : {}", svitaforDetectorDTO);

        return svitaforDetectorRepository
            .findById(svitaforDetectorDTO.getId())
            .map(existingSvitaforDetector -> {
                svitaforDetectorMapper.partialUpdate(existingSvitaforDetector, svitaforDetectorDTO);

                return existingSvitaforDetector;
            })
            .map(svitaforDetectorRepository::save)
            .map(svitaforDetectorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SvitaforDetectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SvitaforDetectors");
        return svitaforDetectorRepository.findAll(pageable).map(svitaforDetectorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SvitaforDetectorDTO> findOne(Long id) {
        log.debug("Request to get SvitaforDetector : {}", id);
        return svitaforDetectorRepository.findById(id).map(svitaforDetectorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SvitaforDetector : {}", id);
        svitaforDetectorRepository.deleteById(id);
    }
}
