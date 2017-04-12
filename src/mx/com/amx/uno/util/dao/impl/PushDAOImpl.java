package mx.com.amx.uno.util.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import mx.com.amx.uno.util.dao.IPushDAO;
import mx.com.amx.uno.util.dto.HistoricalPushDTO;
import mx.com.amx.uno.util.exception.PushDAOException;

@Component
@Qualifier("pushDAO")
public class PushDAOImpl implements IPushDAO {
	
	private Logger logger=Logger.getLogger(PushDAOImpl.class);
	private JdbcTemplate jdbc;
	
	@Override
	public boolean saveHistoricalPush(HistoricalPushDTO pushDTO) throws PushDAOException {
		StringBuffer sql=new StringBuffer();
		boolean res=false;
		int numRegistros=0;
		try {
			sql.append(" INSERT INTO WPDB2INS.UNO_MX_H_PUSH ");
			sql.append(" (FC_USUARIO, FC_MSJ_PUSH, FC_URL_NOTA, FD_FECHA_REGISTRO )");
			sql.append(" VALUES (?,?,?, CURRENT TIMESTAMP )");
			
			Object [] listObjects={
					pushDTO.getUsuario(), 
					pushDTO.getMensaje_push(),
					pushDTO.getUrl_nota()
					};
			numRegistros=jdbc.update(sql.toString(), listObjects );
			
			if(numRegistros>0)
				res=true;
			
		} catch (Exception e) {
			logger.error("Error saveHistoricalPush: "+e.getLocalizedMessage());
			throw new PushDAOException(e);
		}
		return res;
	}

	public JdbcTemplate getJdbc() {
		return jdbc;
	}
	@Autowired
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	
	
	
	
	

}
