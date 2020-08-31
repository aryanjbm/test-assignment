package com.observe.test.testservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.observe.test.testservice.entity.Url;

public interface UrlRepository extends JpaRepository<Url,Long> {
	Url findByLongUrlAndClientId(String LongUrl,String ClientId);
}
