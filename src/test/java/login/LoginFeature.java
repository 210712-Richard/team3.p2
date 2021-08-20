package login;

import com.intuit.karate.junit5.Karate;

public class LoginFeature {
		@Karate.Test
		Karate testLogin() {
			return Karate.run("login").relativeTo(getClass());
		}
}
