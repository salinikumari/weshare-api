package com.weshare.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.weshare.repository.MessageRepository;
import com.weshare.entity.Message;

@Service
public class MessageService 
{
	@Autowired
	private MessageRepository messageRepository;

	public boolean addNewMessage(Message message)
	{
		messageRepository.save(message);
		return true;
	}
	
	public Page<Message> getAllMessagesByUser(long userId, Pageable pageable)
	{
		return messageRepository.findByUserIdOrderByCreatedDateDesc(userId, pageable);
	}

	public Page<Message> getAllMessagesByOthers(long userId, Pageable pageable)
	{
		return messageRepository.findByUserIdNotOrderByCreatedDateDesc(userId, pageable);
	}
}
