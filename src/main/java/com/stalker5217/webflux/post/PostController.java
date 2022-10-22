package com.stalker5217.webflux.post;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {
	private final PostRepository postRepository;

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<PostDto.Response> create(@RequestBody @Valid Mono<PostDto.Request> postRequest) {
		Mono<Post> postMono = postRequest.map(request -> {
			Post post = new Post();
			post.setContent(request.content());

			return post;
		});

		Mono<Post> result = postRepository.saveAll(postMono).next();

		return result.map(r -> new PostDto.Response(
			r.getId().toString(),
			r.getContent()
		));
	}

	@GetMapping
	public Flux<Post> read() {
		return postRepository.findAll();
	}

	@GetMapping("/{uuid}")
	public Mono<Post> readById(@PathVariable String uuid) {
		return postRepository.findById(UUID.fromString(uuid));
	}
}
