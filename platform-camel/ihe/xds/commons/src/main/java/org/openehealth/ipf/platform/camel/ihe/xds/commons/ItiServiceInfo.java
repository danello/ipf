/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.ipf.platform.camel.ihe.xds.commons;

import org.apache.commons.lang.Validate;

import javax.xml.namespace.QName;

/**
 * Contains information about a web-service (e.g. the service class).
 * @param <T>
 *          type of the service interface.
 */
public class ItiServiceInfo<T> {
    private final QName bindingName;
    private final Class<T> serviceClass;
    private final QName serviceName;
    private final String wsdlLocation;
    private final boolean mtom;
    private final boolean addressing;
    private final QName portName11;
    private final QName portName12;

    /**
     * Constructs the service info.
     * @param serviceName
     *          the qualified name of the service.
     * @param serviceClass
     *          the class of the service interface.
     * @param bindingName
     *          the qualified name of the binding to use.
     * @param portName11
     *          the qualified name of the SOAP 1.1 port.
     * @param portName12
     *          the qualified name of the SOAP 1.2 port (if present).
     * @param mtom
     *          {@code true} if this service requires MTOM.
     * @param wsdlLocation
     *          the location of the WSDL of this webservice.
     */
    public ItiServiceInfo(QName serviceName, 
                          Class<T> serviceClass, 
                          QName bindingName,
                          QName portName11,
                          QName portName12,
                          boolean mtom, 
                          String wsdlLocation, 
                          boolean addressing) 
    {
        Validate.notNull(serviceName, "serviceName");
        Validate.notNull(serviceClass, "serviceClass");
        Validate.notNull(bindingName, "bindingName");
        Validate.notNull(portName11, "portName11");
        Validate.notNull(wsdlLocation, "wsdlLocation");

        this.serviceClass = serviceClass;
        this.serviceName = serviceName;
        this.bindingName = bindingName;
        this.portName11 = portName11;
        this.portName12 = portName12;
        this.mtom = mtom;
        this.wsdlLocation = wsdlLocation;
        this.addressing = addressing;
    }

    /**
     * @return the qualified name of the binding to use.
     */
    public QName getBindingName() {
        return bindingName;
    }

    /**
     * @return the qualified port name for SOAP 1.1.
     */
    public QName getPortName11() {
        return portName11;
    }

    /**
     * @return the qualified port name for SOAP 1.2 
     * (can be <code>null<code> for XDS.a transactions).  
     */
    public QName getPortName12() {
        return portName12;
    }

    /**
     * @return the qualified name of the service.
     */
    public QName getServiceName() {
        return serviceName;
    }

    /**
     * @return the class of the service interface.
     */
    public Class<T> getServiceClass() {
        return serviceClass;
    }

    /**
     * @return {@code true} if this service requires MTOM.
     */
    public boolean isMtom() {
        return mtom;
    }

    /**
     * @return the location of the WSDL of this webservice.
     */
    public String getWsdlLocation() {
        return wsdlLocation;
    }

    /**
     * Whether WS-Addressing should be supported.  
     * Currently affects only the client side (i.e. the Camel producer). 
     * 
     * @return {@code true} if this service requires WS-Addressing.
     */
    public boolean isAddressing() {
        return addressing;
    }
}
