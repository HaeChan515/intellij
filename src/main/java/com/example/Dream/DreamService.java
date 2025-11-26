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

    // 꿈 생성 + 자동 해몽 + DB 저장
    public Dream createDream(String content) {

        // 1. 키워드 추출 (DB에 있는 키워드 중, 실제로 content에 포함된 것만)
        List<String> foundKeywords = keywordRepository.findAll().stream()
                .map(Keyword::getName)
                .filter(content::contains)
                .collect(Collectors.toList());

        // 2. 키워드에 해당하는 해몽 의미 조회
        List<DreamMeaning> meanings = foundKeywords.isEmpty()
                ? List.of()
                : dreamMeaningRepository.findByKeywordIn(foundKeywords);

        // 3. 해몽 문장 조합
        StringBuilder sb = new StringBuilder();

        if (!meanings.isEmpty()) {
            for (DreamMeaning m : meanings) {
                sb.append(m.getDescription()).append(" ");
            }
        } else {
            sb.append("특정 키워드보다 전반적인 심리 상태를 반영한 꿈으로 해석됩니다. ");
        }

        sb.append("꿈 해몽은 절대적인 예언이 아니라, 자신을 돌아보는 참고 자료로 활용해 주세요.");

        // 4. Dream 엔티티 저장
        Dream dream = new Dream();
        dream.setContent(content);
        dream.setResultText(sb.toString());
        dream.setCreatedAt(LocalDateTime.now());

        return dreamRepository.save(dream);
    }

    // 목록 조회
    public List<Dream> getDreamList() {
        return dreamRepository.findAll();
    }
}
