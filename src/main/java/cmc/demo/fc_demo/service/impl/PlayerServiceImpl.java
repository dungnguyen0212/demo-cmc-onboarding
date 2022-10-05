package cmc.demo.fc_demo.service.impl;

import cmc.demo.fc_demo.constant.FCConstant;
import cmc.demo.fc_demo.constant.ValidMessageEnum;
import cmc.demo.fc_demo.dto.request.PlayerFilterRequest;
import cmc.demo.fc_demo.dto.request.PlayerRequest;
import cmc.demo.fc_demo.dto.request.PlayerValidRequest;
import cmc.demo.fc_demo.dto.response.CountryResponse;
import cmc.demo.fc_demo.dto.response.PlayerResponse;
import cmc.demo.fc_demo.dto.response.PositionDetailResponse;
import cmc.demo.fc_demo.model.Player;
import cmc.demo.fc_demo.repository.PlayerRepository;
import cmc.demo.fc_demo.service.BudgetService;
import cmc.demo.fc_demo.service.CountryService;
import cmc.demo.fc_demo.service.PlayerService;
import cmc.demo.fc_demo.service.PositionDetailService;
import cmc.demo.fc_demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {
	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PositionDetailService positionDetailService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private BudgetService budgetService;

	@Override
	public List<PlayerResponse> getAllPlayers() {
		return playerRepository.findAll().stream().map(transformToPlayerResponse()).collect(Collectors.toList());
	}

	@Override
	public List<PlayerResponse> getAllActivePlayers() {
		return playerRepository.findAllByIsActive(true).stream().map(transformToPlayerResponse()).collect(Collectors.toList());
	}

	@Override
	public Map<String, String> checkValidPlayer(PlayerValidRequest request) {
		Map<String, String> mapValidPlayer = new HashMap<>();
		Integer salaryFund = budgetService.findById(1L).getValue();
		Integer totalMoney = budgetService.findById(2L).getValue();
		List<String> typeUpload = Arrays.asList(".jpg", ".jpeg", ".png", ".svg");
		String validSalary = null;
		String validTotalMoney = null;
		String validClothesNumber = null;
		String validTypeUpload = null;
		if (Objects.nonNull(request.getAnnuallySalary()) && request.getAnnuallySalary() >= salaryFund) {
			validSalary = ValidMessageEnum.SALARY.getMessage();
		}
		if (Objects.nonNull(request.getMarketValue()) && request.getMarketValue() >= totalMoney) {
			validTotalMoney = ValidMessageEnum.TOTAL_MONEY.getMessage();
		}
		if (Objects.nonNull(request.getAvatar()) && !typeUpload.contains(request.getAvatar())) {
			validTypeUpload = ValidMessageEnum.TYPE_UPLOAD.getMessage();
		}
		if (Objects.nonNull(request.getClothesNumber())
				&& checkDuplicateClothesNumber(request.getClothesNumber(), request.getId())) {
			validClothesNumber = ValidMessageEnum.CLOTHES_NUMBER.getMessage();
		}
		mapValidPlayer.put(ValidMessageEnum.SALARY.getName(), validSalary);
		mapValidPlayer.put(ValidMessageEnum.TOTAL_MONEY.getName(), validTotalMoney);
		mapValidPlayer.put(ValidMessageEnum.CLOTHES_NUMBER.getName(), validClothesNumber);
		mapValidPlayer.put(ValidMessageEnum.TYPE_UPLOAD.getName(), validTypeUpload);
		return mapValidPlayer;
	}

	@Override
	public PlayerResponse findById(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}
		Player player = playerRepository.findById(id).orElse(null);
		if (Objects.nonNull(player)) {
			return transformToPlayerResponse().apply(player);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public String createOrUpdatePlayer(PlayerRequest playerRequest) {
		String result = "";
		// Create or update player
		if (Objects.nonNull(playerRequest.getId())) {
			result = updatePlayer(playerRequest);
		} else {
			result = createPlayer(playerRequest);
		};
		return result;
	}

	@Override
	@Transactional
	public String deletePlayer(Long id) {
		Player player = playerRepository.findById(id).orElse(null);
		if (Objects.nonNull(player)) {
			player.setIsActive(false);
			playerRepository.save(player);
			budgetService.changeMoney(1L, -player.getAnnuallySalary());
			return "Delete player successfully";
		} else {
			return "Something went wrong! Please try again!";
		}
	}

	@Override
	public List<PlayerResponse> findAllPlayerFilter(PlayerFilterRequest request) {
		Integer marketValueGte = FCConstant.MIN_MONEY;
		Integer marketValueLte = FCConstant.MAX_MONEY;
		Integer annuallySalaryGte = FCConstant.MIN_MONEY;
		Integer annuallySalaryLte = FCConstant.MAX_MONEY;
		String country = null;
		if (Objects.nonNull(request.getMarketValueGte())) marketValueGte = request.getMarketValueGte();
		if (Objects.nonNull(request.getMarketValueLte())) marketValueLte = request.getMarketValueLte();
		if (Objects.nonNull(request.getAnnuallySalaryGte())) annuallySalaryGte = request.getAnnuallySalaryGte();
		if (Objects.nonNull(request.getAnnuallySalaryLte())) annuallySalaryLte = request.getAnnuallySalaryLte();
		Timestamp createdAtFrom = StringUtils.isNotBlank(request.getCreatedAtFrom())
				? DateUtils.convertToTimestamp(request.getCreatedAtFrom(), DateUtils.SQL_DATE_PATTERN) : new Timestamp(0);
		Timestamp createdAtTo = StringUtils.isNotBlank(request.getCreatedAtTo())
				? DateUtils.endOfDay(DateUtils.convertToTimestamp(request.getCreatedAtTo(), DateUtils.SQL_DATE_PATTERN)) : new Timestamp(System.currentTimeMillis());

		return playerRepository.findAllByFilter(request.getNameLike(), request.getPositionDetailId(),
				marketValueGte, marketValueLte, annuallySalaryGte, annuallySalaryLte,
				request.getCountryId(), request.getIsInjury(), request.getClothesNumber(),
				createdAtFrom, createdAtTo).stream().map(transformToPlayerResponse())
				.collect(Collectors.toList());
	}

	@Override
	public void savePlayer(PlayerRequest playerRequest) {
		Player player = transformToPlayer().apply(playerRequest);
		playerRepository.save(player);
	}

	private Function<Player, PlayerResponse> transformToPlayerResponse() {
		return player -> {
			PositionDetailResponse positionDetailResponse = null;
			CountryResponse countryResponse = null;
			if (Objects.nonNull(player.getPositionDetailId()))
				positionDetailResponse = positionDetailService.findById(player.getPositionDetailId());
			if (Objects.nonNull(player.getCountryId()))
				countryResponse = countryService.findById(player.getCountryId());
			return PlayerResponse.builder()
					.id(player.getId())
					.firstName(player.getFirstName())
					.lastName(player.getLastName())
					.fullName(getFullName(player.getFirstName(), player.getLastName()))
					.positionDetailId(Objects.nonNull(positionDetailResponse) ? positionDetailResponse.getId() : null)
					.positionDetailCode(Objects.nonNull(positionDetailResponse) ? positionDetailResponse.getCode() : null)
					.positionDetailName(Objects.nonNull(positionDetailResponse) ? positionDetailResponse.getName() : null)
					.positionDetailDescription(Objects.nonNull(positionDetailResponse) ? positionDetailResponse.getDescription() : null)
					.positionId(Objects.nonNull(positionDetailResponse) ? positionDetailResponse.getPositionId() : null)
					.positionName(Objects.nonNull(positionDetailResponse) ? positionDetailResponse.getPositionName() : null)
					.positionDescription(Objects.nonNull(positionDetailResponse) ? positionDetailResponse.getPositionDescription() : null)
					.annuallySalary(player.getAnnuallySalary())
					.marketValue(player.getMarketValue())
					.countryId(Optional.ofNullable(countryResponse).map(CountryResponse::getId).orElse(null))
					.countryCode(Optional.ofNullable(countryResponse).map(CountryResponse::getCode).orElse(null))
					.countryName(Optional.ofNullable(countryResponse).map(CountryResponse::getName).orElse(null))
					.dateOfBirth(Objects.nonNull(player.getDateOfBirth()) ? new java.sql.Date(player.getDateOfBirth().getTime()) : null)
					.skill(player.getSkill())
					.clothesNumber(player.getClothesNumber())
					.createdAt(player.getCreatedAt())
					.isInjury(player.getIsInjury())
					.isActive(player.getIsActive())
					.avatar(player.getAvatar())
					.build();
		};
	}

	private Function<PlayerRequest, Player> transformToPlayer() {
		return player -> {
			return Player.builder()
					.id(player.getId())
					.firstName(player.getFirstName())
					.lastName(player.getLastName())
					.positionDetailId(player.getPositionDetailId())
					.annuallySalary(player.getAnnuallySalary())
					.marketValue(player.getMarketValue())
					.countryId(player.getCountryId())
					.dateOfBirth(Optional.ofNullable(player.getDateOfBirth()).map(d -> DateUtils.convertStringToDate(d, DateUtils.SQL_DATE_PATTERN)).orElse(null))
					.skill(player.getSkill())
					.clothesNumber(player.getClothesNumber())
					.isInjury(player.getIsInjury())
					.isActive(player.getIsActive())
					.avatar(player.getAvatar())
					.build();
		};
	}

	private String getFullName(String firstName, String lastName) {
		if (Objects.isNull(firstName) && !Objects.isNull(lastName))
			return lastName;
		if (!Objects.isNull(firstName) && Objects.isNull(lastName))
			return firstName;
		return firstName.concat(" ").concat(lastName);
	}

	private String createPlayer(PlayerRequest pR) {
//		if (!checkDuplicateClothesNumber(pR.getClothesNumber())) {
			Player p = transformToPlayer().apply(pR);
			p.setIsActive(true);
			p.setIsInjury(false);
			budgetService.changeMoney(1L, pR.getAnnuallySalary());
			budgetService.changeMoney(2L, pR.getMarketValue());
			playerRepository.save(p);
			return "Player create success";
//		} else {
//			return "Clothes number is existed! Please try again!";
//		}
	}

	private String updatePlayer(PlayerRequest pR) {
		Player p = playerRepository.findById(pR.getId()).orElse(null);
		if (Objects.nonNull(p)) {
			p.setFirstName(pR.getFirstName());
			p.setLastName(pR.getLastName());
			p.setPositionDetailId(pR.getPositionDetailId());
			p.setAnnuallySalary(pR.getAnnuallySalary());
			p.setMarketValue(pR.getMarketValue());
			p.setCountryId(pR.getCountryId());
			p.setDateOfBirth(Optional.ofNullable(pR.getDateOfBirth()).map(d -> DateUtils.convertStringToDate(d, DateUtils.SQL_DATE_PATTERN)).orElse(null));
			p.setSkill(pR.getSkill());
			p.setClothesNumber(pR.getClothesNumber());
			if (Objects.nonNull(pR.getIsInjury())) p.setIsInjury(pR.getIsInjury());
			p.setIsActive(true);
			p.setAvatar(pR.getAvatar());
			playerRepository.save(p);
		}
		return "Player update success";
	}

	private Boolean checkDuplicateClothesNumber(Integer clothesNumber, Long playerId) {
		Map<Integer, Long> mapPlayerIdWithClothesNumber = new HashMap<>();
		playerRepository.findAllByClothesNumberAndIsActive(clothesNumber, true)
						.forEach(p -> mapPlayerIdWithClothesNumber.put(p.getClothesNumber(), p.getId()));
		if (playerId == null && mapPlayerIdWithClothesNumber.containsKey(clothesNumber)) {
			return true;
		} else return mapPlayerIdWithClothesNumber.containsKey(clothesNumber)
				&& !Objects.equals(playerId, mapPlayerIdWithClothesNumber.get(clothesNumber));
	}

	private Boolean checkAnnuallySalary(Integer annuallySalary) {
		return true;
	}

	private Boolean checkMarketValue(Integer marketValue) {
		return true;
	}
}
