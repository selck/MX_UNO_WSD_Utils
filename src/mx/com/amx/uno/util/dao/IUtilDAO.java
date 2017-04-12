package mx.com.amx.uno.util.dao;

import java.util.ArrayList;

import mx.com.amx.uno.util.dto.ParametroDTO;
import mx.com.amx.uno.util.exception.UtilDAOException;

public interface IUtilDAO {
	/*
	 * Método que devuelve un booleano true para confirmar que se guardo el parametro
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: Boolean
	 * 
	*/
	boolean saveOrUpdateParameter(String idParameter, String value) throws UtilDAOException;
	
	/*
	 * Método que devuelve un booleano true para confirmar que existe el parametro
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: Boolean
	 * 
	*/
	boolean existParameter(String idParameter) throws UtilDAOException;
	
	/*
	 * Método que devuelve el valor de un id parametro
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: String
	 * 
	*/
	String getParameter(String idParameter) throws UtilDAOException;
	/*
	 * Método que devuelve una lista de Parametros a mostrar en el backOffice
	 *@author: Jesús Vicuña 
	 * @version: 1.0
	 * @return: List<ParametroDTO> 
	 * 
	*/
	ArrayList<ParametroDTO> getParameters() throws UtilDAOException;
}
