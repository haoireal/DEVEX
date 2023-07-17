package com.Devex.Sevice;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Devex.Config.TwilioConfig;
import com.Devex.DTO.OtpRequestDTO;
import com.Devex.DTO.OtpResponseDTO;
import com.Devex.DTO.OtpStatus;
import com.Devex.DTO.OtpValidationRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioOTPService {
	@Autowired
	private TwilioConfig twilioConfig;
	Map<String, String> otpMap = new HashMap<>();

	public OtpResponseDTO sendSMS(OtpRequestDTO otpRequest) {
		OtpResponseDTO otpResponse = null;
		try {
			PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
			String otp = generateOtp();
			String otpMessage = "Devex: Ma xac thuc dang ky tai khoan cua ban la " + otp
					+ " Ma co hieu luc trong 3 phut. Khong chia se ma nay voi nguoi khac.";
			Message message = Message.creator(to, from, otpMessage).create();
			otpMap.put(otpRequest.getUsername(), otp);
			otpResponse = new OtpResponseDTO(OtpStatus.DELIVERED, otpMessage);
		} catch (Exception e) {
			// TODO: handle exception
			otpResponse = new OtpResponseDTO(OtpStatus.FAILED, e.getMessage());
		}
		return otpResponse;
	}

	public String validateOtp(OtpValidationRequest otpValidationRequest) {
		Set<String> keys = otpMap.keySet();
		String username = null;
		for (String key : keys)
			username = key;
		if (otpValidationRequest.getUsername().equals(username)) {
			otpMap.remove(username, otpValidationRequest.getOtpNumber());
			return "OTP is valid!";
		} else {
			return "OTP is invalid!";
		}
	}
	
	private String generateOtp() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}

}
