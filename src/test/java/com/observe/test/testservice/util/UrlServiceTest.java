package com.observe.test.testservice.util;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.observe.test.testservice.dto.UrlLongRequest;
import com.observe.test.testservice.entity.Url;
import com.observe.test.testservice.repository.UrlRepository;
import com.observe.test.testservice.service.UrlService;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @Mock
    UrlRepository mockUrlRepository;

    @Mock
    BaseConversion mockBaseConversion;

    @InjectMocks
    UrlService urlService;

    @Test
    public void convertToShortUrlTest() {
        Url url = new Url();
        url.setLongUrl("https://github.com/AnteMarin/UrlShortener-API");
        url.setCreatedDate(new Date());
        url.setId(5);

        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);
        when(mockBaseConversion.encode(url.getId())).thenReturn("f");

        UrlLongRequest urlRequest = new UrlLongRequest();
        urlRequest.setLongUrl("https://github.com/AnteMarin/UrlShortener-API");

        assertEquals("f", urlService.convertToShortUrl(urlRequest));
    }

    @Test
    public void getOriginalUrlTest() {
        when(mockBaseConversion.decode("h")).thenReturn((long) 7);

        var url = new Url();
        url.setLongUrl("https://github.com/AnteMarin/UrlShortener-API");
        url.setCreatedDate(new Date());
        url.setId(7);

        when(mockUrlRepository.findById((long) 7)).thenReturn(java.util.Optional.of(url));
        assertEquals("https://github.com/AnteMarin/UrlShortener-API", urlService.getOriginalUrl("h"));

    }
}
