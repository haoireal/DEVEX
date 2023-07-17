package com.Devex.Controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.Config.TwilioConfig;
import com.Devex.DTO.OtpRequestDTO;
import com.Devex.DTO.OtpResponseDTO;
import com.Devex.DTO.OtpValidationRequest;
import com.Devex.Sevice.TwilioOTPService;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/otp")
@Slf4j
public class OTPController {
	@Autowired
	private TwilioOTPService otpService;
	
	@GetMapping("/process")
	public String processSMS() {
		return "SMS sent";
	}
	
	@PostMapping("/sent-otp")
	public OtpResponseDTO sendOtp(@RequestBody OtpRequestDTO otpRequest) {
		log.info("inside sendOtp :: " + otpRequest.getUsername());
		return otpService.sendSMS(otpRequest);
	}
	
	@PostMapping("/validate-otp")
	public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
		log.info("inside validateOtp :: " + otpValidationRequest.getUsername() + " " + otpValidationRequest.getOtpNumber());
		return otpService.validateOtp(otpValidationRequest);
	}
}
