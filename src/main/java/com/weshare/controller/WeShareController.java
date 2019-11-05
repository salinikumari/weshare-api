package com.weshare.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.weshare.service.UserService;
import com.weshare.service.MessageService;
import com.weshare.entity.User;
import com.weshare.entity.Message;
import com.weshare.dto.ErrorDto;
import com.weshare.dto.MessageDto;
import com.weshare.dto.UserDto;
import com.weshare.exception.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Validated 
@RestController
public class WeShareController 
{
	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/login")
    public ResponseEntity<Object> validateLogin(@Size(min = 5, max = 20, message = "Username should be of 5 to 20 chars ")@Pattern(regexp="^[a-zA-Z0-9.\\-\\/+=@_]*$", message ="Username has invalid characters.")@RequestParam String userName, @Size(min = 1, max = 20, message = "Password should be of 5 to 20 chars")@Pattern(regexp="^[a-zA-Z0-9.\\-\\/+=@_]*$", message ="Password has invalid characters.")@RequestParam String password)
    {
		User user;
		UserDto userDto;
		ErrorDto errDto;
		
		user = userService.getUser(userName, password);
		if (user == null)
		{
			errDto = new ErrorDto(HttpStatus.NOT_FOUND.value(), "Invalid User.");
			return new ResponseEntity<>(errDto, HttpStatus.NOT_FOUND);
		}
		userDto = convertToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

	@PostMapping("/postmsg/{id}")
	public ResponseEntity<Object> addNewMessage(@Valid @RequestBody MessageDto msgDto, @PathVariable("id") int userId)
	{
		Message message;
		User user;
		
		user = userService.getUserById(userId);
		message = convertToMessageEntity(msgDto, user);
		messageService.addNewMessage(message);
		return new ResponseEntity<>("The message is posted successfully.", HttpStatus.CREATED);
	}

	@GetMapping("/viewmsg/{id}")
	public Page<MessageDto> getAllMessagesByUser(@PathVariable("id") int userId,@PageableDefault(size = 10) Pageable pageable)
	{
		List<MessageDto> msgDtos;
		Page<Message> msgs;
		PageImpl<MessageDto> pageMsgDtos;

		if (!userService.isValiduser(userId))
			throw new UserNotFoundException();
		msgs = messageService.getAllMessagesByUser(userId, pageable);
		msgDtos = msgs.getContent().stream().map(message -> convertToMessageDto(message)).collect(Collectors.toList());
		pageMsgDtos = new PageImpl<MessageDto>(msgDtos, pageable, msgs.getTotalElements());
		return pageMsgDtos;
	}

	@GetMapping("/viewothermsg/{id}")
	public Page<MessageDto> getAllMessagesByOthers(@PathVariable("id") int userId, @PageableDefault(size = 10)Pageable pageable)
	{
		List<MessageDto> msgDtos;
		Page<Message> msgs;
		PageImpl<MessageDto> pageMsgDtos;
		
		if (!userService.isValiduser(userId))
			throw new UserNotFoundException();
		msgs = messageService.getAllMessagesByOthers(userId, pageable);
		msgDtos = msgs.getContent().stream().map(message -> convertToMessageDto(message)).collect(Collectors.toList());
		pageMsgDtos = new PageImpl<MessageDto>(msgDtos, pageable, msgs.getTotalElements());
		return pageMsgDtos;
	}
	
	private Message convertToMessageEntity(MessageDto msgDto, User user)
	{
		Message message = null;
		
		message = modelMapper.map(msgDto, Message.class);
		message.setUser(user);
		message.setCreatedDate(LocalDateTime.now());

		return message;
	}

	private MessageDto convertToMessageDto(Message message)
	{
		MessageDto msgDto;
		
		msgDto = modelMapper.map(message, MessageDto.class);
		
		return msgDto;
	}

	private UserDto convertToUserDto(User user)
	{
		UserDto userDto;
		
		userDto = modelMapper.map(user, UserDto.class);
		
		return userDto;
	}
}
