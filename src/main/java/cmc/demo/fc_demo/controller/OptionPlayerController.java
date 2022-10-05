package cmc.demo.fc_demo.controller;

import cmc.demo.fc_demo.dto.response.PlayerResponse;
import cmc.demo.fc_demo.dto.response.PositionDetailResponse;
import cmc.demo.fc_demo.dto.response.PositionResponse;
import cmc.demo.fc_demo.service.PositionDetailService;
import cmc.demo.fc_demo.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/options")
public class OptionPlayerController {
	@Autowired
	private PositionService positionService;

	@Autowired
	private PositionDetailService positionDetailService;

	@GetMapping("/position")
	public ResponseEntity<List<PositionResponse>> getAllPositions() {
		return ResponseEntity.ok(positionService.getAllActivePosition());
	}

	@GetMapping("/position-detail")
	public ResponseEntity<List<PositionDetailResponse>> getAllPositionDetail() {
		return ResponseEntity.ok(positionDetailService.getAllActivePositionDetail());
	}

	@GetMapping("/position-detail-by-position-id")
	public ResponseEntity<List<PositionDetailResponse>> getAllPositionDetail(@RequestParam(value = "position_id") Long positionId) {
		return ResponseEntity.ok(positionDetailService.getAllActivePositionDetailByPositionId(positionId));
	}

}
