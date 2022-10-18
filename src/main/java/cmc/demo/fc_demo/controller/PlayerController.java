package cmc.demo.fc_demo.controller;

import cmc.demo.fc_demo.constant.ToastMessageEnum;
import cmc.demo.fc_demo.constant.ValidMessageEnum;
import cmc.demo.fc_demo.dto.PlayerExcelDTO;
import cmc.demo.fc_demo.dto.request.PlayerFilterRequest;
import cmc.demo.fc_demo.dto.request.PlayerRequest;
import cmc.demo.fc_demo.dto.request.PlayerValidRequest;
import cmc.demo.fc_demo.dto.response.*;
import cmc.demo.fc_demo.service.*;
import cmc.demo.fc_demo.service.excel.ExportService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.poi.hpsf.wellknown.PropertyIDMap.UNDEFINED;

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

	@Autowired
	private ConditionTypeService conditionTypeService;

	@Autowired
	private TransferHistoryService transferHistoryService;

	@Autowired
	private ExportService exportService;

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

	@ModelAttribute("conditions")
	public List<ConditionTypeResponse> getConditions() {
		return conditionTypeService.getAllConditions();
	}

	@ModelAttribute("transferHistories")
	public List<TransferHistoryResponse> getTransferHistories() {
		return transferHistoryService.getAllTransferHistory();
	}

	@ModelAttribute("cssArrow")
	public Integer getCssArrow() {
		return 0;
	}

	@GetMapping("/manage-player")
	public ModelAndView showManagePlayer(@RequestParam(defaultValue = "0") int page,
										 @RequestParam(defaultValue = "5") int size,
										 @RequestParam(defaultValue = "updatedAt") String sortBy,
										 @RequestParam(defaultValue = "DESC") String direction) {
		ModelAndView modelAndView = new ModelAndView("/home");
		PlayerFilterRequest searchPlayer = PlayerFilterRequest.builder().build();
		modelAndView.addObject("players", playerService.getAllActivePlayers(page, size, sortBy, direction));
		modelAndView.addObject("searchPlayer", searchPlayer);
		if (sortBy.equals("id") && direction.equals("ASC")) {
			modelAndView.addObject("cssArrow", 1);
		} else if (sortBy.equals("id") && direction.equals("DESC")) {
			modelAndView.addObject("cssArrow", 2);
		} else if (sortBy.equals("clothesNumber") && direction.equals("ASC")) {
			modelAndView.addObject("cssArrow", 3);
		} else if (sortBy.equals("clothesNumber") && direction.equals("DESC")) {
			modelAndView.addObject("cssArrow", 4);
		} else if (sortBy.equals("createdAt") && direction.equals("ASC")) {
			modelAndView.addObject("cssArrow", 5);
		} else if (sortBy.equals("createdAt") && direction.equals("DESC")) {
			modelAndView.addObject("cssArrow", 6);
		} else {
			// To-Do
		}
		String pagingAndSortingParams = "&&sortBy=" + sortBy + "&&direction=" + direction;
		modelAndView.addObject("pagingAndSortingParam", pagingAndSortingParams);
		modelAndView.addObject("transferHistories", getTransferHistories());
		return modelAndView;
	}

	@GetMapping("/buy-player")
	public ModelAndView getTransferPlayerInMarket() {
		PlayerRequest playerRequest = PlayerRequest.builder().build();
		return new ModelAndView("/buyplayer", "player", playerRequest);
	}

	@SneakyThrows
	@PostMapping("/buy-or-update-player")
	public ModelAndView buyPlayer(@Valid @ModelAttribute("player") PlayerRequest player, BindingResult bindingResult,
								  @RequestParam MultipartFile uploadImg, final RedirectAttributes redirectAttributes) {
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
			} else modelAndView = new ModelAndView("editplayer", "player", player);
			modelAndView.addObject("validAnnuallySalary", checkValid.get(ValidMessageEnum.SALARY.getName()));
			modelAndView.addObject("validMarketValue", checkValid.get(ValidMessageEnum.TOTAL_MONEY.getName()));
			modelAndView.addObject("validClothesNumber", checkValid.get(ValidMessageEnum.CLOTHES_NUMBER.getName()));
			modelAndView.addObject("validAvatar", checkValid.get(ValidMessageEnum.TYPE_UPLOAD.getName()));
			return modelAndView;
		}
		String result = playerService.createOrUpdatePlayer(player);
		if (result.equals("Success")) {
			String toastMessageValue = Objects.nonNull(player.getId()) ? ToastMessageEnum.UPDATE.getDescription() : ToastMessageEnum.CREATE.getDescription();
			redirectAttributes.addFlashAttribute("toastMessage", toastMessageValue);
		}
		return new ModelAndView("redirect:/player/manage-player");
//		Page<PlayerResponse> playerResponses = playerService.getAllActivePlayers(0, 5, "updatedAt", "DESC");
//		modelAndView = new ModelAndView("home", "players", playerResponses);
//		modelAndView.addObject("searchPlayer", PlayerFilterRequest.builder().build());
//		String toastMessageValue = Objects.nonNull(player.getId()) ? ToastMessageEnum.UPDATE.getDescription() : ToastMessageEnum.CREATE.getDescription();
//		modelAndView.addObject("toastMessage", toastMessageValue);
//		Page<PlayerResponse> playerResponses2 = playerService.getAllActivePlayers(0, 5, "updatedAt", "DESC");
//		return modelAndView;
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
		return new ModelAndView("editplayer", "player", playerService.findById(id));
	}

	@GetMapping("/detail-player")
	public ModelAndView showDetailPlayer(@RequestParam("id") Long id) {
		return new ModelAndView("detailplayer", "player", playerService.findById(id));
	}

	@GetMapping("/delete-player")
	public ModelAndView deleteCity(@RequestParam("id") Long id, final RedirectAttributes redirectAttributes) {
		String result = playerService.deletePlayer(id);
		if (result.equalsIgnoreCase("Success")) {
			redirectAttributes.addFlashAttribute("toastMessage", ToastMessageEnum.DELETE.getDescription());
		}
//		ModelAndView modelAndView = new ModelAndView("home", "players", playerService.getAllActivePlayers(0, 5, "updatedAt", "DESC"));
//		modelAndView.addObject("searchPlayer", PlayerFilterRequest.builder().build());
		return new ModelAndView("redirect:/player/manage-player");
	}

	@PostMapping("/to-excel")
	public void exportPlayerToExcel(
//			@Valid @ModelAttribute PlayerFilterRequest request,
			HttpServletResponse response
	) throws Exception {
		ExportService.exportExcel(response, getExcelDataPlayer(playerService.getAllActivePlayers()));
	}

	private List<PlayerExcelDTO> getExcelDataPlayer(List<PlayerResponse> players) throws Exception {
		List<PlayerExcelDTO> playerInfoResponses = players.stream()
				.map(p -> PlayerExcelDTO.builder()
						.fullName(p.getFullName())
						.positionDetailCode(Objects.nonNull(p.getPositionDetailCode()) ? p.getPositionDetailCode() : UNDEFINED)
						.annuallySalary(p.getAnnuallySalary())
						.marketValue(p.getMarketValue())
						.countryName(Objects.nonNull(p.getCountryName()) ? p.getCountryName() : UNDEFINED)
						.skill(Objects.nonNull(p.getSkill()) ? p.getSkill() : UNDEFINED)
						.clothesNumber(p.getClothesNumber())
						.build())
				.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(playerInfoResponses)) {
			playerInfoResponses = Collections.singletonList(PlayerExcelDTO.builder().build());
		}
		return playerInfoResponses;
	}
}
