package hue.controllers;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import hue.domain.BridgeCredentials;
import hue.dto.PHNotifications;
import hue.services.PHBridgeService;
import hue.services.PHLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HueController {

	@Autowired
    PHLightService lightService;

	@Autowired
	PHBridgeService bridgeService;

	@RequestMapping(value = "/bridge/connect", method = RequestMethod.POST)
	public ResponseEntity<String> connectToBridge(@RequestBody BridgeCredentials credentials) {
		bridgeService.connectToAccessPoint(credentials.getUsername(), credentials.getIp());
		return new ResponseEntity<>("Connected", HttpStatus.OK);
	}

	@RequestMapping(value = "/bridge/status", method = RequestMethod.GET)
	public ResponseEntity<PHNotifications.BridgeStatus> getBridgeStatus() {
		PHNotifications.BridgeStatus bridgeStatus = bridgeService.getBridgeStatus();
		return new ResponseEntity<>(bridgeStatus, HttpStatus.OK);
	}

	@RequestMapping(value = "/lights/{id}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> setLightState(@PathVariable String id, @RequestBody PHLightState lightState) {
		boolean success = lightService.updateLight(id, lightState);
		return new ResponseEntity<>(success, HttpStatus.OK);
	}

	@RequestMapping(value = "/lights", method = RequestMethod.GET)
	public ResponseEntity<List<PHLight>> getLights() {
		List<PHLight> lights = lightService.getLights();
		return new ResponseEntity<>(lights, HttpStatus.OK);
	}
}
