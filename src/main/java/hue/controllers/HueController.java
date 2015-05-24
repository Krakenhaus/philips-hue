package hue.controllers;


import com.philips.lighting.model.PHLight;
import hue.domain.CustomLightState;
import hue.domain.HueSDK;
import hue.dto.BridgeDto;
import hue.dto.LightsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HueController {

	/*
	Assumptions are that the hue lights are set up with the ip of the bridge and the username known
	 */

	@Autowired
	LightsDto lightsDto;

	@Autowired
	BridgeDto bridgeDto;

	@RequestMapping(value = "/bridge/status", method = RequestMethod.GET)
	public ResponseEntity<String> getBridgeStatus() {
		bridgeDto.checkForBridgeErrors();
		if(bridgeDto.getStatus() == HueSDK.PENDING) {
			return new ResponseEntity<String>("Processing", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Connected", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/lights/{id}", method = RequestMethod.POST)
	public ResponseEntity<PHLight> setLightState(@PathVariable String id, @RequestBody CustomLightState customLightState) {
		bridgeDto.checkForBridgeErrors();
		PHLight updatedLight = lightsDto.setLightRGB(id, customLightState);

		return new ResponseEntity<PHLight>(updatedLight, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/lights", method = RequestMethod.GET)
	public ResponseEntity<List<PHLight>> getLights() {
		bridgeDto.checkForBridgeErrors();
		List<PHLight> lights = lightsDto.getLights();

		return new ResponseEntity<List<PHLight>>(lights, HttpStatus.OK);
	}

	public void setLightsDto(LightsDto lightsDto) {
		this.lightsDto = lightsDto;
	}

	public void setBridgeDto(BridgeDto bridgeDto) {
		this.bridgeDto = bridgeDto;
	}
}
