/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.bsc.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.netsteadfast.greenstep.base.action.BaseSupportAction;
import com.netsteadfast.greenstep.base.action.IBaseAdditionalSupportAction;
import com.netsteadfast.greenstep.base.chain.SimpleChain;
import com.netsteadfast.greenstep.base.exception.ControllerException;
import com.netsteadfast.greenstep.base.exception.ServiceException;
import com.netsteadfast.greenstep.base.model.ChainResultObj;
import com.netsteadfast.greenstep.base.model.ControllerAuthority;
import com.netsteadfast.greenstep.base.model.ControllerMethodAuthority;
import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.base.sys.UserAccountHttpSessionSupport;
import com.netsteadfast.greenstep.bsc.service.IVisionService;
import com.netsteadfast.greenstep.bsc.vo.StrategyMapItemsVO;
import com.netsteadfast.greenstep.po.hbm.BbVision;
import com.netsteadfast.greenstep.util.MenuSupportUtils;
import com.netsteadfast.greenstep.vo.VisionVO;

@ControllerAuthority(check=true)
@Controller("bsc.web.controller.StrategyMapManagementAction")
@Scope
public class StrategyMapManagementAction extends BaseSupportAction implements IBaseAdditionalSupportAction {
	private static final long serialVersionUID = -818474109895868062L;
	private IVisionService<VisionVO, BbVision, String> visionService;
	private Map<String, String> visionMap = this.providedSelectZeroDataMap(true);
	private String visionOid = "";
	private List<String> divItems = new ArrayList<String>();
	private List<String> cssItems = new ArrayList<String>();
	private List<String> conItems = new ArrayList<String>();
	private String printMode = YesNo.NO;
	
	public StrategyMapManagementAction() {
		super();
	}
	
	public IVisionService<VisionVO, BbVision, String> getVisionService() {
		return visionService;
	}

	@Autowired
	@Resource(name="bsc.service.VisionService")
	@Required		
	public void setVisionService(
			IVisionService<VisionVO, BbVision, String> visionService) {
		this.visionService = visionService;
	}
	
	@SuppressWarnings("unchecked")
	private Context getChainContext() throws Exception {
		Context context = new ContextBase();
		context.put("visionOid", this.visionOid);
		return context;
	}

	private void initData() throws ServiceException, Exception {
		this.visionMap = this.visionService.findForMap(true);
	}	
	
	private void loadNew() throws ControllerException, ServiceException, Exception {
		if ( StringUtils.isBlank(this.visionOid) || super.isNoSelectId(this.visionOid) ) {
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0007Q_vision") );
		}
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("strategyMapItemsForNewChain", context);
		this.setPageMessage( resultObj.getMessage() );
		if ( resultObj.getValue() instanceof StrategyMapItemsVO ) {
			this.divItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getDiv();
			this.cssItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCss();
		}
		
	}
	
	private void loadRecord() throws ControllerException, ServiceException, Exception {
		if ( StringUtils.isBlank(this.visionOid) || super.isNoSelectId(this.visionOid) ) {
			throw new ControllerException( this.getText("MESSAGE.BSC_PROG002D0007Q_vision") );
		}
		Context context = this.getChainContext();
		SimpleChain chain = new SimpleChain();
		ChainResultObj resultObj = chain.getResultFromResource("strategyMapItemsForRecChain", context);
		this.setPageMessage( resultObj.getMessage() );
		if ( resultObj.getValue() instanceof StrategyMapItemsVO ) {
			this.divItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getDiv();
			this.cssItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCss();
			this.conItems = ( (StrategyMapItemsVO)resultObj.getValue() ).getCon();
		}		
	}
	
	/**
	 *  bsc.strategyMapManagementAction.action
	 */
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
	public String execute() throws Exception {
		try {
			this.initData();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}	
	
	/**
	 *  bsc.strategyMapLoadNewAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
	public String doLoadNew() throws Exception {
		try {
			this.initData();
			this.loadNew();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}		
	
	/**
	 *  bsc.strategyMapLoadRecordAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
	public String doLoadRecord() throws Exception {
		try {
			this.initData();
			this.loadRecord();
		} catch (ControllerException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (ServiceException e) {
			this.setPageMessage(e.getMessage().toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setPageMessage(e.getMessage().toString());
		}
		return SUCCESS;		
	}
	
	/**
	 *  bsc.strategyMapOpenWinDlgAction.action
	 */	
	@ControllerMethodAuthority(programId="BSC_PROG002D0007Q")
	public String doOpenWinDlg() throws Exception {
		String forward = doLoadRecord();
		this.printMode = YesNo.YES;
		return forward;
	}
	
	@Override
	public String getProgramName() {
		try {
			return MenuSupportUtils.getProgramName(this.getProgramId(), UserAccountHttpSessionSupport.getLang( ServletActionContext.getContext() ));
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getProgramId() {
		return super.getActionMethodProgramId();
	}

	public Map<String, String> getVisionMap() {
		return visionMap;
	}

	public String getVisionOid() {
		return visionOid;
	}

	public void setVisionOid(String visionOid) {
		this.visionOid = visionOid;
	}

	public List<String> getDivItems() {
		return divItems;
	}

	public List<String> getCssItems() {
		return cssItems;
	}

	public List<String> getConItems() {
		return conItems;
	}

	public String getPrintMode() {
		return printMode;
	}
	
}
