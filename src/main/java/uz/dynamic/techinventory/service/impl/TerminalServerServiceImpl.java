package uz.dynamic.techinventory.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dynamic.techinventory.domain.TerminalServer;
import uz.dynamic.techinventory.repository.TerminalServerRepository;
import uz.dynamic.techinventory.service.TerminalServerService;
import uz.dynamic.techinventory.service.dto.TerminalServerDTO;
import uz.dynamic.techinventory.service.mapper.TerminalServerMapper;

/**
 * Service Implementation for managing {@link uz.dynamic.techinventory.domain.TerminalServer}.
 */
@Service
@Transactional
public class TerminalServerServiceImpl implements TerminalServerService {

    private final Logger log = LoggerFactory.getLogger(TerminalServerServiceImpl.class);

    private final TerminalServerRepository terminalServerRepository;

    private final TerminalServerMapper terminalServerMapper;

    public TerminalServerServiceImpl(TerminalServerRepository terminalServerRepository, TerminalServerMapper terminalServerMapper) {
        this.terminalServerRepository = terminalServerRepository;
        this.terminalServerMapper = terminalServerMapper;
    }

    @Override
    public TerminalServerDTO save(TerminalServerDTO terminalServerDTO) {
        log.debug("Request to save TerminalServer : {}", terminalServerDTO);
        TerminalServer terminalServer = terminalServerMapper.toEntity(terminalServerDTO);
        terminalServer = terminalServerRepository.save(terminalServer);
        return terminalServerMapper.toDto(terminalServer);
    }

    @Override
    public TerminalServerDTO update(TerminalServerDTO terminalServerDTO) {
        log.debug("Request to update TerminalServer : {}", terminalServerDTO);
        TerminalServer terminalServer = terminalServerMapper.toEntity(terminalServerDTO);
        terminalServer = terminalServerRepository.save(terminalServer);
        return terminalServerMapper.toDto(terminalServer);
    }

    @Override
    public Optional<TerminalServerDTO> partialUpdate(TerminalServerDTO terminalServerDTO) {
        log.debug("Request to partially update TerminalServer : {}", terminalServerDTO);

        return terminalServerRepository
            .findById(terminalServerDTO.getId())
            .map(existingTerminalServer -> {
                terminalServerMapper.partialUpdate(existingTerminalServer, terminalServerDTO);

                return existingTerminalServer;
            })
            .map(terminalServerRepository::save)
            .map(terminalServerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TerminalServerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TerminalServers");
        return terminalServerRepository.findAll(pageable).map(terminalServerMapper::toDto);
    }

    @Override
    public Page<TerminalServerDTO> findAllByObyekt(Pageable pageable, Long obyektId) {
        log.debug("Request to get all TerminalServers");
        return terminalServerRepository.findAllByObyektId(pageable, obyektId).map(terminalServerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TerminalServerDTO> findOne(Long id) {
        log.debug("Request to get TerminalServer : {}", id);
        return terminalServerRepository.findById(id).map(terminalServerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TerminalServer : {}", id);
        terminalServerRepository.deleteById(id);
    }
}
