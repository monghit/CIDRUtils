package com.monghit.utils.cidrutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.UnknownHostException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.monghit.utils.cidrutils.CIDRUtils;


class CIDRUtilsTest {
	


	 
	@ParameterizedTest(name = "#{index} - Test with {0} and {1} is {2}")
	@CsvSource(value = {
		    "10.88.135.144/28, 10.88.135.144 ,true",
		    "10.88.135.144/28, 10.88.135.159 ,true",
		    "10.88.135.144/28, 10.88.135.145 ,true",
		    "10.88.135.144/28, 10.88.135.158 ,true",
		    "10.88.135.144/28, 10.88.135.150 ,true",
		    "10.88.135.144/28, 10.88.135.143 ,false",
		    "10.88.135.144/28, 10.88.135.160 ,false",
		}, ignoreLeadingAndTrailingWhitespace = true)

	void test_IP_IN_RANGE(String cidr,String ip, boolean isInRange) throws UnknownHostException {
		CIDRUtils cu = new CIDRUtils(cidr);

		assertEquals(cu.isInRange(ip),isInRange );


	}
	
	
	@ParameterizedTest(name = "#{index} - Test with CIDR = {0}, networkAddress = {1} is {3} and broadCasterAddress = {2} is {4}")
	@CsvSource(value = {
		    "10.88.135.144/28, 10.88.135.144 ,10.88.135.159 ,true,true",
		    "10.88.135.144/28, 10.88.135.143 ,10.88.135.159 ,false,true",
		    "10.88.135.144/28, 10.88.135.144 ,10.88.135.155 ,true,false"
		}, ignoreLeadingAndTrailingWhitespace = true)
	void test_NETWORKADDRESS_BROADCASTERADDRESS(String cidr,String netWorkIp,String broadCIp, boolean isNetWorkIp, boolean isBroadCIp) throws UnknownHostException {
		CIDRUtils cu = new CIDRUtils(cidr);
		assertEquals(isNetWorkIp,cu.getNetworkAddress().equals(netWorkIp));
		assertEquals(isBroadCIp,cu.getBroadcastAddress().equals(broadCIp));
	}
	
	@ParameterizedTest(name = "#{index} - Test with CIDR = {0} with no BROADCAST IP- WHEN networkPart >30")
	@CsvSource(value = {
		    "10.88.135.144/31",
		}, ignoreLeadingAndTrailingWhitespace = true)
	void test_BROADCASTERADDRESS_EXCEPTION(String cidr) throws UnknownHostException {
		CIDRUtils cu = new CIDRUtils(cidr);
		
		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
		           () -> cu.getBroadcastAddress(),
		           " "
		    );

		    assertTrue(thrown.getMessage().contains("not have a broadcast IP!"));


	}
	
	
	@ParameterizedTest(name = "#{index} - Test with CIDR = {0} NOT valid")
	@CsvSource(value = {
		    "10.88.135.144",
		}, ignoreLeadingAndTrailingWhitespace = true)
	void test_CDIR_EXCEPTION(String cidr) throws UnknownHostException {
		
		IllegalArgumentException thrown = assertThrows(
				IllegalArgumentException.class,
		           () -> new CIDRUtils(cidr),
		           " "
		    );

		    assertTrue(thrown.getMessage().contains("not an valid CIDR format!"));

	}
	
	

}

