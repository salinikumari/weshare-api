package com.weshare.repository;
import com.weshare.entity.Message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer>
{
	Page<Message> findByUserIdOrderByCreatedDateDesc(int userId, Pageable pageable);
	Page<Message> findByUserIdNotOrderByCreatedDateDesc(int userId, Pageable pageable);
}
