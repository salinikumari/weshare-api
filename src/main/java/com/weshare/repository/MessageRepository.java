package com.weshare.repository;
import com.weshare.entity.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>
{
	Page<Message> findByUserIdOrderByCreatedDateDesc(long userId, Pageable pageable);
	Page<Message> findByUserIdNotOrderByCreatedDateDesc(long userId, Pageable pageable);
}
