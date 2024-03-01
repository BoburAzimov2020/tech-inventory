package uz.dynamic.techinventory.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.Obyekt;
import uz.dynamic.techinventory.repository.*;
import uz.dynamic.techinventory.repository.specification.ObyektSpecification;
import uz.dynamic.techinventory.service.ObyektService;
import uz.dynamic.techinventory.service.dto.ObyektDTO;
import uz.dynamic.techinventory.service.dto.ObyektFilterDTO;
import uz.dynamic.techinventory.service.mapper.ObyektMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.Obyekt}.
 */
@Service
@Transactional
public class ObyektServiceImpl implements ObyektService {

    private final Logger log = LoggerFactory.getLogger(ObyektServiceImpl.class);

    private final ObyektRepository obyektRepository;
    private final ObyektMapper obyektMapper;
    private final ObyektSpecification obyektSpecification;
    private final AkumulatorRepository akumulatorRepository;
    private final AvtomatRepository avtomatRepository;
    private final CabelRepository cabelRepository;
    private final CameraRepository cameraRepository;
    private final ProjectorRepository projectorRepository;
    private final RozetkaRepository rozetkaRepository;
    private final ShelfRepository shelfRepository;
    private final StabilizatorRepository stabilizatorRepository;
    private final StoykaRepository stoykaRepository;
    private final SvitaforDetectorRepository svitaforDetectorRepository;
    private final SwichRepository swichRepository;
    private final TerminalServerRepository terminalServerRepository;
    private final UpsRepository upsRepository;

    public ObyektServiceImpl(ObyektRepository obyektRepository, ObyektMapper obyektMapper,
                             ObyektSpecification obyektSpecification, AkumulatorRepository akumulatorRepository,
                             AvtomatRepository avtomatRepository, CabelRepository cabelRepository,
                             CameraRepository cameraRepository, ProjectorRepository projectorRepository,
                             RozetkaRepository rozetkaRepository, ShelfRepository shelfRepository,
                             StabilizatorRepository stabilizatorRepository, StoykaRepository stoykaRepository,
                             SvitaforDetectorRepository svitaforDetectorRepository, SwichRepository swichRepository,
                             TerminalServerRepository terminalServerRepository, UpsRepository upsRepository) {
        this.obyektRepository = obyektRepository;
        this.obyektMapper = obyektMapper;
        this.obyektSpecification = obyektSpecification;
        this.akumulatorRepository = akumulatorRepository;
        this.avtomatRepository = avtomatRepository;
        this.cabelRepository = cabelRepository;
        this.cameraRepository = cameraRepository;
        this.projectorRepository = projectorRepository;
        this.rozetkaRepository = rozetkaRepository;
        this.shelfRepository = shelfRepository;
        this.stabilizatorRepository = stabilizatorRepository;
        this.stoykaRepository = stoykaRepository;
        this.svitaforDetectorRepository = svitaforDetectorRepository;
        this.swichRepository = swichRepository;
        this.terminalServerRepository = terminalServerRepository;
        this.upsRepository = upsRepository;
    }

    @Override
    public ObyektDTO save(ObyektDTO obyektDTO) {
        log.debug("Request to save Obyekt : {}", obyektDTO);
        Obyekt obyekt = obyektMapper.toEntity(obyektDTO);
        obyekt = obyektRepository.save(obyekt);
        return obyektMapper.toDto(obyekt);
    }

    @Override
    public ObyektDTO update(ObyektDTO obyektDTO) {
        log.debug("Request to update Obyekt : {}", obyektDTO);
        Obyekt obyekt = obyektMapper.toEntity(obyektDTO);
        obyekt = obyektRepository.save(obyekt);
        return obyektMapper.toDto(obyekt);
    }

    @Override
    public Optional<ObyektDTO> partialUpdate(ObyektDTO obyektDTO) {
        log.debug("Request to partially update Obyekt : {}", obyektDTO);

        return obyektRepository
                .findById(obyektDTO.getId())
                .map(existingObyekt -> {
                    obyektMapper.partialUpdate(existingObyekt, obyektDTO);

                    return existingObyekt;
                })
                .map(obyektRepository::save)
                .map(obyektMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObyektDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Obyekts");
        return obyektRepository.findAll(pageable).map(obyektMapper::toDto);
    }

    @Override
    public Page<ObyektDTO> findAllByBuyurtmaRaqamId(Pageable pageable, Long buyurtmaRaqamId) {
        log.debug("Request to get all Obyekts");
        return obyektRepository.findAllByBuyurtmaRaqamId(pageable, buyurtmaRaqamId).map(obyektMapper::toDto);
    }

    @Override
    public Page<ObyektDTO> findAllByFilter(Pageable pageable, ObyektFilterDTO obyektFilterDTO) {
        return obyektRepository.findAll(obyektSpecification.getObyekts(obyektFilterDTO), pageable).map(
                obyektMapper::toDto);
    }

    @Override
    public Map<String, Integer> getCountOfModelsByObyekt(Long obyektId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Akumulator", akumulatorRepository.countByObyektId(obyektId));
        map.put("Avtomat", avtomatRepository.countByObyektId(obyektId));
        map.put("Cabel", cabelRepository.countByObyektId(obyektId));
        map.put("Camera", cameraRepository.countByObyektId(obyektId));
        map.put("Projector", projectorRepository.countByObyektId(obyektId));
        map.put("Rozetka", rozetkaRepository.countByObyektId(obyektId));
        map.put("Shelf", shelfRepository.countByObyektId(obyektId));
        map.put("Stabilizator", stabilizatorRepository.countByObyektId(obyektId));
        map.put("Stoyka", stoykaRepository.countByObyektId(obyektId));
        map.put("SvitaforDetector", svitaforDetectorRepository.countByObyektId(obyektId));
        map.put("TerminalServer", terminalServerRepository.countByObyektId(obyektId));
        map.put("UPS", upsRepository.countByObyektId(obyektId));
        map.put("Switch", swichRepository.countByObyektId(obyektId));
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObyektDTO> findOne(Long id) {
        log.debug("Request to get Obyekt : {}", id);
        return obyektRepository.findById(id).map(obyektMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Obyekt : {}", id);
        obyektRepository.deleteById(id);
    }
}
