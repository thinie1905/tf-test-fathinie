package com.tecforte.blog.service;

import com.tecforte.blog.domain.Blog;
import com.tecforte.blog.repository.BlogRepository;
import com.tecforte.blog.repository.EntryRepository;
import com.tecforte.blog.service.dto.BlogDTO;
import com.tecforte.blog.service.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Blog}.
 */
@Service
@Transactional
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);

    private final BlogRepository blogRepository;

    private final BlogMapper blogMapper;
    
    private final EntryRepository entryRepository;

    public BlogService(BlogRepository blogRepository, BlogMapper blogMapper, EntryRepository entryRepository) {
        this.blogRepository = blogRepository;
        this.blogMapper = blogMapper;
        this.entryRepository = entryRepository;
    }

    /**
     * Save a blog.
     *
     * @param blogDTO the entity to save.
     * @return the persisted entity.
     */
    public BlogDTO save(BlogDTO blogDTO) {
        log.debug("Request to save Blog : {}", blogDTO);
        Blog blog = blogMapper.toEntity(blogDTO);
        blog = blogRepository.save(blog);
        return blogMapper.toDto(blog);
    }

    /**
     * Get all the blogs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BlogDTO> findAll() {
        log.debug("Request to get all Blogs");
        return blogRepository.findAllWithEagerRelationships().stream()
            .map(blogMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one blog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BlogDTO> findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        return blogRepository.findById(id)
            .map(blogMapper::toDto);
    }

    /**
     * Delete the blog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
    }
    
    /**
     * Delete entry by its keyword from title or content
     * 
     * @param keyword
     * 
     */
    public void deleteBlogEntries(String keyword) {
    	log.debug("Request to delete Blog entries by keyword : {}", keyword);
    	entryRepository.deleteEntryWithKeyword(keyword);
    }
    
    /**
     * Delete entry by its keyword from title or content from specific blog
     * 
     * @param keyword
     * @param blog id
     * 
     */
    public void deleteEntryFromBlogID(Long id, String keyword) {
    	log.debug("Request to delete Blog entries by keyword from blog ID : {}", id, keyword);
    	entryRepository.deleteEntryFromBlogID(id, keyword);
    }
    
}
