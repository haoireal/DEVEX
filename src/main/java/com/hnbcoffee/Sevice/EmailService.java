package com.hnbcoffee.Sevice;

import org.springframework.stereotype.Service;

import com.hnbcoffee.Entity.User;
import com.hnbcoffee.Utils.EmailUtils;

@Service("emailService")
public class EmailService {
	private static final String EMAIL_VERIFI_FORGOT = "ĐÂY LÀ MÃ XÁC THỰC CỦA BẠN CHO TRANG WEB H&B COFFEE";
	private static final String EMAIL_SHARE = "MỘT NGƯỜI BẠN ĐÃ CHIA SẼ CHO BẠN VIDEO NÀY HÃY CÙNG VÀO XEM NHÉ!";
	EmailUtils emailUntil = new EmailUtils();

	public void sendEmail(User recipient, String type) {

		try {
			String content = null;
			String subject = null;
			switch (type) {
			case "code": {
				subject = EMAIL_VERIFI_FORGOT;
				content = "Giử " + recipient.getFullname()
						+ ", đây là mã xác thực của bạn tại H&B COFFEE " + recipient.getVericode();
			}
				break;
			case "share": {
				subject = EMAIL_SHARE;

			}
			default:
			}

			// send message
			emailUntil.setup(subject, content, recipient.getEmail());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
