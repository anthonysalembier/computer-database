package com.excilys.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="computer")
public class Computer implements Serializable {
	
	private static final long serialVersionUID = 6111680208456002875L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="introduced")
	@Type(type="com.excilys.mapper.LocalDateMapper")
	private LocalDateTime introduced;

	@Column(name="discontinued")
	@Type(type="com.excilys.mapper.LocalDateMapper")
	private LocalDateTime discontinued;
	
	@ManyToOne
	private Company company;
	
	public Computer() {}
	
	public Computer(String name) {
		this.name = name;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	
	
	public Company getCompany() {
		return company;
	}

	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}
	
	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Computer [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", introduced=");
		builder.append(introduced);
		builder.append(", discontinued=");
		builder.append(discontinued);
		builder.append(", company=");
		builder.append(company);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 131;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result
				+ ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result
				+ ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {
        private Computer computer;

        private Builder(String name) {
            computer = new Computer(name);
        }

        public Builder id(Long id) {
            computer.id = id;
            return this;
        }

        public Builder name(String name) {
            computer.name = name;
            return this;
        }

        public Builder introduced(LocalDateTime introduced) {
            computer.introduced = introduced;
            return this;
        }

        public Builder discontinued(LocalDateTime discontinued) {
            computer.discontinued = discontinued;
            return this;
        }

        public Builder company(Company company) {
            computer.company = company;
            return this;
        }

        public Computer build() {
            return computer;
        }
    }

}
