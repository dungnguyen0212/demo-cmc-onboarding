package cmc.demo.fc_demo.controller;

import cmc.demo.fc_demo.constant.ToastMessageEnum;
import cmc.demo.fc_demo.constant.ValidMessageEnum;
import cmc.demo.fc_demo.dto.request.PlayerFilterRequest;
import cmc.demo.fc_demo.dto.request.PlayerRequest;
import cmc.demo.fc_demo.dto.request.PlayerValidRequest;
import cmc.demo.fc_demo.dto.response.*;
import cmc.demo.fc_demo.service.*;
import lombok.SneakyThrows;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/player")
public class PlayerController {
	@Autowired
	private PlayerService playerService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private PositionDetailService positionDetailService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private BudgetService budgetService;

	@Value("${uploadPath}")
	private String fileUpload;

	@ModelAttribute("countries")
	public List<CountryResponse> getCountries() {
		return countryService.getAllCountries();
	}

	@ModelAttribute("positions")
	public List<PositionResponse> getPositions() {
		return positionService.getAllActivePosition();
	}

	@ModelAttribute("positionDetails")
	public List<PositionDetailResponse> getPositionDetails() {
		return positionDetailService.getAllActivePositionDetail();
	}

	@ModelAttribute("budgetSalary")
	public BudgetResponse getBudgetSalary() {
		return budgetService.findById(1L);
	}

	@ModelAttribute("budgetTotalMoney")
	public BudgetResponse getBudgetTotalMoney() {
		return budgetService.findById(2L);
	}

	@GetMapping("/manage-player")
	public ModelAndView showManagePlayer() {
		ModelAndView modelAndView = new ModelAndView("/home");
		PlayerFilterRequest searchPlayer = PlayerFilterRequest.builder().build();
		modelAndView.addObject("players", playerService.getAllActivePlayers());
		modelAndView.addObject("searchPlayer", searchPlayer);
		return modelAndView;
	}
	@GetMapping("/buy-player")
	public ModelAndView getTransferPlayerInMarket() {
		PlayerRequest playerRequest = PlayerRequest.builder().build();
		return new ModelAndView("/buyplayer", "player", playerRequest);
	}

	@SneakyThrows
	@PostMapping("/buy-or-update-player")
	public ModelAndView buyPlayer(@Valid @ModelAttribute("player") PlayerRequest player, BindingResult bindingResult, @RequestParam MultipartFile uploadImg) {
		ModelAndView modelAndView;
		String originalFilename = uploadImg.getOriginalFilename();
		PlayerValidRequest validRequest = PlayerValidRequest.builder()
				.annuallySalary(player.getAnnuallySalary())
				.marketValue(player.getMarketValue())
				.clothesNumber(player.getClothesNumber())
				.id(player.getId())
				.build();
		if (StringUtils.isNotBlank(originalFilename)) {
			String typeFile = originalFilename.substring(originalFilename.lastIndexOf("."));
			String imgName = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + System.currentTimeMillis() + typeFile;
			FileCopyUtils.copy(uploadImg.getBytes(), new File(fileUpload + "img\\" + imgName));
			player.setAvatar("/img/" + imgName);
			validRequest.setAvatar(typeFile);
		}
		Map<String, String> checkValid = playerService.checkValidPlayer(validRequest);
		Set<String> setValid = checkValid.values().stream().filter(Objects::nonNull).collect(Collectors.toSet());
		if (bindingResult.hasFieldErrors() || !CollectionUtils.isEmpty(setValid)) {
			if (Objects.isNull(player.getId())) {
				modelAndView = new ModelAndView("buyplayer", "player", player);
			}
			else modelAndView = new ModelAndView("editplayer","player", player);
			modelAndView.addObject("validAnnuallySalary", checkValid.get(ValidMessageEnum.SALARY.getName()));
			modelAndView.addObject("validMarketValue", checkValid.get(ValidMessageEnum.TOTAL_MONEY.getName()));
			modelAndView.addObject("validClothesNumber", checkValid.get(ValidMessageEnum.CLOTHES_NUMBER.getName()));
			modelAndView.addObject("validAvatar", checkValid.get(ValidMessageEnum.TYPE_UPLOAD.getName()));
			return modelAndView;
		}
		playerService.createOrUpdatePlayer(player);
		modelAndView = new ModelAndView("home", "players", playerService.getAllActivePlayers());
		modelAndView.addObject("searchPlayer", PlayerFilterRequest.builder().build());
		String toastMessageValue = Objects.nonNull(player.getId()) ? ToastMessageEnum.UPDATE.getDescription() : ToastMessageEnum.CREATE.getDescription();
		modelAndView.addObject("toastMessage", toastMessageValue);
		return modelAndView;
	}

	@PostMapping("/search-player")
	public ModelAndView getAllFilterPlayer(@ModelAttribute PlayerFilterRequest request) {
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("players", playerService.findAllPlayerFilter(request));
		modelAndView.addObject("searchPlayer", request);
		return modelAndView;
	}

	@GetMapping("/edit-player")
	public ModelAndView showEditForm(@RequestParam("id") Long id) {
		return new ModelAndView("editplayer","player", playerService.findById(id));
	}

	@GetMapping("/detail-player")
	public ModelAndView showDetailPlayer(@RequestParam("id") Long id) {
		return new ModelAndView("detailplayer","player", playerService.findById(id));
	}

	@GetMapping("/delete-player")
	public ModelAndView deleteCity(@RequestParam("id") Long id) {
		playerService.deletePlayer(id);
		ModelAndView modelAndView = new ModelAndView("home", "players", playerService.getAllActivePlayers());
		modelAndView.addObject("searchPlayer", PlayerFilterRequest.builder().build());
		// toast TO-DO
		modelAndView.addObject("toastMessage", ToastMessageEnum.DELETE.getDescription());
		return modelAndView;
	}
}
