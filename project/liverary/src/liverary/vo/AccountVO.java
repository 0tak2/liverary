package liverary.vo;

public class AccountVO {
	private String ano;
	private String aname;
	private String adepartment;
	private String abirth;
	private String acreatedAt;
	private String aphone;
	private String aemail;
	private String aaddr;
	private String apoint;
	private String alevel;
	private String ausername;
	private String apassword;
	
	public AccountVO() {
	}

	public AccountVO(String ano, String aname, String adepartment, String abirth, String acreatedAt, String aphone,
			String aemail, String aaddr, String apoint, String alevel, String ausername, String apassword) {
		super();
		this.ano = ano;
		this.aname = aname;
		this.adepartment = adepartment;
		this.abirth = abirth;
		this.acreatedAt = acreatedAt;
		this.aphone = aphone;
		this.aemail = aemail;
		this.aaddr = aaddr;
		this.apoint = apoint;
		this.alevel = alevel;
		this.ausername = ausername;
		this.apassword = apassword;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getAdepartment() {
		return adepartment;
	}

	public void setAdepartment(String adepartment) {
		this.adepartment = adepartment;
	}

	public String getAbirth() {
		return abirth;
	}

	public void setAbirth(String abirth) {
		this.abirth = abirth;
	}

	public String getAcreatedAt() {
		return acreatedAt;
	}

	public void setAcreatedAt(String acreatedAt) {
		this.acreatedAt = acreatedAt;
	}

	public String getAphone() {
		return aphone;
	}

	public void setAphone(String aphone) {
		this.aphone = aphone;
	}

	public String getAemail() {
		return aemail;
	}

	public void setAemail(String aemail) {
		this.aemail = aemail;
	}

	public String getAaddr() {
		return aaddr;
	}

	public void setAaddr(String aaddr) {
		this.aaddr = aaddr;
	}

	public String getApoint() {
		return apoint;
	}

	public void setApoint(String apoint) {
		this.apoint = apoint;
	}

	public String getAlevel() {
		return alevel;
	}

	public void setAlevel(String alevel) {
		this.alevel = alevel;
	}

	public String getAusername() {
		return ausername;
	}

	public void setAusername(String ausername) {
		this.ausername = ausername;
	}

	public String getApassword() {
		return apassword;
	}

	public void setApassword(String apassword) {
		this.apassword = apassword;
	}
}
