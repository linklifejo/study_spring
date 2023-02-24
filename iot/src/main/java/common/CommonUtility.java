package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import member.MemberVO;

@Service
public class CommonUtility {
	
	//실행되고 있는 app의 url
	public String appURL(HttpServletRequest request) {
		//  http://127.0.0.1/iot/naverCallback
		//--> http://127.0.0.1/iot
		return request.getRequestURL().toString()
				.replace( request.getServletPath(), "");
	}
	
	//API 요청
	public String requestAPI( String apiURL ) {
		try {	
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			  BufferedReader br;
			  System.out.print("responseCode="+responseCode);
			  if(responseCode==200) { // 정상 호출
			    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			  } else {  // 에러 발생
			    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			  }
			  String inputLine;
			  StringBuffer res = new StringBuffer();
			  while ((inputLine = br.readLine()) != null) {
			    res.append(inputLine);
			  }
			  br.close();
			  if(responseCode==200) {
				  System.out.println( res.toString() );
			  }
			  apiURL = res.toString();
		} catch (Exception e) {
		  System.out.println(e);
		}		
		return apiURL;
	}
	
	//API 요청 - 헤더정보추가
	public String requestAPI( String apiURL, String property ) {
		try {	
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", property);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode="+responseCode);
			if(responseCode==200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if(responseCode==200) {
				System.out.println( res.toString() );
			}
			apiURL = res.toString();
		} catch (Exception e) {
			System.out.println(e);
		}		
		return apiURL;
	}
	
	
	
	
	
	//이메일로 임시비번 보내기
	public boolean sendPassword(MemberVO vo, String pw) {
		boolean send = true;
		
		HtmlEmail email = new HtmlEmail();
		email.setCharset("utf-8");
		email.setDebug(true);
		
		email.setHostName("smtp.naver.com"); //이메일보낼서버지정
		//이메일로 임시비번을 보내는 이는 사이트 관리자
		email.setAuthentication("itstudydev", "itlearning10102"); //관리자 아이디,비번 입력
		email.setSSLOnConnect(true);  //로그인버튼 누르기
		
		try {
		email.setFrom("itstudydev@naver.com", "IoT 관리자"); //보내는이 지정
		email.addTo( vo.getEmail(), vo.getName()  ); //받는이 지정
		email.setSubject("IoT 로그인 임시 비밀번호 확인") ;//제목
		
		//내용
		StringBuffer msg = new StringBuffer();
		msg.append("<html>");
		msg.append("<body>");
		msg.append("<h3>임시비번</h3>");
		msg.append("<div><strong>"+ pw +"</strong></div>");
		msg.append("<div>임시 비밀번호로 로그인 후 비밀번호를 변경하세요</div>");
		msg.append("</body>");
		msg.append("</html>");
		email.setHtmlMsg( msg.toString() );
		
		email.send(); //보내기버튼 클릭
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
			send = false;
		}
		return send;
	}
	
	
	
	//솔트를 사용해 비밀번호를 암호화하기
	public String getEncrypt(String pw, String salt) {
		pw = pw + salt;
		
		try {
			MessageDigest md		//암호화방식: SHA-256
				= MessageDigest.getInstance("SHA-256");
			md.update( pw.getBytes() );
			byte[] bytes = md.digest();
			
			StringBuffer val = new StringBuffer();
			for(byte b : bytes) {
				val.append( String.format("%02x", b) );
			}
			pw = val.toString(); //암호화된 비번		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return pw;
	}
	
	//암호화에 사용할 솔트 생성
	public String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[16];
				
		random.nextBytes(bytes);
		//16개의 바이트를 16진수로 변환
		StringBuffer salt = new StringBuffer();
		for(byte b : bytes) {
			salt.append( String.format("%02x", b) );
		}
		return salt.toString();
	}
}