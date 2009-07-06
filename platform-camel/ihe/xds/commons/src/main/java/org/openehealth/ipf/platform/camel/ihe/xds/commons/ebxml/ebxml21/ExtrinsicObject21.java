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
package org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.ebxml21;

import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.ExtrinsicObject;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.ObjectLibrary;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.rim.ExtrinsicObjectType;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.stub.ebrs21.rim.ObjectFactory;

/**
 * Encapsulation of {@link ExtrinsicObjectType}.
 * @author Jens Riemschneider
 */
public class ExtrinsicObject21 extends RegistryEntry21<ExtrinsicObjectType> implements ExtrinsicObject {
    private final static ObjectFactory rimFactory = new ObjectFactory();

    private ExtrinsicObject21(ExtrinsicObjectType extrinsic, ObjectLibrary objectLibrary) {
        super(extrinsic, objectLibrary);
    }

    public static ExtrinsicObject21 create(ExtrinsicObjectType extrinsicObject, ObjectLibrary objectLibrary) {
        return new ExtrinsicObject21(extrinsicObject, objectLibrary);
    }

    static ExtrinsicObject21 create(ObjectLibrary objectLibrary, String id) {
        ExtrinsicObjectType extrinsicObjectType = rimFactory.createExtrinsicObjectType();
        extrinsicObjectType.setId(id);
        return new ExtrinsicObject21(extrinsicObjectType, objectLibrary);
    }

    @Override
    public String getMimeType() {
        // For compatibility with ebXML 3.0, we return a default if the mime type wasn't set. 
        String mimeType = getInternal().getMimeType();
        return mimeType != null ? mimeType : "application/octet-stream";
    }

    @Override
    public void setMimeType(String mimeType) {
        getInternal().setMimeType(mimeType);
    }
}
