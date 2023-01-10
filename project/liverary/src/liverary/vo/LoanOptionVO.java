package liverary.vo;

import java.time.LocalDate;

public class LoanOptionVO {
	private int lno;
	private LocalDate lcreatedAt;
	private LocalDate lcreatedAtStart;
	private LocalDate lcreatedAtEnd;
	private LocalDate ldueDate;
	private LocalDate lreturnedAt;
	private String bisbn;
	private String btitle;
	private String bdate;
	private int bpage;
	private int bprice;
	private String bauthor;
	private String btranslator;
	private String bsupplement;
	private String bpublisher;
	private boolean available;
	private int ano;
	private String available_kor;
	
	public int getAno() {
		return ano;
	}

	
	public void setAno(int ano) {
		this.ano = ano;
	}



	public LoanOptionVO() {
	}
	
	
	
	public LoanOptionVO(int lno, LocalDate lcreatedAtStart, LocalDate lcreatedAtEnd, LocalDate ldueDate,
			LocalDate lreturnedAt, String bisbn, String btitle, String bdate, int bpage, int bprice, String bauthor,
			String btranslator, String bsupplement, String bpublisher, boolean available, int ano,
			String available_kor) {
		super();
		this.lno = lno;
		this.lcreatedAtStart = lcreatedAtStart;
		this.lcreatedAtEnd = lcreatedAtEnd;
		this.ldueDate = ldueDate;
		this.lreturnedAt = lreturnedAt;
		this.bisbn = bisbn;
		this.btitle = btitle;
		this.bdate = bdate;
		this.bpage = bpage;
		this.bprice = bprice;
		this.bauthor = bauthor;
		this.btranslator = btranslator;
		this.bsupplement = bsupplement;
		this.bpublisher = bpublisher;
		this.available = available;
		this.ano = ano;
		this.available_kor = available_kor;
	}


	
	public String getAvailable_kor() {
		return available_kor;
	}



	public void setAvailable_kor(String available_kor) {
		this.available_kor = available_kor;
	}
	


	public int getLno() {
		return lno;
	}



	public void setLno(int lno) {
		this.lno = lno;
	}


	public LocalDate getLdueDate() {
		return ldueDate;
	}



	public void setLdueDate(LocalDate ldueDate) {
		this.ldueDate = ldueDate;
	}



	public LocalDate getLreturnedAt() {
		return lreturnedAt;
	}



	public void setLreturnedAt(LocalDate lreturnedAt) {
		this.lreturnedAt = lreturnedAt;
	}



	public String getBisbn() {
		return bisbn;
	}



	public void setBisbn(String bisbn) {
		this.bisbn = bisbn;
	}



	public String getBtitle() {
		return btitle;
	}



	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}



	public String getBdate() {
		return bdate;
	}



	public void setBdate(String bdate) {
		this.bdate = bdate;
	}



	public int getBpage() {
		return bpage;
	}



	public void setBpage(int bpage) {
		this.bpage = bpage;
	}



	public int getBprice() {
		return bprice;
	}



	public void setBprice(int bprice) {
		this.bprice = bprice;
	}



	public String getBauthor() {
		return bauthor;
	}



	public void setBauthor(String bauthor) {
		this.bauthor = bauthor;
	}



	public String getBtranslator() {
		return btranslator;
	}



	public void setBtranslator(String btranslator) {
		this.btranslator = btranslator;
	}



	public String getBsupplement() {
		return bsupplement;
	}



	public void setBsupplement(String bsupplement) {
		this.bsupplement = bsupplement;
	}



	public String getBpublisher() {
		return bpublisher;
	}



	public void setBpublisher(String bpublisher) {
		this.bpublisher = bpublisher;
	}



	public boolean isAvailable() {
		return available;
	}



	public void setAvailable(boolean available) {
		this.available = available;
	}


	public LocalDate getLcreatedAtStart() {
		return lcreatedAtStart;
	}


	public void setLcreatedAtStart(LocalDate lcreatedAtStart) {
		this.lcreatedAtStart = lcreatedAtStart;
	}


	public LocalDate getLcreatedAtEnd() {
		return lcreatedAtEnd;
	}


	public void setLcreatedAtEnd(LocalDate lcreatedAtEnd) {
		this.lcreatedAtEnd = lcreatedAtEnd;
	}


	public LocalDate getLcreatedAt() {
		return lcreatedAt;
	}

	
	public void setLcreatedAt(LocalDate lcreatedAt) {
		this.lcreatedAt = lcreatedAt;
	}
	
}
