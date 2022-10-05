package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.request.PlayerFilterRequest;
import cmc.demo.fc_demo.dto.request.PlayerRequest;
import cmc.demo.fc_demo.dto.request.PlayerValidRequest;
import cmc.demo.fc_demo.dto.response.PlayerResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PlayerService {
	List<PlayerResponse> getAllPlayers();

	PlayerResponse findById(Long id);

	String createOrUpdatePlayer(PlayerRequest playerRequest);

	String deletePlayer(Long id);

	List<PlayerResponse> findAllPlayerFilter(PlayerFilterRequest request);

	void savePlayer(PlayerRequest playerRequest);

	List<PlayerResponse> getAllActivePlayers();

	Map<String, String> checkValidPlayer(PlayerValidRequest request);

}
