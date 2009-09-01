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
package org.openehealth.ipf.commons.ihe.xds.validate.responses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.openehealth.ipf.commons.ihe.xds.validate.ValidationMessage.*;

import org.junit.Before;
import org.junit.Test;
import org.openehealth.ipf.commons.ihe.xds.SampleData;
import org.openehealth.ipf.commons.ihe.xds.ebxml.EbXMLFactory;
import org.openehealth.ipf.commons.ihe.xds.ebxml.EbXMLRegistryResponse;
import org.openehealth.ipf.commons.ihe.xds.ebxml.ebxml30.EbXMLFactory30;
import org.openehealth.ipf.commons.ihe.xds.responses.ErrorInfo;
import org.openehealth.ipf.commons.ihe.xds.responses.Response;
import org.openehealth.ipf.commons.ihe.xds.responses.Severity;
import org.openehealth.ipf.commons.ihe.xds.stub.ebrs30.rs.RegistryResponseType;
import org.openehealth.ipf.commons.ihe.xds.transform.responses.ResponseTransformer;
import org.openehealth.ipf.commons.ihe.xds.validate.ValidationMessage;
import org.openehealth.ipf.commons.ihe.xds.validate.XDSMetaDataException;
import org.openehealth.ipf.commons.ihe.xds.validate.responses.RegistryResponseValidator;

/**
 * Tests for {@link RegistryResponseValidator}.
 * @author Jens Riemschneider
 */
public class RegistryResponseValidatorTest {
    private RegistryResponseValidator validator;
    private Response response;
    private ResponseTransformer transformer;
    private EbXMLFactory factory;

    @Before
    public void setUp() {
        validator = new RegistryResponseValidator();
        factory = new EbXMLFactory30();
        transformer = new ResponseTransformer(factory);
        response = SampleData.createResponse();
    }

    @Test
    public void testGoodCase() throws XDSMetaDataException {
        validator.validate(transformer.toEbXML(response), null);
    }
    
    @Test
    public void testInvalidStatus() {
        response.setStatus(null);
        expectFailure(INVALID_STATUS_IN_RESPONSE);
    }
    
    @Test
    public void testInvalidErrorInfo() {
        response.getErrors().add(null);
        expectFailure(INVALID_ERROR_INFO_IN_RESPONSE);
    }
    
    @Test
    public void testInvalidErrorCode() {
        response.getErrors().add(new ErrorInfo(null, null, Severity.ERROR, null));
        expectFailure(INVALID_ERROR_CODE_IN_RESPONSE);
    }    

    @Test
    public void testInvalidSeverity() {
        EbXMLRegistryResponse ebXML = transformer.toEbXML(response);
        ((RegistryResponseType)ebXML.getInternal()).getRegistryErrorList().getRegistryError().get(0).setSeverity("lol");
        expectFailure(INVALID_SEVERITY_IN_RESPONSE, ebXML);
    }    

    private void expectFailure(ValidationMessage expectedMessage) {
        expectFailure(expectedMessage, transformer.toEbXML(response));
    }

    private void expectFailure(ValidationMessage expectedMessage, EbXMLRegistryResponse ebXMLRegistryResponse) {
        try {
            validator.validate(ebXMLRegistryResponse, null);
            fail("Expected exception: " + XDSMetaDataException.class);
        }
        catch (XDSMetaDataException e) {
            assertEquals(expectedMessage, e.getValidationMessage());
        }
    }
}