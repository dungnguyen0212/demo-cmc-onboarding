package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.response.CountryResponse;

import java.util.List;

public interface CountryService {
	List<CountryResponse> getAllCountries();

	CountryResponse findById(Long id);
}
