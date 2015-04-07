package com.excilys.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.connection.ComputerDatabaseConnection;
import com.excilys.exception.DAOException;
import com.excilys.exception.PersistenceException;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;

public enum CompanyDAO implements DAO<Company, Long> {
	INSTANCE;
	
	private Properties properties;
	
	private String companyTable;
	private String computerTable;
	private String computerCompanyId;
	private String companyId;
	private String companyName;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
	
	private ComputerDatabaseConnection connection = ComputerDatabaseConnection.INSTANCE;
	
	private CompanyDAO() {
		if (properties == null) {
			properties = new Properties();
			
			try (InputStream input = CompanyDAO.class.getClassLoader().getResourceAsStream("tableConfig.properties")) {
				properties.load(input);
				companyTable = properties.getProperty("company");
				companyId = properties.getProperty("companyId");
				companyName = properties.getProperty("companyName");
				computerTable = properties.getProperty("computer");
				computerCompanyId = properties.getProperty("computerCompanyId");
			} catch (IOException e) {
				throw new DAOException(e.getMessage());
			}
		}
	}

	@Override
	public List<Company> getAll() {
		final List<Company> companies = new ArrayList<>();
		final CompanyMapper companyMapper = new CompanyMapper();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ");
		sql.append(companyTable);
		sql.append(" ORDER BY ").append(companyName);
		
		try (final PreparedStatement pStatement = connection.getInstance().prepareStatement(sql.toString())) {
			try (final ResultSet rs = pStatement.executeQuery()) {
				while (rs.next()) {
					companies.add(companyMapper.rowMap(rs));
				}
			}
		} catch (SQLException | PersistenceException e) {
			throw new DAOException(e.getMessage());
		}

		return companies;
	}

	@Override
	public Company getById(Long id) {
		final CompanyMapper companyMapper = new CompanyMapper();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ").append(companyTable);
		sql.append(" WHERE ").append(this.companyId).append(" = ?");

		try (final PreparedStatement pStatement = connection.getInstance().prepareStatement(sql.toString())) {
			pStatement.setLong(1, id);
			try (final ResultSet rs = pStatement.executeQuery()) {
				if (rs.first()) {
					return companyMapper.rowMap(rs);
				}
			}
		} catch (SQLException | PersistenceException e) {
			throw new DAOException(e.getMessage());
		}
		return null;
	}
	
	@Override
	public void delete(Long id) {
		StringBuffer deleteComputers = new StringBuffer();
		StringBuffer deleteCompany = new StringBuffer();

		deleteComputers.append("DELETE FROM ").append(computerTable);
		deleteComputers.append(" WHERE ");
		deleteComputers.append(computerCompanyId).append(" = ?");
		
		deleteCompany.append("DELETE FROM ").append(companyTable);
		deleteCompany.append(" WHERE ");
		deleteCompany.append(companyId).append(" = ?");
		
		try (final PreparedStatement delCptsStatement = connection.getInstance()
														.prepareStatement(deleteComputers.toString());
				final PreparedStatement delCpyStatement = connection.getInstance()
														.prepareStatement(deleteCompany.toString())) {
			connection.startTransaction();
			
			delCptsStatement.setLong(1, id);
			delCptsStatement.execute();
			
			delCpyStatement.setLong(1, id);
			delCpyStatement.execute();

			connection.commit();

			LOGGER.info("Company (id:{}) and all computers linked have successfully been deleted", id);
		} catch (SQLException | PersistenceException e) {
			connection.rollback();
			throw new DAOException(e.getMessage());
		} finally {
			connection.endTransaction();
		}
	}
	
	@Override
	public int count() {
		final String sql = "SELECT COUNT(*) FROM " + companyTable;
		try (final Statement state = connection.getInstance().createStatement()) {
            final ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | PersistenceException e) {
            throw new DAOException(e.getMessage());
		}
        return 0;
	}
}
