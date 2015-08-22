package web;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "Timeline:jason:";
		System.out.println(DigestUtils.md5Hex(str));
		System.out.println(DigestUtils.md2Hex(UUID.randomUUID().toString()));
		System.out.println(DigestUtils.md5Hex(UUID.randomUUID().toString()));
		System.out.println(DigestUtils.sha1Hex(UUID.randomUUID().toString()));
	}

}
