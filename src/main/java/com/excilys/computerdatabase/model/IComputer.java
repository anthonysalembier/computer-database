package com.excilys.computerdatabase.model;

import java.sql.Timestamp;
import java.time.LocalDate;

public interface IComputer {
	
	public long getId();
	
	public String getName();
	
	public Timestamp getIntroducedDate();
	
	public Timestamp getDiscontinuedDate();
	
	public long getCompanyId();

	public void setId(long id);
	
	public void setName(String name);
	
	public void setIntroducedDate(Timestamp date);
	
	public void setDiscontinuedDate(Timestamp date);
	
	public void setCompanyId(long id);

}
