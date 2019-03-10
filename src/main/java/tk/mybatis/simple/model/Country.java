package tk.mybatis.simple.model;

public class Country {
	
	/** 序列主键  **/
	private Long id;
	/** 国家名称 **/
	private String countryname;
	/** 国家代码 **/
	private String countrycode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryname() {
		return countryname;
	}

	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Country [id=");
		builder.append(id);
		builder.append(", countryname=");
		builder.append(countryname);
		builder.append(", countrycode=");
		builder.append(countrycode);
		builder.append("]");
		return builder.toString();
	}
}
