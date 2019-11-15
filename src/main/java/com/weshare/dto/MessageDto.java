package com.weshare.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MessageDto 
{
	private String userName;
	
	@NotNull(message = "Invalid User.")
	@Min(value = 1, message = "Invalid User.")
	@NumberFormat(style = Style.NUMBER)
	private Long userId;

	@Size(min=0, max= 150, message = "Message maximum character limit is 150.")
	@NotBlank(message = "Please provide a valid message.")
	@Pattern(regexp="^[a-zA-Z0-9.\\-\\/+=@_ !#$%&*(){}|,;:?<>]*$", message ="Has invalid characters.")
	private String message;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime createdDate;

	public MessageDto() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
