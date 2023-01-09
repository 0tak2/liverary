package liverary.vo;

import java.util.Date;

public class AccountVO {
	private int ano;
	private String aname;
	private String adepartment;
	private Date abirth;
	private Date acreatedAt;
	private String aphone;
	private String aemail;
	private String aaddr;
	private int apoint;
	private int alevel;
	private String ausername;
	private String apassword;
	private boolean adisabled;
	private Date adisabledAt;
	
	public AccountVO() {
	}
	
	public AccountVO(int ano, String aname, Date abirth, String aphone, String ausername) {
		super();
		this.ano = ano;
		this.aname = aname;
		this.abirth = abirth;
		this.aphone = aphone;
		this.ausername = ausername;
		this.adisabled = false;
		this.adisabledAt = null;
	}

	public AccountVO(int ano, String aname, String adepartment, Date abirth, Date acreatedAt, String aphone,
			String aemail, String aaddr, int apoint, int alevel, String ausername, String apassword) {
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
		this.adisabled = false;
		this.adisabledAt = null;
	}
	
	// 이용자 인서트용
	public AccountVO(String aname, Date abirth, Date acreatedAt, String aphone,
			String aemail, String aaddr, int apoint, int alevel, String ausername, String apassword) {
		super();
		this.aname = aname;
		this.abirth = abirth;
		this.acreatedAt = acreatedAt;
		this.aphone = aphone;
		this.aemail = aemail;
		this.aaddr = aaddr;
		this.apoint = apoint;
		this.alevel = alevel;
		this.ausername = ausername;
		this.apassword = apassword;
		this.adisabled = false;
		this.adisabledAt = null;
	}
	
	// 직원 인서트용
	public AccountVO(String aname, Date abirth, Date acreatedAt, String aphone,
			String aemail, String aaddr, String adepartment, int apoint, int alevel, String ausername, String apassword) {
		super();
		this.aname = aname;
		this.abirth = abirth;
		this.acreatedAt = acreatedAt;
		this.aphone = aphone;
		this.aemail = aemail;
		this.aaddr = aaddr;
		this.adepartment = adepartment;
		this.apoint = apoint;
		this.alevel = alevel;
		this.ausername = ausername;
		this.apassword = apassword;
		this.adisabled = false;
		this.adisabledAt = null;
	}
	
	public AccountVO(int ano, String aname, Date abirth, Date acreatedAt, String aphone,
			String aemail, String aaddr, int apoint, int alevel, String ausername, String apassword) {
		super();
		this.aname = aname;
		this.abirth = abirth;
		this.acreatedAt = acreatedAt;
		this.aphone = aphone;
		this.aemail = aemail;
		this.aaddr = aaddr;
		this.apoint = apoint;
		this.alevel = alevel;
		this.ausername = ausername;
		this.apassword = apassword;
		this.adisabled = false;
		this.adisabledAt = null;
	}
	
	// 계정 정보 업데이트용
	public AccountVO(int ano, String aname, String adepartment, String aphone, String aemail, String aaddr, String apassword) {
		super();
		this.ano = ano;
		this.aname = aname;
		this.adepartment = adepartment;
		this.aphone = aphone;
		this.aemail = aemail;
		this.aaddr = aaddr;
		this.apassword = apassword;
		this.adisabled = false;
		this.adisabledAt = null;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
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

	public Date getAbirth() {
		return abirth;
	}

	public void setAbirth(Date abirth) {
		this.abirth = abirth;
	}

	public Date getAcreatedAt() {
		return acreatedAt;
	}

	public void setAcreatedAt(Date acreatedAt) {
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

	public int getApoint() {
		return apoint;
	}

	public void setApoint(int apoint) {
		this.apoint = apoint;
	}

	public int getAlevel() {
		return alevel;
	}

	public void setAlevel(int alevel) {
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

	public boolean isAdisabled() {
		return adisabled;
	}

	public void setAdisabled(boolean adisabled) {
		this.adisabled = adisabled;
	}

	public Date getAdisabledAt() {
		return adisabledAt;
	}

	public void setAdisabledAt(Date adisabledAt) {
		this.adisabledAt = adisabledAt;
	}
}
