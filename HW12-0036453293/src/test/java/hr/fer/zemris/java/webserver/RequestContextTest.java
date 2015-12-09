package hr.fer.zemris.java.webserver;

import static org.junit.Assert.*;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class RequestContextTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void ConstructorExceptionsTest() {
		new RequestContext(null, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RCCookie>());
	}
	
	@Test
	public void GeneralTest1() {
		OutputStream os = System.out;
		Map<String, String> parameters = new HashMap<>();
		parameters.put("param1", "test1");
		Map<String, String> persParameters = new HashMap<>();
		persParameters.put("param2", "test2");
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		RCCookie cookie = new RCCookie("sid", "sessionID1", 123, "127.0.0.1", "/");
		cookies.add(cookie);
		RequestContext rc = new RequestContext(os, parameters, persParameters, cookies);
		rc.setEncoding("UTF-8");
		rc.setStatusCode(502);
		rc.setStatusText("Internal server error");
		rc.setMimeType("text/html");
		try {
			rc.write("Ovo je text koji ce se ispisati u konzoli nakon zaglavlja. Nisam siguran kako bih to testirao.");
		} catch (IOException ignore) {
		}
		
		assertEquals("Expected = 'test1'", "test1", rc.getParameter("param1"));
		
		assertEquals("Expected = size 1", 1, rc.getParameterNames().size());
		assertEquals("Expected = 'test2'", "test2", rc.getPersistentParameter("param2"));
		assertEquals("Expected = size 1", 1, rc.getPersistentParameterNames().size());
		rc.setPersistentParameter("param2", "changed");
		assertEquals("Expected = 'changed'", "changed", rc.getPersistentParameter("param2"));
		rc.removePersistentParameter("param2");
		assertEquals("Expected = size 0", 0, rc.getPersistentParameterNames().size());
		
		rc.setTemporaryParameter("param1", "temp1");
		rc.setTemporaryParameter("param2", "temp2");
		assertEquals("Expected = 'temp2'", "temp2", rc.getTemporaryParameter("param2"));
		assertEquals("Expected = size 2", 2, rc.getTemporaryParameterNames().size());
		rc.removeTemporaryParameter("param2");
		assertEquals("Expected = size 1", 1, rc.getTemporaryParameterNames().size());
		
		assertEquals("Expected = 'sid'", "sid", cookie.getName());
		assertEquals("Expected = 'sessionID1'", "sessionID1", cookie.getValue());
		assertEquals("Expected = '127.0.0.1'", "127.0.0.1", cookie.getDomain());
		assertEquals("Expected = '/'", "/", cookie.getPath());
		assertEquals("Expected = 123", 123, cookie.getMaxAge());
	}
	
	
}
