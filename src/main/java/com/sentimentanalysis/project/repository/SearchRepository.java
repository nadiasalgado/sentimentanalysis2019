package com.sentimentanalysis.project.repository;

import com.sentimentanalysis.project.domain.Search;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Search entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

}
