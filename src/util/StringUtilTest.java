package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testGetRealTime() {
		System.out.println(StringUtil.getRealTime(String.valueOf(System.currentTimeMillis())));
		System.out.println(StringUtil.getRealTime("13390678014242"));
	}

	@Test
	public void testGetCurrentData() {
		System.out.println(StringUtil.getCurrentData());

	}

}
