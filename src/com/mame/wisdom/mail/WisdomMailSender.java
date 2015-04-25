package com.mame.wisdom.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mame.wisdom.util.DbgUtil;

public class WisdomMailSender {

	private final static String TAG = WisdomMailSender.class.getSimpleName();

	public static boolean sendContactMail(String name, String address,
			String message) throws UnsupportedEncodingException {

		DbgUtil.showLog(TAG, "sendInqueryMail");

		if (address == null) {
			throw new IllegalArgumentException("mail address is null");
		}

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		String msgBody = "userName: " + name + "<br>" + "message: " + message;

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(address, "Inquery"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
					"mame0112@gmail.com", "chienowa"));
			msg.setSubject("Contact");
			msg.setText(msgBody);
			msg.setContent(msgBody, "text/html");
			Transport.send(msg);
			DbgUtil.showLog(TAG, "Successfully sent message.");
			return true;
		} catch (MessagingException e) {
			DbgUtil.showLog(TAG, "MessagingException: " + e.getMessage());
			return false;
		} catch (Exception e) {
			DbgUtil.showLog(TAG, "Exception: " + e.getMessage());
			return false;
		}
	}
}
