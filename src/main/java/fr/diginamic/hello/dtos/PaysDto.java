package fr.diginamic.hello.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class PaysDto {

    @JsonProperty("name")
    private NameWrapper name;

    private String region;
    private String subregion;
    public List<String> capital;
    public Map<String, String> languages;
    private int population;

    public String getNomCommun() {
        return name != null ? name.getCommon() : null;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public List<String> getCapital() {
        return capital;
    }

    public void setCapital(List<String> capital) {
        this.capital = capital;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, String> languages) {
        this.languages = languages;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public static class NameWrapper {
        private String common;

        public String getCommon() { return common; }
        public void setCommon(String common) { this.common = common;
        }
    }
}
