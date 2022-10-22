package com.stalker5217.webflux.post;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import lombok.Data;

@Data
@Table("post")
public class Post {
	@PrimaryKey
	private UUID id = Uuids.timeBased();
	private String content;
}
