package cmc.demo.fc_demo.service.impl;

import cmc.demo.fc_demo.dto.response.CountryResponse;
import cmc.demo.fc_demo.dto.response.PositionDetailResponse;
import cmc.demo.fc_demo.model.Country;
import cmc.demo.fc_demo.model.PositionDetail;
import cmc.demo.fc_demo.repository.CountryRepository;
import cmc.demo.fc_demo.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public List<CountryResponse> getAllCountries() {
		return countryRepository.findAll().stream().map(
				c -> modelMapper.map(c, CountryResponse.class)).collect(Collectors.toList());
	}
	@Override
	public CountryResponse findById(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}

		Country country = countryRepository.findById(id)
				.orElse(null);
		if (Objects.nonNull(country)) {
			return modelMapper.map(country, CountryResponse.class);
		} else {
			return null;
		}
	}
}
