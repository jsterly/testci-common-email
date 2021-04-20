package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import junit.framework.Assert;



public class EmailTest {

private static final String[] TEST_EMAILS = { "ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd" };

private String[] testValidChars = {" ", "a", "A", "\uc5ec", "0123456789", "012345678901234567890", "\n"};

private EmailConcrete email;

@Before
public void setUpEmailTest() throws Exception{
	email = new EmailConcrete();
}

@After
public void tearDownEmailTest() throws Exception{

}//closes tearDownEmailTest
//Makes sure that addBcc works
@Test
public void testAddBcc() throws Exception
{
	email.addBcc(TEST_EMAILS);
	assertEquals(3, email.getBccAddresses().size());

}
//makes sure that addcc works
@Test
public void testAddCc() throws Exception
{
	email.addCc(TEST_EMAILS);
	assertEquals(3, email.getCcAddresses().size());
}
// makes sure that addheader works as long as valid strings are in parameters
@Test
public void testAddHeader() throws Exception
{
	email.addHeader("TestName", "Test Value");
	assertEquals(1, email.headers.size());


}

// test addheader if key is null
@Test(expected = IllegalArgumentException.class)
public void testAddHeaderWithNull() throws Exception
{
	email.addHeader(null, "Test Value");
	assertEquals(0, email.headers.size());

}

//test reply to
@Test
public void testAddReplyTo() throws Exception
{

	email.addReplyTo("a@bc.com", "Test name");
	assertEquals(1, email.getReplyToAddresses().size());
}


//test buildmimemessage
@Test(expected = EmailException.class)
public void testBuildMimeMessage() throws Exception
{
	email.setHostName("HostTest");
	email.setSmtpPort(1234);
	email.setFrom("a@b.com");
	email.addTo("c@d.com");
	email.setSubject("test mail");
	email.setCharset("ISO-8859-1");
	email.setContent("test content", "text/plain");
	email.addCc("test@abc.com");
	email.addBcc("testname@testing.com");
	email.addHeader("Test name", "abc");
	email.addReplyTo("a@bc.com", "Test name");
	//setPopBeforeSmtp is what makes the exception required
	email.setPopBeforeSmtp(true,  "HostTest", "username", "password");
	email.buildMimeMessage();


}
//tests gethostname with valid parameter
@Test
public void testGetHostName() throws Exception
{
	email.setHostName("TestHost");
	String hostName = email.getHostName();

	assertEquals("TestHost", hostName);


}

//test gethostname with null
@Test
public void testGetHostNameWithNull() throws Exception
{
	email.setHostName(null);
	assertEquals(null, email.getHostName());
}

//tests gethost name with session
@Test
public void testGetHostNameWithSession() throws Exception
{
	Properties properties = new Properties();
	Session session = Session.getDefaultInstance(properties, null);
	properties.put(EmailConstants.MAIL_HOST, "hostTest");
	email.setMailSession(session);
	assertEquals("hostTest", email.getHostName());
}

//test getmail session, exception required
@Test(expected = EmailException.class)
public void testGetMailSession() throws Exception
{

	email.setSmtpPort(1234);
	email.setFrom("a@b.com");
	email.addTo("c@d.com");
	email.setSubject("test mail");
	email.setCharset("ISO-8859-1");
	email.setContent("test content", "text/plain");
	email.addCc("test@abc.com");
	email.addBcc("testname@testing.com");
	email.addHeader("Test name", "abc");
	email.addReplyTo("a@bc.com", "Test name");
	email.setSSLOnConnect(true);
	email.getMailSession();

}

@Test
public void testgetSentDateNull() throws Exception
{
	email.setSentDate(null);
	email.getSentDate();
}

@Test
public void testgetSentDate() throws Exception
{
	Date currentDate = new Date();
	email.setSentDate(currentDate);
	assertEquals(currentDate, email.getSentDate());
}
@Test
public void testgetSocketConnectionTimeout()
{
	email.setSocketConnectionTimeout(5);
	assertEquals(5, email.getSocketConnectionTimeout());
}
@Test
public void testSetFrom() throws Exception
{
	email.setFrom("a@bc.com");

}


}//closes EmailTest
