package com.stalker5217.webflux.post;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostDto {
	public record Request(@NotBlank String content) {
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record Response(String id, String content) {
	}
}