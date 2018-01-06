package cn.itcast.export.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import cn.itcast.export.vo.ExportResult;
import cn.itcast.export.vo.ExportVo;

public interface IEpService {
	
	@PUT
	@Path("/exportE")
	@Consumes({"application/json","application/xml"})
	@Produces({"application/json","application/xml"})
	public ExportResult exportE(ExportVo exportVo) throws Exception;
	
	
}
