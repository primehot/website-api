package web.api.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import web.api.application.dto.unit.WeatherDto;

/**
 * Created by oleht on 30.11.2018
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    private WeatherDto dto;

//    @Scheduled(fixedDelay = 1000)
//    public void scheduleFixedDelayTask() {
//        ResponseEntity<WeatherDto> response = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID={APIKEY}", WeatherDto.class);
//        if(response.getStatusCode().equals(HttpStatus.OK)) {
//            this.dto = response.getBody();
//        }
//    }

    @Override
    public WeatherDto getWeather() {
        ResponseEntity<WeatherDto> respose = restTemplate.getForEntity("http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID={APIKEY}", WeatherDto.class);

        return null;
    }
}
