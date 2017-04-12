package mx.com.amx.uno.util.controller;

import javax.servlet.http.HttpServletResponse;

import mx.com.amx.uno.util.dao.IPushDAO;
import mx.com.amx.uno.util.dto.HistoricalPushDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@RequestMapping("pushController")
public class PushController {
	
	private Logger logger=Logger.getLogger(PushController.class);
	private IPushDAO pushDAO;
	
	@RequestMapping(value={"saveHistoricalPush"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Accept=application/json"})
	@ResponseBody
	Boolean saveHistoricalPush(@RequestBody HistoricalPushDTO pushDTO, HttpServletResponse response){
		logger.info("saveHistoricalPush [Controller]");
		String msj="OK";
		String codigo="0";
		String causa_error="";
		int status_peticion=HttpServletResponse.SC_OK;
		Boolean res=false;
		try {
			res=pushDAO.saveHistoricalPush(pushDTO);
		} catch (Exception e) {
			logger.error("Error saveHistoricalPush [Controller]: ",e);
			codigo="-1";
			msj=e.getMessage();
			causa_error=e.toString();
			status_peticion=HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		response.setHeader("codigo", codigo);
		response.setHeader("mensaje", msj);
		response.setHeader("causa_error", causa_error);
		response.setStatus(status_peticion);
		return res;
	}

	public IPushDAO getPushDAO() {
		return pushDAO;
	}
	@Autowired
	public void setPushDAO(IPushDAO pushDAO) {
		this.pushDAO = pushDAO;
	}
	
	
	

}
