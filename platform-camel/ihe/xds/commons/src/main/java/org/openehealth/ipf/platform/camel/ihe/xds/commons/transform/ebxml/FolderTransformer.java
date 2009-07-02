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
package org.openehealth.ipf.platform.camel.ihe.xds.commons.transform.ebxml;

import static org.apache.commons.lang.Validate.notNull;
import static org.openehealth.ipf.platform.camel.ihe.xds.commons.metadata.Vocabulary.*;

import java.util.List;

import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.Classification;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.EbXMLFactory;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.ObjectLibrary;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.ebxml.RegistryPackage;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.metadata.Code;
import org.openehealth.ipf.platform.camel.ihe.xds.commons.metadata.Folder;

/**
 * Transforms between a {@link Folder} and its ebXML 2.1 representation.
 * @author Jens Riemschneider
 */
public class FolderTransformer extends XDSMetaClassTransformer<RegistryPackage, Folder> {
    private final EbXMLFactory factory;

    private final CodeTransformer codeTransformer;

    /**
     * Constructs the transformer.
     */
    public FolderTransformer(EbXMLFactory factory) {
        super(FOLDER_PATIENT_ID_EXTERNAL_ID, 
                FOLDER_LOCALIZED_STRING_PATIENT_ID, 
                FOLDER_UNIQUE_ID_EXTERNAL_ID,
                FOLDER_LOCALIZED_STRING_UNIQUE_ID);

        notNull(factory, "factory cannot be null");

        this.factory = factory;
        codeTransformer = new CodeTransformer(factory);
    }
    
    @Override
    protected RegistryPackage createEbXMLInstance(String id, ObjectLibrary objectLibrary) {
        return factory.createRegistryPackage(id, objectLibrary);
    }
    
    @Override
    protected Folder createMetaClassInstance() {
        return new Folder();
    }
    
    @Override
    protected void addAttributes(Folder metaData, RegistryPackage ebXML, ObjectLibrary objectLibrary) {
        super.addAttributes(metaData, ebXML, objectLibrary);
        ebXML.setHome(metaData.getHomeCommunityId());
    }
    
    @Override
    protected void addAttributesFromEbXML(Folder metaData, RegistryPackage ebXML) {
        super.addAttributesFromEbXML(metaData, ebXML);
        metaData.setHomeCommunityId(ebXML.getHome());
    }

    @Override
    protected void addSlotsFromEbXML(Folder folder, RegistryPackage regPackage) {
        super.addSlotsFromEbXML(folder, regPackage);        
        
        folder.setLastUpdateTime(regPackage.getSingleSlotValue(SLOT_NAME_LAST_UPDATE_TIME));
    }

    @Override
    protected void addSlots(Folder folder, RegistryPackage regPackage, ObjectLibrary objectLibrary) {
        super.addSlots(folder, regPackage, objectLibrary);
        
        regPackage.addSlot(SLOT_NAME_LAST_UPDATE_TIME, folder.getLastUpdateTime());        
    }

    @Override
    protected void addClassificationsFromEbXML(Folder folder, RegistryPackage regPackage) {
        super.addClassificationsFromEbXML(folder, regPackage);
        
        List<Code> codes = folder.getCodeList();
        for (Classification code : regPackage.getClassifications(FOLDER_CODE_LIST_CLASS_SCHEME)) {
            codes.add(codeTransformer.fromEbXML(code));
        }
    }

    @Override
    protected void addClassifications(Folder folder, RegistryPackage regPackage, ObjectLibrary objectLibrary) {
        super.addClassifications(folder, regPackage, objectLibrary);
        
        for (Code codeListElem : folder.getCodeList()) {
            Classification code = codeTransformer.toEbXML(codeListElem, objectLibrary);
            regPackage.addClassification(code, FOLDER_CODE_LIST_CLASS_SCHEME);
        }
    }
}
