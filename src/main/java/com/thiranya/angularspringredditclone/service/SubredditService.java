package com.thiranya.angularspringredditclone.service;

import com.thiranya.angularspringredditclone.dto.SubredditDto;
import com.thiranya.angularspringredditclone.exception.SubredditNotFoundException;
import com.thiranya.angularspringredditclone.mapper.SubredditMapper;
import com.thiranya.angularspringredditclone.model.Subreddit;
import com.thiranya.angularspringredditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SubredditService {
    private SubredditRepository subredditRepository;
    private SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}