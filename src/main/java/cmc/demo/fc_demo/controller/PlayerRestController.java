package cmc.demo.fc_demo.controller;

import cmc.demo.fc_demo.dto.request.PlayerFilterRequest;
import cmc.demo.fc_demo.dto.request.PlayerRequest;
import cmc.demo.fc_demo.dto.response.PlayerResponse;
import cmc.demo.fc_demo.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/player")
@Slf4j
public class PlayerRestController {
	@Autowired
	private PlayerService playerService;

	@GetMapping
	public ResponseEntity<List<PlayerResponse>> getAllPlayers() {
		return ResponseEntity.ok(playerService.getAllPlayers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable("id") Long playerId) {
		return ResponseEntity.ok(playerService.findById(playerId));
	}

	@PostMapping("/filter")
	public ResponseEntity<List<PlayerResponse>> getAllFilterPlayer(@RequestBody PlayerFilterRequest request) {
		log.info("Start filter player by request: {}", request.toString());
		return ResponseEntity.ok(playerService.findAllPlayerFilter(request));
	}

	@PostMapping("/create-or-update")
	public ResponseEntity<String> createOrUpdatePlayer(@RequestBody PlayerRequest playerRequest) {
		log.info("Start create or update player with request: {}", playerRequest);
		return ResponseEntity.ok(playerService.createOrUpdatePlayer(playerRequest));
	}
}
