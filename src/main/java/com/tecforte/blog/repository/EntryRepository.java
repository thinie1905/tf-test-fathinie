package com.tecforte.blog.repository;
import com.tecforte.blog.domain.Blog;
import com.tecforte.blog.domain.Entry;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data  repository for the Entry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM Entry WHERE title LIKE :keyword OR content LIKE :keyword")
	void deleteEntryWithKeyword(@Param("keyword") String keyword);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Entry WHERE blog.id = :blogid AND (title LIKE :keyword OR content LIKE :keyword)")
	void deleteEntryFromBlogID(@Param("blogid") Long id, @Param("keyword") String keyword);

}
