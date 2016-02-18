package hue.controllers;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import hue.domain.BridgeCredentials;
import hue.domain.UrlImage;
import hue.dto.PHNotifications;
import hue.services.PHBridgeService;
import hue.services.PHLightService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
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
		return new ResponseEntity<>("Initializing", HttpStatus.OK);
	}

	@RequestMapping(value = "/bridge/status", method = RequestMethod.GET)
	public ResponseEntity<PHNotifications.BridgeStatus> getBridgeStatus() {
		PHNotifications.BridgeStatus bridgeStatus = bridgeService.getBridgeStatus();
		return new ResponseEntity<>(bridgeStatus, HttpStatus.OK);
	}

	@RequestMapping(value = "/lights/{id}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> setLightState(@PathVariable String id, @RequestBody PHLightState lightState) {
		boolean isSuccessful = lightService.updateLight(id, lightState);
		return new ResponseEntity<>(isSuccessful, HttpStatus.OK);
	}

	@RequestMapping(value = "/lights/image", method = RequestMethod.POST)
	public ResponseEntity<Boolean> setLightsToImage(@RequestBody UrlImage urlImage) throws Exception {
		File temp = File.createTempFile("test", ".tmp");
		URL url = new URL(urlImage.getUrl());
		FileUtils.copyURLToFile(url, temp);

		List<String> lightIds = new ArrayList<>();
		lightIds.add("1");
		lightIds.add("2");
		lightIds.add("3");

		boolean isSuccessful = lightService.setLightsToImage(Files.readAllBytes(temp.toPath()), lightIds);
		return new ResponseEntity<>(isSuccessful, HttpStatus.OK);
	}


	@RequestMapping(value = "/lights", method = RequestMethod.GET)
	public ResponseEntity<List<PHLight>> getLights() {
		List<PHLight> lights = lightService.getLights();
		return new ResponseEntity<>(lights, HttpStatus.OK);
	}
}
