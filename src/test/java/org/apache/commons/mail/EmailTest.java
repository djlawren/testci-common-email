package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	
	private static final String[] TEST_EMAILS = { "ab@bc.org", "abawdasdwas@dfaxcasdweda.com", "asdwasd@asdawid.net" };	// Sample set of emails
	private static final String[] EMPTY_LIST = {};	// Empty set of emails
	
	private EmailConcrete email;	// Email object to test
	
	// Setup the email object before running any test
	@Before
	public void setupEmailTest() throws Exception {
		email = new EmailConcrete();
	}
	
	// Test the addBcc method
	@Test
	public void testAddBcc() throws Exception {
		email.addBcc(TEST_EMAILS);	// Add the three email dataset
		
		assertEquals(3, email.getBccAddresses().size());	// Check if the count == 3
	}
	
	// Test the exception caught on an empty list
	@Test(expected = EmailException.class)
	public void testAddBccException() throws Exception {
		email.addBcc(EMPTY_LIST);	// Add no emails
		
		fail();	// Fail the test
	}
	
	// Test the add list of emails to CC
	@Test
	public void testAddCc() throws Exception {
		email.addCc(TEST_EMAILS);	// Add list of emails to CC
		
		assertEquals(3, email.getCcAddresses().size());	// Check if the count == 3
	}
	
	// Test the exception on empty list
	@Test(expected = EmailException.class)
	public void testAddCcException() throws Exception {
		email.addCc(EMPTY_LIST);	// Give it no emails
		
		fail();	// Fail the test
	}

	// Test the correct usage for add header
	@Test
	public void testAddHeader() throws Exception {
		email.addHeader("Key", "Value");	// Give temp values 
		
		assertEquals(1, email.getHeaders().size());	// Check if there are one headers
	}
	
	// Test the exception on no key given
	@Test(expected = IllegalArgumentException.class)
	public void testAddHeaderEmptyKey() throws Exception {
		email.addHeader("", "Value");	// Give value but no key
		
		fail();	// Fail the test
	}
	
	// Test the exception on no value given
	@Test(expected = IllegalArgumentException.class)
	public void testAddHeaderEmptyValue() throws Exception {
		email.addHeader("Key", "");	// Give key but no value
		
		fail();	// Fail the test
	}

	// Test the addReplyTo method
	@Test
	public void testAddReplyTo() throws Exception {
		email.addReplyTo("abc@acb.org", "Hello world", "");	// Add one reply to
		
		assertEquals(1, email.getReplyList().size());	// Check if there is one in the list
	}

	// Check for exception on building mime message twice
	@Test(expected = IllegalStateException.class)
	public void testBuildMimeMessageException() throws Exception {
		email.setHostName("localhost");
		
		email.addTo("hello@hello.com");
		email.addCc("hello@hello.com");
		email.addBcc("hello@hello.com");
		email.addHeader("Key", "Value");
		email.addReplyTo("abc@acb.org", "Hello world", "");
		email.setFrom("dasdaw@asdwasd.net", "Mmhmm", "");
		
		email.buildMimeMessage();	// Build once
		email.buildMimeMessage();	// Build a second time to get the exception
	}
	
	// Check correct usage of buildMimeMessage
	@Test
	public void testBuildMimeMessage() throws Exception {
		email.setHostName("localhost");
		
		email.addTo("hello@hello.com");
		email.addCc("hello@hello.com");
		email.addBcc("hello@hello.com");
		email.addHeader("Key", "Value");
		email.addReplyTo("abc@acb.org", "Hello world", "");
		email.setFrom("dasdaw@asdwasd.net", "Mmhmm", "");
		email.setSubject("Subject line");
		email.setContent(new MimeMultipart("Hello"));
		email.setBounceAddress("hello@hello.com");
		
		email.buildMimeMessage();	// Build message after giving it sufficient values
		
		assertNotNull(email.getMimeMessage());	// Make sure it isn't null
	}
	
	// Test buildMimeMessage without enough information
	@Test(expected = EmailException.class)
	public void testBuildMimeMessageEmailException() throws Exception {
		email.setHostName("localhost");	// Set host name
		
		email.buildMimeMessage();	// Will fail because not enough details are set
		
		fail();	// Fail the test
	}

	// Test the getHostName method
	@Test
	public void testGetHostName() throws Exception {
		email.setHostName("localhost");	// Set a host name
		
		assertNotNull(email.getHostName());	// Make sure it isn't null
	}
	
	// Test the getHostName method without setting it first
	@Test
	public void testGetHostNameNull() throws Exception {
		
		assertNull(email.getHostName());	// Was never set, so it will be null
	}

	// Test the exception on getting mail session without setting up
	@Test(expected = EmailException.class)
	public void testGetMailSession() throws Exception {
		email.getMailSession();	// Never set up, so  should give issue
	}

	// Test the getSocketConnectionTimeout method
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		email.setSocketConnectionTimeout(1000);	// Set the value
		
		assertEquals(1000, email.getSocketConnectionTimeout());	// Check if it is equal
	}

	// Test the getSentDate method
	@Test
	public void testGetSentDate() throws Exception {
		email.setSentDate(new Date()); // Give sent date
		
		assertNotNull(email.getSentDate());	// Assert it has been set
	}

	// Test the setFrom method
	@Test
	public void testSetFrom() throws Exception {
		email.setFrom("test@test.com");	// Give a value from the test email
		
		assertNotNull(email.getFromAddress());	// Assert it is not null
	}

	// Not really necessary to tear down anything
	@After
	public void tearDownEmailTest() throws Exception {
		
	}
}
