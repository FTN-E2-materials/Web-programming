package servletengine;

/**
 * Klasa koja reprezentuje datoteku koja je poslata na server.
 * 
 * @author Milan Vidakovic
 */
public class UploadedFile {
	private String fieldName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String s) {
		fieldName = s;
	}

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String s) {
		filePath = s;
	}

	public UploadedFile(String fn, String fp) {
		fieldName = fn;
		filePath = fp;
	}
}