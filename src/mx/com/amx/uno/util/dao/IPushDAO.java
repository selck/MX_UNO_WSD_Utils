package mx.com.amx.uno.util.dao;

import mx.com.amx.uno.util.dto.HistoricalPushDTO;
import mx.com.amx.uno.util.exception.PushDAOException;

public interface IPushDAO {
	/*
	 * Método que devuelve un booleano true para confirmar que se guardo el parametro
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: Boolean
	 * 
	*/
	boolean saveHistoricalPush(HistoricalPushDTO pushDTO) throws PushDAOException;
}
