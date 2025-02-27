/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2025 DBeaver Corp and others
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

package org.jkiss.dbeaver.ui.config.migration.wizards.navicat;

import org.jkiss.dbeaver.model.connection.DBPDriver;
import org.jkiss.dbeaver.ui.config.migration.wizards.ConfigImportWizard;

import java.io.File;

public class ConfigImportWizardNavicat extends ConfigImportWizard {
	
	private ConfigImportWizardPageNavicatDriver pageDriver;
	private ConfigImportWizardPageNavicatSettings pageSettings;
	
	@Override
    protected ConfigImportWizardPageNavicatConnections createMainPage() {
        return new ConfigImportWizardPageNavicatConnections();
    }
	
	@Override
    public void addPages() {
		pageDriver = new ConfigImportWizardPageNavicatDriver();
        pageSettings = new ConfigImportWizardPageNavicatSettings();

        addPage(pageDriver);
        addPage(pageSettings);
        super.addPages();
    }
	
	public ConfigImportWizardPageNavicatSettings getPageSettings() {
		return pageSettings;
	}
	
	public ConfigImportWizardPageNavicatDriver getPageDriver() {
		return pageDriver;
	}
	
	public DBPDriver getDriver() {
        return pageDriver.getSelectedDriver();
    }
	
	public File getInputFile() {
        return pageSettings.getInputFile();
    }

    public String getInputFileEncoding() {
        return pageSettings.getInputFileEncoding();
    }

}
