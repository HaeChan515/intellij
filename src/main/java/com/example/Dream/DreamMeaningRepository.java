package com.example.Dream;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DreamMeaningRepository extends JpaRepository<DreamMeaning, Long> {

    List<DreamMeaning> findByKeywordIn(List<String> keywords);
}
