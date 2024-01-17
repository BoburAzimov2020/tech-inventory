package uz.dynamic.techinventory.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.dynamic.techinventory.domain.ProjectorType;

/**
 * Spring Data JPA repository for the ProjectorType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectorTypeRepository extends JpaRepository<ProjectorType, Long> {}
