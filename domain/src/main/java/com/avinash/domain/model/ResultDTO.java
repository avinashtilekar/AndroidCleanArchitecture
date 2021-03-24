package com.avinash.domain.model;

import com.avinash.domain.model.other.CloudsDTO;
import com.avinash.domain.model.other.CoordDTO;
import com.avinash.domain.model.other.MainDTO;
import com.avinash.domain.model.other.SysDTO;
import com.avinash.domain.model.other.WeatherDTO;
import com.avinash.domain.model.other.WindDTO;

import java.util.List;


public class ResultDTO {

    private CoordDTO coordDTO;
    private List<WeatherDTO> weatherDTO = null;
    private String base;
    private MainDTO mainDTO;
    private Integer visibility;
    private WindDTO windDTO;
    private CloudsDTO cloudsDTO;
    private Integer dt;
    private SysDTO sysDTO;
    private Integer timezone;
    private Integer id;
    private String name;
    private Integer cod;

    public CoordDTO getCoordDTO() {
        return coordDTO;
    }

    public void setCoordDTO(CoordDTO coordDTO) {
        this.coordDTO = coordDTO;
    }

    public List<WeatherDTO> getWeatherDTO() {
        return weatherDTO;
    }

    public void setWeatherDTO(List<WeatherDTO> weatherDTO) {
        this.weatherDTO = weatherDTO;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainDTO getMainDTO() {
        return mainDTO;
    }

    public void setMainDTO(MainDTO mainDTO) {
        this.mainDTO = mainDTO;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public WindDTO getWindDTO() {
        return windDTO;
    }

    public void setWindDTO(WindDTO windDTO) {
        this.windDTO = windDTO;
    }

    public CloudsDTO getCloudsDTO() {
        return cloudsDTO;
    }

    public void setCloudsDTO(CloudsDTO cloudsDTO) {
        this.cloudsDTO = cloudsDTO;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public SysDTO getSysDTO() {
        return sysDTO;
    }

    public void setSysDTO(SysDTO sysDTO) {
        this.sysDTO = sysDTO;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }








}