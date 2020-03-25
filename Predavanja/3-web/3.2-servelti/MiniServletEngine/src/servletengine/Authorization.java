package servletengine;

/**
 * Klasa koja omogucuje osnovnu autorizaciju tipa username:password.
 * 
 * @author Milan Vidakovic
 */
public class Authorization {

	private static INIFile ini = new INIFile("acl.ini");

	public static boolean requiresBasicAuthorization(String resource) {
		String inMap = ini.getString("Basic", resource, null);
		if (inMap != null) {
			// ovaj resurs je na listi zasticenih resursa Basic metodom
			return true;
		} else {
			// nije na listi zasticenih resursa Basic metodom
			return false;
		}
	}

	public static boolean requiresDigestAuthorization(String resource) {
		String inMap = ini.getString("Digest", resource, null);
		if (inMap != null) {
			// ovaj resurs je na listi zasticenih resursa Digest metodom
			return true;
		} else {
			// nije na listi zasticenih resursa Digest metodom
			return false;
		}
	}

	public static boolean validBasic(String resource, String credentials) {
		if (credentials == null)
			return false;

		String decoded = Base64.decode(credentials);
		String inMap = ini.getString("Basic", resource, null);
		if (inMap != null) {
			// resurs postoji u mapi
			if (decoded.equals(inMap)) {
				// sve je OK
				return true;
			} else {
				// nije dobar par uname:pass
				return false;
			}
		} else {
			// ovaj resurs ne postoji u mapi
			return false;
		}
	}

	public static String getNonce() {
		return MD5.getHashString("id" + System.currentTimeMillis());
	}

	/*
	 * Tipican http zahtev klijenta sa Digest autorizacijom:
	 * 
	 * Authorization: Digest username="proba", realm="protect.html", qop="auth",
	 * algorithm="MD5", uri="/protect.html",
	 * nonce="dcd98b7102dd2f0e8b11d0f600bfb0c093", nc=00000001,
	 * cnonce="aa671f12a8587e2cffe73519863f9dbb",
	 * opaque="5ccc069c403ebaf9f0171e9517f40e41",
	 * response="f90a24d42f7d59fa0496cbbce6aacfe6"
	 */

	/**
	 * Izdvaja iz prosledjenog teksta vrednost parametra. Ako ne nadje, vraæa
	 * null.
	 */
	private static String extractParameter(String text, String name) {
		String retVal = null;
		String[] tokens = text.split(name + "=\"?");
		if (tokens.length > 0) {
			String value = tokens[1];
			int idx1 = value.indexOf(',');
			int idx2 = value.indexOf('"');
			int idxmn = Math.min(idx1, idx2);
			int idxmx = Math.max(idx1, idx2);
			if (idxmn == -1 && idxmx == -1)
				return value;
			if (idxmn == -1)
				return value.substring(0, idxmx);
			else if (idxmn > -1)
				return value.substring(0, idxmn);
			return value;
		}
		return retVal;
	}

	public static boolean validDigest(String resource, String credentials) {
		if (credentials == null)
			return false;
		String response = extractParameter(credentials, "response");
		String nc = extractParameter(credentials, "nc");
		String cnonce = extractParameter(credentials, "cnonce");
		String qop = extractParameter(credentials, "qop");
		String nonce = extractParameter(credentials, "nonce");
		if (response == null)
			return false;
		String inMap = ini.getString("Digest", resource, null);
		if (inMap != null) {
			String HA1 = inMap;
			String HA2 = MD5.getHashString("GET:/" + resource);
			String computed = MD5.getHashString(HA1 + ":" + nonce + ":" + nc
					+ ":" + cnonce + ":" + qop + ":" + HA2);
			// resurs postoji u mapi
			if (response.equals(computed)) {
				// sve je OK
				return true;
			} else {
				// nije dobar par uname:pass
				return false;
			}
		} else {
			// ovaj resurs ne postoji u mapi
			return false;
		}
	}
}