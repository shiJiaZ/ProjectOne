
package cn.itcast.webservice;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IUserService", targetNamespace = "http://webservice.itcast.cn/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IUserService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<cn.itcast.webservice.Car>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findCarsByUsername", targetNamespace = "http://webservice.itcast.cn/", className = "cn.itcast.webservice.FindCarsByUsername")
    @ResponseWrapper(localName = "findCarsByUsernameResponse", targetNamespace = "http://webservice.itcast.cn/", className = "cn.itcast.webservice.FindCarsByUsernameResponse")
    public List<Car> findCarsByUsername(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "say", targetNamespace = "http://webservice.itcast.cn/", className = "cn.itcast.webservice.Say")
    @ResponseWrapper(localName = "sayResponse", targetNamespace = "http://webservice.itcast.cn/", className = "cn.itcast.webservice.SayResponse")
    public void say(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
