
package cn.itcast.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.itcast.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Say_QNAME = new QName("http://webservice.itcast.cn/", "say");
    private final static QName _FindCarsByUsername_QNAME = new QName("http://webservice.itcast.cn/", "findCarsByUsername");
    private final static QName _SayResponse_QNAME = new QName("http://webservice.itcast.cn/", "sayResponse");
    private final static QName _FindCarsByUsernameResponse_QNAME = new QName("http://webservice.itcast.cn/", "findCarsByUsernameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.itcast.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FindCarsByUsernameResponse }
     * 
     */
    public FindCarsByUsernameResponse createFindCarsByUsernameResponse() {
        return new FindCarsByUsernameResponse();
    }

    /**
     * Create an instance of {@link SayResponse }
     * 
     */
    public SayResponse createSayResponse() {
        return new SayResponse();
    }

    /**
     * Create an instance of {@link Say }
     * 
     */
    public Say createSay() {
        return new Say();
    }

    /**
     * Create an instance of {@link FindCarsByUsername }
     * 
     */
    public FindCarsByUsername createFindCarsByUsername() {
        return new FindCarsByUsername();
    }

    /**
     * Create an instance of {@link Car }
     * 
     */
    public Car createCar() {
        return new Car();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Say }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.itcast.cn/", name = "say")
    public JAXBElement<Say> createSay(Say value) {
        return new JAXBElement<Say>(_Say_QNAME, Say.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindCarsByUsername }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.itcast.cn/", name = "findCarsByUsername")
    public JAXBElement<FindCarsByUsername> createFindCarsByUsername(FindCarsByUsername value) {
        return new JAXBElement<FindCarsByUsername>(_FindCarsByUsername_QNAME, FindCarsByUsername.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.itcast.cn/", name = "sayResponse")
    public JAXBElement<SayResponse> createSayResponse(SayResponse value) {
        return new JAXBElement<SayResponse>(_SayResponse_QNAME, SayResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindCarsByUsernameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.itcast.cn/", name = "findCarsByUsernameResponse")
    public JAXBElement<FindCarsByUsernameResponse> createFindCarsByUsernameResponse(FindCarsByUsernameResponse value) {
        return new JAXBElement<FindCarsByUsernameResponse>(_FindCarsByUsernameResponse_QNAME, FindCarsByUsernameResponse.class, null, value);
    }

}
