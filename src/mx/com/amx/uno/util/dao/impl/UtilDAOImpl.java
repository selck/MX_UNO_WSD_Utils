package mx.com.amx.uno.util.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import mx.com.amx.uno.util.dao.IUtilDAO;
import mx.com.amx.uno.util.dto.ParametroDTO;
import mx.com.amx.uno.util.exception.UtilDAOException;
@Component
@Qualifier("utilDAO")
public class UtilDAOImpl implements IUtilDAO {
	
	private JdbcTemplate jdbcTemplate;
	private Logger logger=Logger.getLogger(UtilDAOImpl.class);
	
	@Override
	public boolean saveOrUpdateParameter(String idParameter, String value) throws UtilDAOException {
		boolean exist=false;
		StringBuffer sql=new StringBuffer();
		List<Object> listObjects=null;
		logger.info("saveOrUpdateParameter: [DAO-Impl] ");
		logger.info("IdParameter: "+idParameter);
		logger.info("value: "+value);
		try {
			if(existParameter(idParameter)){
				logger.info("Actualizamos Parametro");
				sql.append(" UPDATE WPDB2INS.UNO_MX_C_PARAMETROS");
				sql.append(" SET FC_VALOR = ?");
				sql.append(" WHERE FC_ID_PARAMETRO = ?");
				listObjects=new ArrayList<Object>();
				listObjects.add(value);
				listObjects.add(idParameter);
			}else{
				logger.info("Agregamos Parametro");
				sql.append(" INSERT INTO WPDB2INS.UNO_MX_C_PARAMETROS");
				sql.append(" (FC_ID_PARAMETRO, FC_VALOR, FI_ESTATUS, FI_SHOW_PARAMETER)");
				sql.append(" VALUES");
				sql.append(" (?,?,?,?)");
				listObjects=new ArrayList<Object>();
				listObjects.add(idParameter);
				listObjects.add(value);
				listObjects.add(1);
				listObjects.add(1);
			}
			int rows=jdbcTemplate.update(sql.toString(), listObjects.toArray());
			
			if(rows>0)
				exist=true;
			
		} catch (Exception e) {
			logger.error("Error saveOrUpdateParameter: "+e.getLocalizedMessage());
			throw new UtilDAOException(e.getLocalizedMessage());
		}
		return exist;
	}

	@Override
	public boolean existParameter(final String idParameter) throws UtilDAOException {
		boolean exist=false;
		StringBuffer sql=new StringBuffer();
		try {
			sql.append(" select count(FC_VALOR) as existe ");
			sql.append(" from WPDB2INS.UNO_MX_C_PARAMETROS");
			sql.append(" WHERE FC_ID_PARAMETRO=? ");
			
			Object [] arrayObject={idParameter};
			int [] argTypes={Types.VARCHAR};
			
			int total=jdbcTemplate.queryForInt(sql.toString(),arrayObject, argTypes);
			
			if(total>0)
				exist=true;		
			
		} catch (Exception e) {
			logger.error("Error existParameter: "+e.getLocalizedMessage());
			throw new UtilDAOException(e.getLocalizedMessage());
		}
		return exist;
	}

	@Override
	public String getParameter(String idParameter) throws UtilDAOException {
		String valorParametro="";
		StringBuffer sql=new StringBuffer();
		try {
			logger.info("idParameter: "+idParameter);
			
			if(idParameter.equals(""))
				throw new UtilDAOException("No existe el Parametro para un ID vacio ");
			else if(!existParameter(idParameter))
				throw new UtilDAOException("No existe el Parametro con el ID "+idParameter);
			
			sql.append(" select FC_VALOR ");
			sql.append(" from WPDB2INS.UNO_MX_C_PARAMETROS");
			sql.append(" WHERE FC_ID_PARAMETRO=? ");
			
			Object [] arrayObject={idParameter};
			int [] argTypes={Types.VARCHAR};
			
			valorParametro=(String) jdbcTemplate.queryForObject(sql.toString(), arrayObject, argTypes, String.class);
			
		} catch (Exception e) {
			logger.error("Error getParameter: "+e.getLocalizedMessage());
			throw new UtilDAOException(e.getLocalizedMessage());
		}
		return valorParametro;
	}
	
	@Override
	public ArrayList<ParametroDTO> getParameters() throws UtilDAOException {
		ArrayList<ParametroDTO> listParameter=new ArrayList<ParametroDTO>();
		StringBuffer sql=new StringBuffer();
		try {
			sql.append("SELECT ");
			sql.append("FC_ID_PARAMETRO as id, ");
			sql.append("FC_VALOR as valor, ");
			sql.append("FC_DESCRIPTION as descripcion ");
			sql.append("FROM WPDB2INS.UNO_MX_C_PARAMETROS ");
			sql.append("WHERE FI_SHOW_PARAMETER=? ");
			
			Object [] arrayObject={1};
			//BeanPropertyRowMapper<PreferidosDTO> mapper = new BeanPropertyRowMapper<PreferidosDTO>(PreferidosDTO.class);
			//ArrayList<PreferidosDTO> lst = (ArrayList<PreferidosDTO>) jdbcTemplate.query(sbQuery.toString(), array.toArray(), mapper);

			listParameter=(ArrayList<ParametroDTO>)jdbcTemplate.query(sql.toString(), arrayObject, new BeanPropertyRowMapper<ParametroDTO>(ParametroDTO.class));
			
		} catch (Exception e) {
			logger.error("Error getParameters: "+e.getLocalizedMessage());
			e.printStackTrace();
			throw new UtilDAOException(e.getLocalizedMessage());
		}
		return listParameter;
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	

	
}
