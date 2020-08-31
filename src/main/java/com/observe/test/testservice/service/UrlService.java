package com.observe.test.testservice.service;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.observe.test.testservice.dto.UrlLongRequest;
import com.observe.test.testservice.entity.Url;
import com.observe.test.testservice.repository.UrlRepository;
import com.observe.test.testservice.util.BaseConversion;

@Service
public class UrlService {

	@Autowired
    private UrlRepository urlRepository;
	
	@Autowired
    private BaseConversion conversion;



    public String convertToShortUrl(UrlLongRequest request) {
    	Url url = urlRepository.findByLongUrlAndClientId(request.getLongUrl(), request.getClientId());
    	if(url!=null )
    		return conversion.encode(url.getId());
    		
        url = new Url();
        url.setLongUrl(request.getLongUrl());
        url.setExpiresDate(request.getExpiresDate());
        url.setCreatedDate(new Date());
        url.setClientId(request.getClientId());
        
        
        var entity = urlRepository.save(url);

        return conversion.encode(entity.getId());
    }

    public String getOriginalUrl(String shortUrl) {
        var id = conversion.decode(shortUrl);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        if (entity.getExpiresDate() != null && entity.getExpiresDate().before(new Date())){
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link expired!");
        }

        return entity.getLongUrl();
    }
}
