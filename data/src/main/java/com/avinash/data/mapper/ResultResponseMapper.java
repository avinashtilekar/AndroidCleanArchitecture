package com.avinash.data.mapper;

import com.avinash.data.source.remote.model.ResultResponse;
import com.avinash.domain.model.ResultDTO;
import com.avinash.domain.model.other.CloudsDTO;
import com.avinash.domain.model.other.CoordDTO;
import com.avinash.domain.model.other.MainDTO;
import com.avinash.domain.model.other.SysDTO;
import com.avinash.domain.model.other.WeatherDTO;
import com.avinash.domain.model.other.WindDTO;

import java.util.ArrayList;
import java.util.List;


public class ResultResponseMapper implements IMapper<ResultResponse, ResultDTO> {


    @Override
    public ResultDTO mapFromEntity(ResultResponse resultResponse) {
        ResultDTO resultDTO = new ResultDTO();
        if (resultResponse != null) {
            resultDTO.setBase(resultResponse.getBase());
            resultDTO.setCod(resultResponse.getCod());
            resultDTO.setDt(resultResponse.getDt());
            resultDTO.setId(resultResponse.getId());
            resultDTO.setName(resultResponse.getName());
            resultDTO.setTimezone(resultResponse.getTimezone());
            resultDTO.setVisibility(resultResponse.getVisibility());

            if (resultResponse.getCoord() != null) {
                CoordDTO coordDTO = new CoordDTO();
                coordDTO.setLat(resultResponse.getCoord().getLat());
                coordDTO.setLon(resultResponse.getCoord().getLon());
                resultDTO.setCoordDTO(coordDTO);
            }

            if (resultResponse.getWeather() != null && resultResponse.getWeather().size() > 0) {
                List<WeatherDTO> weatherDTOList = new ArrayList<>();
                for (ResultResponse.Weather weather : resultResponse.getWeather()) {
                    WeatherDTO weatherDTO = new WeatherDTO();
                    weatherDTO.setDescription(weather.getDescription());
                    weatherDTO.setIcon(weather.getIcon());
                    weatherDTO.setId(weather.getId());
                    weatherDTO.setMain(weather.getMain());
                    weatherDTOList.add(weatherDTO);
                }
                resultDTO.setWeatherDTO(weatherDTOList);

            }

            if (resultResponse.getMain() != null) {
                MainDTO mainDTO = new MainDTO();
                mainDTO.setFeelsLike(resultResponse.getMain().getFeelsLike());
                mainDTO.setGrndLevel(resultResponse.getMain().getGrndLevel());
                mainDTO.setHumidity(resultResponse.getMain().getHumidity());
                mainDTO.setPressure(resultResponse.getMain().getPressure());
                mainDTO.setSeaLevel(resultResponse.getMain().getSeaLevel());
                mainDTO.setTemp(resultResponse.getMain().getTemp());
                mainDTO.setTempMin(resultResponse.getMain().getTempMin());
                mainDTO.setTempMax(resultResponse.getMain().getTempMax());
                resultDTO.setMainDTO(mainDTO);
            }

            if (resultResponse.getWind() != null) {
                WindDTO windDTO = new WindDTO();
                windDTO.setDeg(resultResponse.getWind().getDeg());
                windDTO.setGust(resultResponse.getWind().getGust());
                windDTO.setSpeed(resultResponse.getWind().getSpeed());
                resultDTO.setWindDTO(windDTO);
            }
            if (resultResponse.getClouds() != null) {
                CloudsDTO cloudsDTO = new CloudsDTO();
                cloudsDTO.setAll(resultResponse.getClouds().getAll());
                resultDTO.setCloudsDTO(cloudsDTO);

            }
            if (resultResponse.getSys() != null) {
                SysDTO sysDTO = new SysDTO();
                sysDTO.setCountry(resultResponse.getSys().getCountry());
                sysDTO.setSunrise(resultResponse.getSys().getSunrise());
                sysDTO.setSunset(resultResponse.getSys().getSunset());
                resultDTO.setSysDTO(sysDTO);
            }


        }

        return resultDTO;
    }

    @Override
    public ResultResponse mapToEntity(ResultDTO resultDTO) {
        return null;
    }
}
