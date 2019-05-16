package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.util.SendMessageUtil;

public class SendMessageTest {

	@Test
	public void test() {
		SendMessageUtil.getCode("17805058402");
	}

}
