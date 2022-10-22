package com.stalker5217.webflux;

import java.util.UUID;

import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.stalker5217.webflux.post.Post;
import com.stalker5217.webflux.post.PostDto;
import com.stalker5217.webflux.post.PostRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@SpringBootTest
@EmbeddedCassandra
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class PostTest {
	@Autowired
	WebTestClient webTestClient;

	@Autowired
	PostRepository postRepository;

	@Test
	@DisplayName("게시글 생성")
	@Order(1)
	void create() {
		Mono<PostDto.Request> postRequest = Mono.just(new PostDto.Request("sample"));

		webTestClient.post()
			.uri("/api/v1/posts")
			.contentType(MediaType.APPLICATION_JSON)
			.body(postRequest, PostDto.Request.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.content").isEqualTo("sample");
	}

	@Test
	@DisplayName("게시글 읽기")
	@Order(2)
	void read() {
		Mono<String> id = postRepository
			.findAll()
			.next()
			.map(post -> post.getId().toString());

		webTestClient.get()
			.uri("/api/v1/posts/{id}", id.block())
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.id").isNotEmpty()
			.jsonPath("$.content").isNotEmpty();
	}

	@Test
	@DisplayName("게시글 전체 읽기")
	@Order(3)
	void readAll() {
		webTestClient.get()
			.uri("/api/v1/posts")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$").isNotEmpty()
			.jsonPath("$[0].id").isNotEmpty()
			.jsonPath("$[0].content").isNotEmpty();
	}
}