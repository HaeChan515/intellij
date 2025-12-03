package com.example.Dream;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DreamService {

    private final DreamRepository dreamRepository;
    private final KeywordRepository keywordRepository;
    private final DreamMeaningRepository dreamMeaningRepository;

    public DreamService(DreamRepository dreamRepository,
                        KeywordRepository keywordRepository,
                        DreamMeaningRepository dreamMeaningRepository) {
        this.dreamRepository = dreamRepository;
        this.keywordRepository = keywordRepository;
        this.dreamMeaningRepository = dreamMeaningRepository;
    }

    // 👉 공통 해몽 생성 로직 (기존 createDream 안에 있던 if/키워드 로직을 여기로 옮기면 됨)
    private String generateResult(String content) {

        // 1. 키워드 추출
        List<String> foundKeywords = keywordRepository.findAll().stream()
                .map(Keyword::getName)
                .filter(content::contains)
                .collect(Collectors.toList());

        // 2. 의미 조회
        List<DreamMeaning> meanings = foundKeywords.isEmpty()
                ? List.of()
                : dreamMeaningRepository.findByKeywordIn(foundKeywords);

        // 3. 문장 조합
        StringBuilder sb = new StringBuilder();

        if (!meanings.isEmpty()) {
            for (DreamMeaning m : meanings) {
                sb.append(m.getDescription()).append(" ");
            }
        } else {
            sb.append("특정 키워드보다 전반적인 심리 상태를 반영한 꿈으로 해석됩니다. ");
        }

        sb.append("꿈 해몽은 절대적인 예언이 아니라, 자신을 돌아보는 참고 자료로 활용해 주세요.");

        return sb.toString();
    }

    // C: 생성 + 해몽
    public Dream createDream(String content) {
        String result = generateResult(content);

        Dream dream = new Dream();
        dream.setContent(content);
        dream.setResultText(result);
        dream.setCreatedAt(LocalDateTime.now());

        return dreamRepository.save(dream);
    }

    // R: 목록 조회
    public List<Dream> getDreamList() {
        return dreamRepository.findAll();
    }

    // R: 단건 상세 조회
    public Dream findById(Long id) {
        return dreamRepository.findById(id).orElse(null);
    }

    // U: 수정
    public Dream updateDream(Long id, String newContent) {
        Dream dream = findById(id);
        if (dream == null) {
            return null;
        }

        String result = generateResult(newContent);

        dream.setContent(newContent);
        dream.setResultText(result);
        // 수정 시간도 따로 두고 싶으면 필드를 하나 더 만들어도 됨

        return dreamRepository.save(dream);
    }

    // D: 삭제
    public void deleteDream(Long id) {
        dreamRepository.deleteById(id);
    }
}
